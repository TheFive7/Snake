package game;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import static game.Donnees.*;
import static game.Game.*;
import static game.Options.menuBarre;

/* TODO
    o Fond (glace, mer, plaine, foret, montagne, espace)
    o Directions
    o Menu
    o LAN
    o Sons
    x Collisions
    o Rochers
    o Bombes
*/

public final class App extends Application {
    public int POSX = 500;
    public int POSY = 100;
    public int HAUTEURSTAGE = 865;
    public double TRANSLATIONY = 15;

    public static StackPane root = new StackPane();
    public static Scene scene = new Scene(root);
    public static Canvas canvas = new Canvas(LARGEUR, HAUTEUR);
    public static Menu menu;
    public static boolean isLaunch = false;

    @Override
    public void start(final Stage primaryStage) {

        // Passage à 60fps
        // Vitesses ajoutées
        // Bugs et latences réglées
        // Directions remaniées
        // Classes remaniées
        // Colisions revues

        if(reseauMode){new Connexion();}else{new Options(primaryStage);}

        // Calibrage
        primaryStage.setX(POSX);primaryStage.setY(POSY);
        primaryStage.setHeight(HAUTEURSTAGE);
        canvas.setTranslateY(TRANSLATIONY);

        // Stage primaire
        primaryStage.setOnCloseRequest(evt -> System.exit(0));
        primaryStage.setScene(scene);
        primaryStage.setTitle("~ SNAKE ~");
        try {primaryStage.getIcons().add(new Image("file:src/img/iconSnake.PNG"));} catch(Exception e){msg("Icone non répertoriée");}
        primaryStage.setResizable(false);
        root.getChildren().addAll(menuBarre, canvas);
        primaryStage.show();

        initial();
    }

    public static void initial(){

        snake = new Snake(25, couleurSnake, couleurSnake);
        snake1 = new Snake(25, Color.BLUE, Color.BLUE);

        // Place le Snake
        snake.addTaillePosition(LARGEUR >> 1, LARGEUR >> 1);
        if (versusMode){snake1.addTaillePosition(LARGEUR >> 2, LARGEUR >> 1);}

        // Repertoire images Nourriture
        new ReadRepertoryFruit();

        // Gestion des touches
        new Controller();

        // Items générés de base
        food.newFruit();
        bombe.newBombe();

        // Lancement
        isLaunch=true;
        if(sonMode){StdAudio.loop("src/sons/play.wav");}
        Game.run();
    }


    public static void main(String[] args) {menu = new Menu();}

    public static void begin(){
        try {
            launch();
        } catch (Exception e) {
            System.exit(0);
        }
    }
}
