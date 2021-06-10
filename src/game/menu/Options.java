package game.menu;

import game.Game;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.control.MenuBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.util.Duration;

import static game.Controller.isTimelinePause;
import static game.Controller.timeline;
import static game.Donnees.*;
import static game.Game.*;

public class Options {
    public static HBox menuBarre;

    public void generiqueCredits(ScrollPane sp){
        sp.setVvalue(sp.getVvalue() + 1);
    }

    public Options(StackPane root) {
        // Aide
        javafx.scene.control.Menu mn0 = new javafx.scene.control.Menu("Infos");
        javafx.scene.control.CheckMenuItem mi01 = new javafx.scene.control.CheckMenuItem("Infos");
        javafx.scene.control.CheckMenuItem mi02 = new javafx.scene.control.CheckMenuItem("Crédits");

        // Regles
        mi01.selectedProperty().addListener(e -> {
            isTimelinePause = true;
            timeline.pause();
            Image imgJeu = new Image("file:src/img/menu/jeu.png", LARGEUR, 800, false, true);
            gc.drawImage(imgJeu, 0, 0);
        });

        // Credits
        mi02.selectedProperty().addListener(e -> {
            isTimelinePause = true;
            timeline.pause();
            Image credits = new Image("file:src/img/menu/credits.png");
            ScrollPane sp = new ScrollPane();
            sp.setContent(new ImageView(credits));
            sp.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
            sp.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
            root.getChildren().add(sp);
            sp.setVmin(0);sp.setVmax(800);sp.setVvalue(0);

            sp.setOnKeyPressed(event -> {
                KeyCode key = event.getCode();
                if (key == KeyCode.SPACE) {
                    root.getChildren().remove(sp);
                    isTimelinePause = false;
                    timeline.play();
                }
            });

            int cpt = 0;
            Timeline t = new Timeline(new KeyFrame(Duration.millis(1000/60.0), ex -> generiqueCredits(sp)));
            t.setCycleCount(50000000);
            t.play();
        });
        mn0.getItems().addAll(mi01, mi02);


        // IA
        javafx.scene.control.Menu mn1 = new javafx.scene.control.Menu("IA");
        javafx.scene.control.CheckMenuItem mi11 = new javafx.scene.control.CheckMenuItem("IA");
        mi11.selectedProperty().addListener(e -> {
            versusMode = !versusMode;
            Game.reset();
        });
        mn1.getItems().add(mi11);

        // Vitesse
        javafx.scene.control.Menu mn2 = new javafx.scene.control.Menu("Vitesse");
        javafx.scene.control.CheckMenuItem mi21 = new javafx.scene.control.CheckMenuItem("TROP LENT");
        javafx.scene.control.CheckMenuItem mi22 = new javafx.scene.control.CheckMenuItem("LENT");
        javafx.scene.control.CheckMenuItem mi23 = new javafx.scene.control.CheckMenuItem("NORMAL");
        javafx.scene.control.CheckMenuItem mi24 = new javafx.scene.control.CheckMenuItem("RAPIDE");
        javafx.scene.control.CheckMenuItem mi25 = new javafx.scene.control.CheckMenuItem("HARDCORE");

        /* Ecouteurs */
        mi21.selectedProperty().addListener(e -> {
            initVitesse(1);
            Game.reset();
            mi21.setSelected(false);
        });
        mi22.selectedProperty().addListener(e -> {
            initVitesse(2.5);
            Game.reset();
            mi22.setSelected(false);
        });
        mi23.selectedProperty().addListener(e -> {
            initVitesse(5);
            Game.reset();
            mi23.setSelected(false);
        });
        mi24.selectedProperty().addListener(e -> {
            initVitesse(8);
            Game.reset();
            mi24.setSelected(false);
        });
        mi25.selectedProperty().addListener(e -> {
            initVitesse(10);
            Game.reset();
            mi25.setSelected(false);
        });

        mn2.getItems().addAll(mi21, mi22, mi23, mi24, mi25);


        // Couleur
        javafx.scene.control.Menu mn3 = new javafx.scene.control.Menu("Couleur");
        javafx.scene.control.CheckMenuItem mi31 = new javafx.scene.control.CheckMenuItem("Rouge");
        javafx.scene.control.CheckMenuItem mi32 = new javafx.scene.control.CheckMenuItem("Bleu");
        javafx.scene.control.CheckMenuItem mi33 = new javafx.scene.control.CheckMenuItem("Vert");
        javafx.scene.control.CheckMenuItem mi34 = new javafx.scene.control.CheckMenuItem("Rose");
        javafx.scene.control.CheckMenuItem mi35 = new javafx.scene.control.CheckMenuItem("Orange");
        javafx.scene.control.CheckMenuItem mi36 = new javafx.scene.control.CheckMenuItem("Blanc");

        /* Ecouteurs */
        mi31.selectedProperty().addListener(e -> {
            snake.setCouleur(Color.RED);
            snake1.setCouleur(Color.BLUE);
            Game.reset();
            mi31.setSelected(false);
        });
        mi32.selectedProperty().addListener(e -> {
            snake.setCouleur(Color.BLUE);
            snake1.setCouleur(Color.RED);
            Game.reset();
            mi32.setSelected(false);
        });
        mi33.selectedProperty().addListener(e -> {
            snake.setCouleur(Color.GREEN);
            Game.reset();
            mi33.setSelected(false);
        });
        mi34.selectedProperty().addListener(e -> {
            snake.setCouleur(Color.PINK);
            Game.reset();
            mi34.setSelected(false);
        });
        mi35.selectedProperty().addListener(e -> {
            snake.setCouleur(Color.ORANGE);
            Game.reset();
            mi35.setSelected(false);
        });
        mi36.selectedProperty().addListener(e -> {
            snake.setCouleur(Color.WHITE);
            Game.reset();
            mi36.setSelected(false);
        });

        mn3.getItems().addAll(mi31, mi32, mi33, mi34, mi35, mi36);

        // Angle
        javafx.scene.control.Menu mn4 = new javafx.scene.control.Menu("Angle");
        javafx.scene.control.CheckMenuItem mi41 = new javafx.scene.control.CheckMenuItem("Angle");
        mi41.selectedProperty().addListener(e -> {
            modeAngle = !modeAngle;
            Game.reset();
        });
        mn4.getItems().add(mi41);

        // Quitter
        javafx.scene.control.Menu mn5 = new javafx.scene.control.Menu("Quitter");
        javafx.scene.control.CheckMenuItem mi51 = new javafx.scene.control.CheckMenuItem("EXIT");
        mi51.selectedProperty().addListener(e -> System.exit(0));
        mn5.getItems().addAll(mi51);

        // Ajouts à la MenuBar
        javafx.scene.control.MenuBar mb = new MenuBar();
        mb.getMenus().addAll(mn0, mn1, mn2, mn3, mn4, mn5);
        menuBarre = new HBox(mb);
    }
}
