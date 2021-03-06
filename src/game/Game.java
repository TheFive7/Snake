package game;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static game.App.backgroundImage;
import static game.App.canvas;
import static game.Controller.isTimelinePause;
import static game.Controller.timeline;
import static game.Donnees.*;
import static game.Draw.drawAll;
import static game.ReadRepertory.listMap;
import static game.Snake.pointsUsed;

public class Game  {

    /* Paramètres à changer */
    public static boolean sonMode = true;
    public static boolean reseauMode = false;
    public static boolean versusMode = false;
    public static boolean collisionMur = true;
    public static int nbMurs = 0;
    public static int nbFramePerSecond = 1000 / 60;
    public static boolean modeAngle = false;
    public static boolean inverseControls = false;

    public static double vitesse_initiale = 5;
    public static GraphicsContext gc = canvas.getGraphicsContext2D();
    public static List<Mur> listMur = new ArrayList<>();
    public static boolean obstacle = false;
    public static boolean gameOver = false;
    public static int nbParties = 0;
    public static Color couleurSnake = Color.RED;
    public static int time = 0;
    public static Snake snake;
    public static Snake snake1;

    public static int curseur = 300;

    // Item
    static Nourriture food = new Nourriture();
    static Bombe bombe = new Bombe("longueur");
    static Mur mur = new Mur();

    /**
     * Boucle du jeu.
     */
    public static void run() {
        // 1P
        if (snake.getGameOver()) {
            gameOver = true;
        }
        if (gameOver) {
            gc.setFill(Color.BLACK);
            gc.setFont(Font.loadFont(
                    Objects.requireNonNull(App.class.getResource("minecraft_font.ttf")).toExternalForm(),
                    50
            ));
            gc.fillText("Game Over !", (LARGEUR >> 1) - 140.0, (HAUTEUR >> 1) - 100.0);
            if (snake.getDirection() == SPACE) {
                reset();
                new Thread().start();
            }
            return;
        }
        if (!versusMode) {
            snake.isGameOver();
        }

        if (snake1.getGameOver()){curseur --;}
        // 2P
        if (versusMode) {
            snake.isGameOver(snake1);
            snake1.isGameOver(snake);
            if (snake1.getGameOver()) {
                versusMode = false;
            }
        }
        if (curseur == 0){
            versusMode = true;
            snake1.resetSnake();
            snake1.addTaillePosition(LARGEUR >> 2, LARGEUR >> 1);
            snake1.addTaille(5);
            curseur = 300;
        }

        // Murs
        mur.genereMur();

        // Bombe
        bombe.effect();

        // Graphismes
        drawAll(gc);
        mur.drawAll();
        food.draw(food.getImg());
        bombe.draw(bombe.getImg());
        snake.drawSnake(gc);
        if (versusMode) {snake1.drawSnake(gc);}

        // Bombe
        if (bombe.affecte(snake, time)) {
            time = 0;
        }
        if (versusMode) {
            bombe.affecte(snake1, time);
        }

        // IA
        if (versusMode) {
            pointsUsed();
            snake1.iaBasique();
        }

        // Pour que le corps suive la tête
        snake.continu();
        if (versusMode) {
            snake1.continu();
        }

        // Direction du Snake (Sa vélocité)
        snake.direction();
        if (versusMode) {
            snake1.direction();
        }

        // Savoir si le Snake a mangé la nourriture
        food.affecte(snake);
        if (versusMode) {
            food.affecte(snake1);
        }

        time++;
        if (time == 1) {
            snake.addTaille(5);
            if (versusMode) {
                snake1.addTaille(5);
            }
        }
    }

    /**
     * Reset les paramètres.
     */
    public static void reset() {
        time = 0;
        obstacle = false;
        gameOver = false;
        snake.resetSnake();
        snake.addTaillePosition(LARGEUR >> 1, LARGEUR >> 1);
        if (versusMode) {
            snake1.resetSnake();
            snake1.addTaillePosition(LARGEUR >> 2, LARGEUR >> 1);
        }

        int r = (int) (Math.random() * listMap.length);
        backgroundImage = new Image("file:src/img/imgMap/" + listMap[r], LARGEUR, 865, false, true);
        drawAll(gc);
        snake.drawSnake(gc);
        if (versusMode) {
            snake1.drawSnake(gc);
        }

        food.newFruit();

        isTimelinePause = true;
        timeline.pause();
        musique_principale = new MediaPlayer(m1);
        musique_principale.setAutoPlay(true);
    }

    /**
     * Vérifie si 2 entités sont proches.
     *
     * @param x1 : Abscisse 1.
     * @param y1 : Ordonnée 1.
     * @param x2 : Abscisse 2.
     * @param y2 : Ordonnée 2.
     * @return : true ou false
     */
    public static boolean voisinage(double x1, double y1, double x2, double y2) {
        double n;
        if (x1 > x2) {
            n = x2;
            x2 = x1;
            x1 = n;
        }
        if (y1 > y2) {
            n = y2;
            y2 = y1;
            y1 = n;
        }
        return (x2 - x1 <= HAUTEURSERPENT) && (y2 - y1 <= HAUTEURSERPENT);
    }

    public static void initVitesse(double v){
        vitesse_initiale = v;
        snake.setVitesse(v);
        snake1.setVitesse(v);
    }

    /**
     * Affiche un message.
     *
     * @param txt : Texte à écrire
     */
    public static void msg(String txt) {
        System.out.println(txt);
    }
}
