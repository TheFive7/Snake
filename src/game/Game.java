package game;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

import static game.Controller.isTimelinePause;
import static game.Controller.timeline;
import static game.App.canvas;
import static game.Donnees.*;
import static game.Draw.*;

public class Game {

    // Item
    static Nourriture food = new Nourriture();
    static Bombe bombe = new Bombe();

    /* Paramètres à changer */
    public static boolean sonMode = true;
    public static boolean reseauMode = false;
    public static boolean versusMode = false;
    public static boolean collisionRocher = true;
    public static int nbRocher = 0;
    public static int nbFramePerSecond = 1000/60;
    public static double VITESSE = 5;
    public static boolean modeAngle = true;

    public static GraphicsContext gc = canvas.getGraphicsContext2D();
    public static List<Point2D.Double> listRocher = new ArrayList<>();
    public static boolean gameOver = false;
    public static int nbParties = 0;
    public static boolean obstacle = false;
    public static Color couleurSnake = Color.RED;
    public static int time = 0;

    public static Snake snake;
    public static Snake snake1;

    public static void run() {

        // 1P
        if (snake.getGameOver()){gameOver = true;}
        if (gameOver) {
            gc.setFill(Color.BLACK);
            gc.setFont(new Font(50));
            gc.fillText("Game Over !", (LARGEUR >> 1) - 120.0, HAUTEUR >> 1);
            if (snake.getDirection() == SPACE) {
                reset();
                new Thread().start();
            }
            return;
        }
        if (!versusMode) {snake.isGameOver();}

        // 2P
        if (versusMode){
            snake.isGameOver(snake1);
            snake1.isGameOver(snake);
            if (snake1.getGameOver()){
                snake1.resetSnake();
                snake1.addTaillePosition(LARGEUR >> 2, LARGEUR >> 1);
                snake1.drawSnake(gc);
            }
        }

        // Graphismes
        drawAll(gc);
        genereRocher();

        food.draw(food.getImg());

        snake.drawSnake(gc);
        if (versusMode){snake1.drawSnake(gc);}

        // Bombe
        if(time%600==0){ bombe.genere(); }
        bombe.draw(bombe.getImg());
        bombe.affecte(snake);
        if(versusMode){bombe.affecte(snake1);}

        // IA
        if (versusMode){snake1.iaBasique();}

        // Pour que le corps suive la tête
        snake.continu();
        if (versusMode){snake1.continu();}

        // Direction du Snake (Sa vélocité)
        snake.direction();
        if (versusMode){snake1.direction();}

        // Savoir si le Snake a mangé la nourriture
        food.affecte(snake);
        if (versusMode){food.affecte(snake1);}

        time++;
        if (time == 1){snake.addTaille(5);if (versusMode){snake1.addTaille(5);}}
    }


    /**
     * Génère des rochers aléatoirement sur la map. LAPTOP-L9DAUN1I/192.168.56.1
     */
    private static void genereRocher(){
        if (nbParties == 0){
            int xRocher,yRocher;
            for (int i = 0; i <= nbRocher-1;i++) {
                int a, b;
                a = (int) (Math.random() * LIGNE - RATIO);
                b = (int) (Math.random() * COLONNE - RATIO);
                if (a <= 0) {
                    a += RATIO;
                }
                if (b <= 0) {
                    b += RATIO;
                }
                if ((a == LIGNE/2 && b == COLONNE/2) || (a == LIGNE/3 && b == COLONNE/3)){
                    a+=TAILLE_CARRE;
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
        snake.resetSnake();snake.addTaillePosition(LARGEUR >> 1, LARGEUR >> 1);
        if(versusMode){snake1.resetSnake();snake1.addTaillePosition(LARGEUR >> 2, LARGEUR >> 1);}

        drawAll(gc);
        snake.drawSnake(gc);
        if(versusMode){snake1.drawSnake(gc);}

        food.newFruit();

        isTimelinePause = true;
        timeline.pause();
    }

    /**
     * Vérifie si 2 entités sont proches.
     * @param x1 : Abscisse 1.
     * @param y1 : Ordonnée 1.
     * @param x2 : Abscisse 2.
     * @param y2 : Ordonnée 2.
     * @return : true ou false
     */
    public static boolean voisinage(double x1, double y1, double x2, double y2){
        double n;
        if (x1 > x2){n=x2;x2=x1;x1=n;}
        if (y1 > y2){n=y2;y2=y1;y1=n;}
        boolean bool = false;
        if ((x2 - x1 <= HAUTEURSERPENT) && (y2 - y1 <= HAUTEURSERPENT)){
            bool = true;
        }
        return bool;
    }

    /**
     * Affiche un message.
     * @param txt : Texte à écrire
     */
    public static void msg(String txt){
        System.out.println(txt);
    }
}
