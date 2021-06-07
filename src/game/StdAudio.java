package game;

import javax.sound.sampled.*;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 * <i>Standard audio</i>. This class provides a basic capability for
 * creating, reading, and saving audio.
 * <p>
 * The audio format uses a sampling rate of 44,100 Hz, 16-bit, monaural.
 *
 * <p>
 * For additional documentation, see <a href="https://introcs.cs.princeton.edu/15inout">Section 1.5</a> of
 * <i>Computer Science: An Interdisciplinary Approach</i> by Robert Sedgewick and Kevin Wayne.
 *
 * @author Robert Sedgewick
 * @author Kevin Wayne
 */
public final class StdAudio {

    /**
     * The sample rate: 44,100 Hz for CD quality audio.
     */
    public static final int SAMPLE_RATE = 44100;

    private static final int BYTES_PER_SAMPLE = 2;       // 16-bit audio
    private static final int BITS_PER_SAMPLE = 16;       // 16-bit audio
    private static final double MAX_16_BIT = 32768;
    private static final int SAMPLE_BUFFER_SIZE = 4096;

    private static final int MONO = 1;
    private static final int STEREO = 2;
    private static final boolean LITTLE_ENDIAN = false;
    private static final boolean SIGNED = true;


    private static SourceDataLine line;   // to play the sound
    private static byte[] buffer;         // our internal buffer
    private static int bufferSize = 0;    // number of samples currently in internal buffer

    // static initializer
    static {
        init();
    }

    private StdAudio() {
        // can not instantiate
    }

    // open up an audio stream
    private static void init() {
        try {
            // 44,100 Hz, 16-bit audio, mono, signed PCM, little endian
            AudioFormat format = new AudioFormat((float) SAMPLE_RATE, BITS_PER_SAMPLE, MONO, SIGNED, LITTLE_ENDIAN);
            DataLine.Info info = new DataLine.Info(SourceDataLine.class, format);

            line = (SourceDataLine) AudioSystem.getLine(info);
            line.open(format, SAMPLE_BUFFER_SIZE * BYTES_PER_SAMPLE);

            // the internal buffer is a fraction of the actual buffer size, this choice is arbitrary
            // it gets divided because we can't expect the buffered data to line up exactly with when
            // the sound card decides to push out its samples.
            buffer = new byte[SAMPLE_BUFFER_SIZE * BYTES_PER_SAMPLE / 3];
        } catch (LineUnavailableException e) {
            System.out.println(e.getMessage());
        }

        // no sound gets made before this call
        line.start();
    }

    // get an AudioInputStream object from a file
    private static AudioInputStream getAudioInputStreamFromFile(String filename) {
        if (filename == null) {
            throw new IllegalArgumentException("filename is null");
        }

        try {
            // first try to read file from local file system
            File file = new File(filename);
            if (file.exists()) {
                return AudioSystem.getAudioInputStream(file);
            }

            // resource relative to .class file
            InputStream is1 = StdAudio.class.getResourceAsStream(filename);
            if (is1 != null) {
                return AudioSystem.getAudioInputStream(is1);
            }

            // resource relative to classloader root
            InputStream is2 = StdAudio.class.getClassLoader().getResourceAsStream(filename);
            if (is2 != null) {
                return AudioSystem.getAudioInputStream(is2);
            }

            // give up
            else {
                throw new IllegalArgumentException("could not read '" + filename + "'");
            }
        } catch (IOException e) {
            throw new IllegalArgumentException("could not read '" + filename + "'", e);
        } catch (UnsupportedAudioFileException e) {
            throw new IllegalArgumentException("file of unsupported audio format: '" + filename + "'", e);
        }
    }

    /**
     * Closes standard audio.
     */
    public static void close() {
        line.drain();
        line.stop();
    }

    /**
     * Writes one sample (between -1.0 and +1.0) to standard audio.
     * If the sample is outside the range, it will be clipped.
     *
     * @param sample the sample to play
     * @throws IllegalArgumentException if the sample is {@code Double.NaN}
     */
    public static void play(double sample) {
        if (Double.isNaN(sample)) throw new IllegalArgumentException("sample is NaN");

        // clip if outside [-1, +1]
        if (sample < -1.0) sample = -1.0;
        if (sample > +1.0) sample = +1.0;

        // convert to bytes
        short s = (short) (MAX_16_BIT * sample);
        if (sample == 1.0) s = Short.MAX_VALUE;   // special case since 32768 not a short
        buffer[bufferSize++] = (byte) s;
        buffer[bufferSize++] = (byte) (s >> 8);   // little endian

        // send to sound card if buffer is full        
        if (bufferSize >= buffer.length) {
            line.write(buffer, 0, buffer.length);
            bufferSize = 0;
        }
    }

