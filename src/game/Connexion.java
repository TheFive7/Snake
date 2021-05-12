package game;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextInputDialog;

import java.util.Optional;

import static game.Game.msg;
import static game.Game.reseauMode;
import static game.Serveur.getIP;

public class Connexion {
    public Connexion(){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Veux - tu être le Serveur ?", ButtonType.YES, ButtonType.NO, ButtonType.CANCEL);
        alert.setHeaderText(null);
        alert.showAndWait();

        if (alert.getResult() == ButtonType.YES) {
            // IP
            Alert alert1 = new Alert(Alert.AlertType.INFORMATION, "Ton IP est la suivante : \n\n " + getIP() + "\n\n Assures toi de la communiquer au client pour qu'il puisse se connecter.", ButtonType.OK);
            alert1.setHeaderText(null);
            alert1.showAndWait();
            if (alert1.getResult() == ButtonType.OK) {
                msg("Attente d'un client...");
                new Serveur();
            }
        } else if (alert.getResult() == ButtonType.NO) {
            // Input IP
            TextInputDialog dialogue = new TextInputDialog();
            dialogue.setHeaderText(null);
            dialogue.setTitle("Adresse IP");
            dialogue.setContentText("");
            Optional<String> result = dialogue.showAndWait();

            result.ifPresent(guestString -> {
                msg("Tentative de connexion au Serveur : " + result);
                new Client(String.valueOf(result));
            });
        } else {
            reseauMode = false;
            msg("Annulation du LAN");
            System.exit(0);
        }
    }
}
