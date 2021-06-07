package game.reseau;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

import static game.Game.msg;

public class Serveur extends Thread {
    private static final Scanner input = new Scanner(System.in);

    public Serveur() {
        try {
            int port = 4242;
            int time = 30000;
            msg("Temps d'attente maximum: " + time / 1000 + "s");
            ServerSocket sS = new ServerSocket(port); // Creation du serveur
            sS.setSoTimeout(time); // Temps d'attente en millis qu'un client se connecte (ici 10 sec)
            Socket s = sS.accept(); // Le seveur accepte le client
            msg("Connexion établie avec Succès !");

            PrintWriter out = new PrintWriter(s.getOutputStream()); // Prendre le flux du Socket
            BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream())); // Récupere les infos

            // Partie envoi des messages
            String recu = "";
            String msg = "";
            while (!recu.equals("stop") && !msg.equals("stop")) {
                /* ENVOI */
                System.out.print("-> ");
                msg = input.nextLine();
                out.println(msg); // Affiche un message
                out.flush(); // Envoi le message

                /* RECU */
                recu = in.readLine();
                System.out.println(recu); // Affiche l'info récupérée
            }


            sS.close(); // Fermer le serveur
            s.close(); // Fermer le Socket
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