    /**
     * Writes the array of samples (between -1.0 and +1.0) to standard audio.
     * If a sample is outside the range, it will be clipped.
     *
     * @param samples the array of samples to play
     * @throws IllegalArgumentException if any sample is {@code Double.NaN}
     * @throws IllegalArgumentException if {@code samples} is {@code null}
     */
    public static void play(double[] samples) {
        if (samples == null) throw new IllegalArgumentException("argument to play() is null");
        for (double sample : samples) {
            play(sample);
        }
    }

    /**
     * Plays an audio file (in .wav, .mid, or .au format) in a background thread.
     *
     * @param filename the name of the audio file
     * @throws IllegalArgumentException if unable to play {@code filename}
     * @throws IllegalArgumentException if {@code filename} is {@code null}
     */
    public static synchronized void play(final String filename) {
        new Thread(() -> {
            AudioInputStream ais = getAudioInputStreamFromFile(filename);
            stream(ais);
        }).start();
    }


    // https://www3.ntu.edu.sg/home/ehchua/programming/java/J8c_PlayingSound.html
    // play a wav or aif file
    // javax.sound.sampled.Clip fails for long clips (on some systems), perhaps because
    // JVM closes (see remedy in loop)
    private static void stream(AudioInputStream ais) {
        SourceDataLine line = null;
        int BUFFER_SIZE = 4096; // 4K buffer

        try {
            AudioFormat audioFormat = ais.getFormat();
            DataLine.Info info = new DataLine.Info(SourceDataLine.class, audioFormat);
            line = (SourceDataLine) AudioSystem.getLine(info);
            line.open(audioFormat);
            line.start();
            byte[] samples = new byte[BUFFER_SIZE];
            int count = 0;
            while ((count = ais.read(samples, 0, BUFFER_SIZE)) != -1) {
                line.write(samples, 0, count);
            }
        } catch (IOException | LineUnavailableException e) {
            e.printStackTrace();
        } finally {
            if (line != null) {
                line.drain();
                line.close();
            }
        }
    }

    /**
     * Loops an audio file (in .wav, .mid, or .au format) in a background thread.
     *
     * @param filename the name of the audio file
     * @throws IllegalArgumentException if {@code filename} is {@code null}
     */
    public static synchronized void loop(String filename) {
        if (filename == null) throw new IllegalArgumentException();

        final AudioInputStream ais = getAudioInputStreamFromFile(filename);

        try {
            Clip clip = AudioSystem.getClip();
            // Clip clip = (Clip) AudioSystem.getLine(new Line.Info(Clip.class));
            clip.open(ais);
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        } catch (LineUnavailableException | IOException e) {
            e.printStackTrace();
        }

        // keep JVM open
        new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }


    /***************************************************************************
     * Unit tests {@code StdAudio}.
     ***************************************************************************/

    // create a note (sine wave) of the given frequency (Hz), for the given
    // duration (seconds) scaled to the given volume (amplitude)
    private static double[] note(double hz) {
        int n = (int) (StdAudio.SAMPLE_RATE * 1.0);
        double[] a = new double[n + 1];
        for (int i = 0; i <= n; i++)
            a[i] = 0.5 * Math.sin(2 * Math.PI * i * hz / StdAudio.SAMPLE_RATE);
        return a;
    }

    /**
     * Test client - play an A major scale to standard audio.
     *
     * @param args the command-line arguments
     */
    public static void main(String[] args) {

        // 440 Hz for 1 sec
        double freq = 440.0;
        for (int i = 0; i <= StdAudio.SAMPLE_RATE; i++) {
            StdAudio.play(0.5 * Math.sin(2 * Math.PI * freq * i / StdAudio.SAMPLE_RATE));
        }

        // scale increments
        int[] steps = {0, 2, 4, 5, 7, 9, 11, 12};
        for (int step : steps) {
            double hz = 440.0 * Math.pow(2, step / 12.0);
            StdAudio.play(note(hz));
        }


        // need to call this in non-interactive stuff so the program doesn't terminate
        // until all the sound leaves the speaker.
        StdAudio.close();
    }
}
