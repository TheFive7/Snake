package game.reseau;

import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

import static game.Game.msg;

public class Serveur extends Thread {
    private static final Scanner input = new Scanner(System.in);
    public static ObjectOutputStream out;
    public static ObjectInputStream in;

    public Serveur() {
        try {

            int port = 4646;
            int time = 30000;
            msg("Temps d'attente maximum: " + time / 1000 + "s");
            ServerSocket sS = new ServerSocket(port); // Creation du serveur
            sS.setSoTimeout(time); // Temps d'attente en millis qu'un client se connecte (ici 10 sec)
            Socket s = sS.accept(); // Le seveur accepte le client
            msg("Connexion établie avec Succès !");

            out = new ObjectOutputStream(s.getOutputStream()); // Prendre le flux du Socket
            in = new ObjectInputStream(s.getInputStream()); // Récupere les infos

            new Game();

/*            sS.close(); // Fermer le serveur
            s.close(); // Fermer le Socket*/
        } catch (IOException e) {
            msg("Connexion échouée.");
            System.exit(0);
        }
    }

    /**
     * Donne l'adresse IP. (Facultatif)
     */
    public static String getIP() {
        try {
            InetAddress add = InetAddress.getLocalHost(); // Avoir son adresse IP Locale
            return String.valueOf(add);
        } catch (UnknownHostException e) {
            e.printStackTrace();
            return null;
        }
    }

}
