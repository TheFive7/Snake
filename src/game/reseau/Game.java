package game.reseau;

import game.App;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static game.App.backgroundImage;
import static game.App.canvas;
import static game.reseau.Controller.isTimelinePause;
import static game.reseau.Controller.timeline;
import static game.reseau.Donnees.*;
import static game.reseau.ReadRepertory.listMap;
import static game.reseau.Snake.pointsUsed;
import static game.reseau.Draw.drawAll;
import static game.reseau.Draw.drawRocher;

public class Game {

    /* Paramètres à changer */
    public static boolean sonMode = true;
    public static boolean reseauMode = false;
    public static boolean versusMode = false;
    public static boolean collisionRocher = true;
    public static int nbRocher = 0;
    public static int nbFramePerSecond = 1000 / 60;
    public static boolean modeAngle = false;

    public static double vitesse_initiale = 5;
    public static GraphicsContext gc = canvas.getGraphicsContext2D();
    public static List<Point2D.Double> listRocher = new ArrayList<>();
    public static boolean obstacle = false;
    public static boolean gameOver = false;
    public static int nbParties = 0;
    public static Color couleurSnake = Color.RED;
    public static int time = 0;
    public static Snake snake;
    public static Snake snake1;

    // Item
    public static Nourriture food = new Nourriture();
    public static Bombe bombe = new Bombe("longueur");

    public Game() {
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

        run();
    }

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

        // Graphismes
        drawAll(gc);

        food.draw(food.getImg());

        snake.drawSnake(gc);

        // Bombe
        if (time % 600 == 0) {
            double random = Math.random();
            if (random <= 0.25) {
                bombe.setEffet("longueur");
            } else if (0.26 < random && random <= 0.50) {
                bombe.setEffet("vitesse");
            } else if (0.51 < random && random <= 0.75) {
                bombe.setEffet("controle");
            } else if (0.76 < random && random <= 1) {
                bombe.setEffet("mort");
            }
            bombe.genere();
        }

        bombe.draw(bombe.getImg());
        if (bombe.affecte(snake, time)) {
            time = 0;
        }

        // Pour que le corps suive la tête
        snake.continu();

        // Direction du Snake (Sa vélocité)
        snake.direction();

        // Savoir si le Snake a mangé la nourriture
        food.affecte(snake);

        time++;
    }


    /**
     * Génère des rochers aléatoirement sur la map.
     */
    private static void genereRocher() {
        if (nbParties == 0) {
            int xRocher, yRocher;
            for (int i = 0; i <= nbRocher - 1; i++) {
                int a, b;
                a = (int) (Math.random() * LARGEUR - LARGEURSERPENT);
                b = (int) (Math.random() * LARGEUR - LARGEURSERPENT);
                if (a <= 1) {
                    a += LARGEURSERPENT;
                }
                if (b <= 1) {
                    b += LARGEURSERPENT;
                }

                if ((a == LIGNE / 2 && b == COLONNE / 2) || (a == LIGNE / 3 && b == COLONNE / 3)) {
                    a += TAILLE_CARRE;
                }
                xRocher = a;
                yRocher = b;
                listRocher.add(new Point2D.Double(xRocher, yRocher));
            }
            nbParties++;
        }
        for (Point2D.Double point : listRocher) {
            drawRocher(gc, point);
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

        int r = (int) (Math.random() * listMap.length);
        backgroundImage = new Image("file:src/img/imgMap/" + listMap[r], LARGEUR, 865, false, true);
        drawAll(gc);
        snake.drawSnake(gc);

        food.newFruit();

        isTimelinePause = true;
        timeline.pause();
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