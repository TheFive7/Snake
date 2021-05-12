package game;

import javafx.application.Application;
import javafx.scene.layout.StackPane;
import javafx.scene.canvas.Canvas;
import javafx.scene.paint.Color;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.scene.Scene;

import static game.Food.foodGenerator;
import static game.Game.*;
import static game.Donnees.*;
import static game.Options.menuBarre;

public final class App extends Application {

    public static StackPane root = new StackPane();
    public static Scene scene = new Scene(root);
    public static Canvas canvas = new Canvas(LARGEUR, HAUTEUR);

    @Override
    public void start(final Stage primaryStage) {

        if(reseauMode){
            new Connexion();
        } else {
            new Options();
        }

        // Calibrage
        primaryStage.setX(POSX);primaryStage.setY(POSY);
        primaryStage.setHeight(HAUTEURSTAGE);
        canvas.setTranslateY(TRANSLATIONY);

        // Stage primaire
        primaryStage.setScene(scene);
        primaryStage.setTitle("~ SNAKE ~");
        primaryStage.getIcons().add(new Image("iconSnake.PNG"));
        primaryStage.setResizable(true);
        root.getChildren().addAll(menuBarre,canvas);
        primaryStage.show();

        init();
    }

    public void init(){
        snake = new Snake(LARGEURSERPENT * 0.8, couleurSnake, couleurSnake);
        snake1 = new Snake(LARGEURSERPENT * 0.8, Color.BLUE, Color.BLUE);

        // Place le Snake
        snake.addTaillePosition(LIGNE >> 1, COLONNE >> 1);
        if (versusMode){snake1.addTaillePosition(LIGNE / 3, COLONNE / 3);}

        // Nourriture
        new ReadRepertoryFruit();

        // Gestion des touches
        new Controller();

        // Generation de nourriture
        foodGenerator();

        // Lancement
        Game.run();
    }


    public static void main(String[] args) {
        new Menu0();
    }

    public static void begin(){launch();}
}


