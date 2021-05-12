package game;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import java.awt.geom.Point2D;
import static game.Game.*;
import static game.Donnees.*;
import static game.ReadRepertoryFruit.listFruit;

public class Food {
    static int foodX;
    static int foodY;
    static int rand = (int) (Math.random() * listFruit.length);

    /**
     * dessine la nourriture.
     * @param gc : graphicscontext
     * @param s : type de fruit
     */
    public static void drawFruit(GraphicsContext gc, String s) {
        Image img = new Image("file:src/img/"+ s,LARGEURSERPENT,LARGEURSERPENT,false,true);
        gc.drawImage(img,foodX * TAILLE_CARRE,foodY * TAILLE_CARRE);
    }

    /**
     * Génère UN bloc de nourriture aléatoirement mais pas sur le snake.
     */
    public static void foodGenerator() {
        int shouldBreak = 0;
        do {
            int a = (int) (Math.random() * LIGNE - RATIO);
            int b = (int) (Math.random() * COLONNE - RATIO);
            if (a <= 0){a += RATIO;}
            if (b <= 0){b +=  RATIO;}
            foodX = a;foodY = b;
            if (!versusMode){
                for (Point2D snake : snake.getTailleSnake()) {
                    if (snake.getX() != foodX && snake.getY() != foodY) {
                        shouldBreak = 1;
                    }
                }
            } else {
                for (Point2D snake : snake.getTailleSnake()) {
                    if (snake.getX() != foodX && snake.getY() != foodY) {shouldBreak = 1;}
                }
                for (Point2D snake : snake1.getTailleSnake()) {
                    if (snake.getX() != foodX && snake.getY() != foodY) {shouldBreak = 1;}
                }
            }
        } while (shouldBreak != 1);
        rand = (int) (Math.random() * listFruit.length);
    }

    /**
     * Génère un nouveau fruit aléatoire.
     */
    public static void newFruit(){
        Food.foodGenerator();
        int rand = (int) (Math.random() * listFruit.length);
        Food.drawFruit(gc, listFruit[rand]);
    }
}

