package game;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

import static game.App.canvas;
import static game.Controller.*;
import static game.Donnees.*;
import static game.Draw.*;
import static game.Draw.drawScore;
import static game.Food.drawFruit;
import static game.ReadRepertoryFruit.listFruit;

public class Game {

    /* Paramètres à changer */
    public static boolean reseauMode = false;
    public static boolean versusMode = false;
    public static boolean collisionRocher = true;
    public static int nbRocher = 0;
    public static int nbFramePerSecond = 60;
    public static double VITESSE = 1;

    public static GraphicsContext gc = canvas.getGraphicsContext2D();
    public static List<Point2D.Double> listRocher = new ArrayList<>();
    public static boolean gameOver = false;
    public static int nbParties = 0;
    public static boolean obstacle = false;
    public static Color couleurSnake = Color.RED;

    public static Snake snake;
    public static Snake snake1;

    public static void run() {
        // 1P
        if (!versusMode) {
            snake.isGameOver();
            if (snake.getGameOver()) {
                gameOver = true;
            }
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
        }

        // 2P
        if (versusMode){
            snake.isGameOver(snake1);
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

            snake1.isGameOver(snake);
            if (snake1.getGameOver()){
                snake1.resetSnake();
                snake1.addTaillePosition(LIGNE / 3, COLONNE / 3);
                snake1.drawSnake(gc);
            }
        }

        // Graphismes
        drawBackGround(gc);
        drawSpawn(gc);
        genereRocher();
        drawFruit(gc, listFruit[Food.rand]);
        snake.drawSnake(gc);
        if (versusMode){snake1.drawSnake(gc);}
        drawScore(gc);

        // IA
        if (versusMode){snake1.iaBasique();}

        // Pour que le corps suive la tête
        snake.continu();
        if (versusMode){snake1.continu();}

        // Direction du Snake (Sa vélocité)
        snake.direction();
        if (versusMode){snake1.direction();}

        // Savoir si le Snake a mangé la nourriture
        snake.mange();
        if (versusMode){snake1.mange();}

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
        obstacle = false;
        gameOver = false;
        snake.resetSnake();snake.addTaillePosition(LIGNE >> 1, COLONNE >> 1);
        if(versusMode){snake1.resetSnake();snake1.addTaillePosition(LIGNE / 3, COLONNE / 3);}
        drawBackGround(gc);
        drawSpawn(gc);
        snake.drawSnake(gc);
        if(versusMode){snake1.drawSnake(gc);}
        drawScore(gc);
        drawFruit(gc, listFruit[Food.rand]);

        isTimelinePause = true;
        drawEtat();
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
        if ((x2 - x1 < RATIO) && (y2 - y1 < RATIO)){
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
