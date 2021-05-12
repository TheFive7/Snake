package game;

import javafx.beans.value.ObservableValue;
import javafx.scene.control.Alert;
import javafx.scene.control.MenuBar;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import static game.Controller.isTimelinePause;
import static game.Controller.timeline;
import static game.Game.*;

public class Options {
    public static HBox menuBarre;

    public Options(){
        // Aide
        javafx.scene.control.Menu mn0 = new javafx.scene.control.Menu("Aide");
        javafx.scene.control.CheckMenuItem mi01 = new javafx.scene.control.CheckMenuItem("Touches");
        javafx.scene.control.CheckMenuItem mi02 = new javafx.scene.control.CheckMenuItem("Crédits");
        String messageTouches =
                """
                Aller en HAUT           :  Z  ou  ⬆
                Aller en BAS            :  S  ou  ⬇
                Aller à GAUCHE          :  Q  ou  ⬅
                Aller à DROITE          :  D  ou  ➡
                Play, Pause, Restart    :  ESPACE
                Quitter                 :  ECHAP
                """;
        String messageCredits =
                """
                Projet réalisé dans le cadre de la 1ère année en DUT Informatique de Belfort.
                2020/2021 - S2B1
                
                Tuteur: HILDENBRAND Jérome
                Chef de Projet: HENNEQUIN Maxime 
                
                Equipe:
                BARCON Baptiste
                JACQUOT Maxime
                JARQUIS Peter
                KLEIN Guillaume
                """;

        mi01.selectedProperty().addListener((ObservableValue<? extends Boolean> ov, Boolean old_val, Boolean new_val) -> {
            isTimelinePause = true;
            timeline.pause();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setX(700);alert.setY(890 >> 1);
            alert.setTitle("Touches");
            alert.setHeaderText(null);
            alert.setContentText(messageTouches);
            alert.showAndWait();
        });
        mi02.selectedProperty().addListener((ObservableValue<? extends Boolean> ov, Boolean old_val, Boolean new_val) -> {
            isTimelinePause = true;
            timeline.pause();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setX(700);alert.setY(890 >> 1);
            alert.setTitle("Crédits");
            alert.setHeaderText(null);
            alert.setContentText(messageCredits);
            alert.showAndWait();
        });
        mn0.getItems().addAll(mi01,mi02);

        // IA
        javafx.scene.control.Menu mn1 = new javafx.scene.control.Menu("IA");
        javafx.scene.control.CheckMenuItem mi1 = new javafx.scene.control.CheckMenuItem("IA");
        mi1.selectedProperty().addListener((ObservableValue<? extends Boolean> ov, Boolean old_val, Boolean new_val) -> {
            versusMode = !versusMode;
            Game.reset();
        });
        mn1.getItems().add(mi1);

        // Vitesse
        javafx.scene.control.Menu mn2 = new javafx.scene.control.Menu("Vitesse");
        javafx.scene.control.CheckMenuItem mi21 = new javafx.scene.control.CheckMenuItem("0.25");
        javafx.scene.control.CheckMenuItem mi22 = new javafx.scene.control.CheckMenuItem("0.5");
        javafx.scene.control.CheckMenuItem mi23 = new javafx.scene.control.CheckMenuItem("1");

        /* Ecouteurs */
        mi21.selectedProperty().addListener((ObservableValue<? extends Boolean> ov, Boolean old_val, Boolean new_val) -> {VITESSE = 0.25;Game.reset();mi21.setSelected(false);});
        mi22.selectedProperty().addListener((ObservableValue<? extends Boolean> ov, Boolean old_val, Boolean new_val) -> {VITESSE = 0.5;Game.reset();mi22.setSelected(false);});
        mi23.selectedProperty().addListener((ObservableValue<? extends Boolean> ov, Boolean old_val, Boolean new_val) -> {VITESSE = 1;Game.reset();mi23.setSelected(false);});

        mn2.getItems().addAll(mi21,mi22,mi23);


        // Couleur
        javafx.scene.control.Menu mn3 = new javafx.scene.control.Menu("Couleur");
        javafx.scene.control.CheckMenuItem mi31 = new javafx.scene.control.CheckMenuItem("Rouge");
        javafx.scene.control.CheckMenuItem mi32 = new javafx.scene.control.CheckMenuItem("Bleu");
        javafx.scene.control.CheckMenuItem mi33 = new javafx.scene.control.CheckMenuItem("Vert");
        javafx.scene.control.CheckMenuItem mi34 = new javafx.scene.control.CheckMenuItem("Rose");
        javafx.scene.control.CheckMenuItem mi35 = new javafx.scene.control.CheckMenuItem("Orange");
        javafx.scene.control.CheckMenuItem mi36 = new javafx.scene.control.CheckMenuItem("Blanc");

        /* Ecouteurs */
        mi31.selectedProperty().addListener((ObservableValue<? extends Boolean> ov, Boolean old_val, Boolean new_val) -> {snake.setCouleur(Color.RED);snake1.setCouleur(Color.BLUE);Game.reset();mi31.setSelected(false);});
        mi32.selectedProperty().addListener((ObservableValue<? extends Boolean> ov, Boolean old_val, Boolean new_val) -> {snake.setCouleur(Color.BLUE);snake1.setCouleur(Color.RED);Game.reset();mi32.setSelected(false);});
        mi33.selectedProperty().addListener((ObservableValue<? extends Boolean> ov, Boolean old_val, Boolean new_val) -> {snake.setCouleur(Color.GREEN);Game.reset();mi33.setSelected(false);});
        mi34.selectedProperty().addListener((ObservableValue<? extends Boolean> ov, Boolean old_val, Boolean new_val) -> {snake.setCouleur(Color.PINK);Game.reset();mi34.setSelected(false);});
        mi35.selectedProperty().addListener((ObservableValue<? extends Boolean> ov, Boolean old_val, Boolean new_val) -> {snake.setCouleur(Color.ORANGE);Game.reset();mi35.setSelected(false);});
        mi36.selectedProperty().addListener((ObservableValue<? extends Boolean> ov, Boolean old_val, Boolean new_val) -> {snake.setCouleur(Color.WHITE);Game.reset();mi36.setSelected(false);});

        mn3.getItems().addAll(mi31,mi32,mi33,mi34,mi35,mi36);

        // Ajouts à la MenuBar
        javafx.scene.control.MenuBar mb = new MenuBar();
        mb.getMenus().addAll(mn0,mn1,mn2,mn3);
        // mb.setPrefWidth(800);
        menuBarre = new HBox(mb);
    }
}
