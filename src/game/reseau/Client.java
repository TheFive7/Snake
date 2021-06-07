package game.reseau;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

import static game.Game.msg;

public class Client {
    private static final Scanner input = new Scanner(System.in);

    public Client(String adresseIP) {
        try {
            int port = 4242;
            Socket s = new Socket(adresseIP, port);  // Socket avec le port du serveur et son IP
            System.out.println("Connexion établie avec Succès !");

            BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream())); // Récupere les infos
            PrintWriter out = new PrintWriter(s.getOutputStream()); // Prendre le flux du Socket

            // Partie recu messages
            String recu = "";
            String msg = "";
            while (!recu.equals("stop") && !msg.equals("stop")) {
                /* RECU */
                recu = in.readLine();
                System.out.println(recu); // Affiche l'info récupérée

                /* ENVOI */
                System.out.print("-> ");
                msg = input.nextLine();
                out.println(msg); // Affiche un message
                out.flush(); // Envoi le message
            }


            s.close(); // Fermer le Socket
        } catch (IOException e) {
            msg("Connexion échouée, LAN désactivé.");
            System.exit(0);
        }
    }
}