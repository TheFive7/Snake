package game;

import game.menu.Menu;
import game.menu.Options;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import game.reseau.Connexion;

import static game.Donnees.HAUTEUR;
import static game.Donnees.LARGEUR;
import static game.Game.*;
import static game.menu.Options.menuBarre;
import static game.ReadRepertory.listMap;

/* TODO
    x Fond (glace, mer, plaine, foret, montagne, espace)
    x Directions
    o Menu replay
    x Menu Fond
    o LAN
    o Sons
    x Collisions
    o Rochers
    x Bombes
    o IA
*/

// V_3.6
// Collisions avec IA
// Spawner de l'IA
// Corrections dans le menu
// Debut du reseau

public final class App extends Application {
    public static Image backgroundImage;
    public static StackPane root = new StackPane();
    public static Scene scene = new Scene(root);
    public static Canvas canvas = new Canvas(LARGEUR, HAUTEUR);
    public static Menu menu;
    public int POSX = 500;
    public int POSY = 100;
    public int HAUTEURSTAGE = 865;
    public double TRANSLATIONY = 15;

    /**
     * Initialise le stage primaire.
     */
    public static void initial() {

        snake = new Snake(25, couleurSnake, couleurSnake);
        snake1 = new Snake(25, Color.BLUE, Color.BLUE);

        // Place le Snake
        snake.addTaillePosition(LARGEUR >> 1, LARGEUR >> 1);
        if (versusMode) {
            snake1.addTaillePosition(LARGEUR >> 2, LARGEUR >> 1);
        }
        initVitesse(vitesse_initiale);

        // Repertoire images Nourriture et Map
        new ReadRepertory();

        // Gestion des touches
        new Controller();

        // Fond Map
        int r = (int) (Math.random() * listMap.length);
        backgroundImage = new Image("file:src/img/imgMap/" + listMap[r], LARGEUR, 865, false, true);

        // Items générés de base
        food.newFruit();
        bombe.newBombe();

        // Lancement
        if (sonMode) {
            StdAudio.loop("src/sons/play.wav");
        }
        Game.run();
    }

    public static void main(String[] args) {
        menu = new Menu();
    }

    /**
     * Start l'application.
     */
    public static void begin() {
        try {
            launch();
        } catch (Exception e) {
            System.exit(0);
        }
    }

    @Override
    public void start(final Stage primaryStage) {

        if (reseauMode) {
            new Connexion();
        } else {
            new Options(primaryStage);
        }

        // Calibrage
        primaryStage.setX(POSX);
        primaryStage.setY(POSY);
        primaryStage.setHeight(HAUTEURSTAGE);
        canvas.setTranslateY(TRANSLATIONY);

        // Stage primaire
        primaryStage.setOnCloseRequest(evt -> System.exit(0));
        primaryStage.setScene(scene);
        primaryStage.setTitle("~ SNAKE ~");
        try {
            primaryStage.getIcons().add(new Image("file:src/img/iconSnake.PNG"));
        } catch (Exception e) {
            msg("Icone non répertoriée");
        }
        primaryStage.setResizable(false);
        root.getChildren().addAll(menuBarre, canvas);
        primaryStage.show();

        initial();
    }
}
