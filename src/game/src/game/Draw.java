package game;

import static game.Controller.*;
import static game.Game.*;
import static game.Donnees.*;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import java.awt.geom.Point2D;

public class Draw {

    /**
     * Dessine le fond de la map.
     * @param gc : GraphicsContext
     */
    static void drawBackGround(GraphicsContext gc) {
        for (int i=0; i < LIGNE; i++){
            for (int j=0; j < COLONNE; j++){
                if ((i + j) % 3 == 0) {
                    gc.setFill(Color.web("55A053"));
                } else {
                    gc.setFill(Color.web("22952E"));
                }
                gc.fillRect(i * TAILLE_CARRE, j * TAILLE_CARRE, TAILLE_CARRE, TAILLE_CARRE);
            }
        }
    }

    /**
     * Affiche l'état du jeu (PLAY ou PAUSE)
     */
    static void drawEtat(){
        gc.setFill(Color.BLACK);
        gc.setFont(new Font("arial",20));
        if (isTimelinePause){gc.fillText("Pause",LARGEUR/2.0,20);}
    }

    /**
     * Affiche le score.
     * @param gc : GraphicsContext
     */
    static void drawScore(GraphicsContext gc) {
        gc.setFill(snake.getCouleurTete());
        gc.setFont(new Font("arial",20));
        gc.fillText("Score: " + snake.getScore(), LARGEUR / 30.0 + 2, HAUTEUR / 15.0);
        if(versusMode){ gc.setFill(snake1.getCouleurTete());gc.fillText("Score: " + snake1.getScore(), LARGEUR / 5.0 + 2, HAUTEUR / 15.0);}
    }

    /**
     * Dessine les spawners.
     * @param gc : GraphicsContext
     */
    static void drawSpawn(GraphicsContext gc){
        gc.setFill(snake.getCouleurTete());
        gc.fillRect(LIGNE / 2.0 * TAILLE_CARRE, COLONNE / 2.0 * TAILLE_CARRE, LARGEURSERPENT, LARGEURSERPENT);
        if (versusMode) {
            gc.setFill(snake1.getCouleurTete());
            gc.fillRect(LIGNE / 3 * TAILLE_CARRE, COLONNE / 3 * TAILLE_CARRE, LARGEURSERPENT, LARGEURSERPENT);
        }
    }

    /**
     * Dessine un rocher.
     * @param point : Coordonnées du rocher
     */
    static void drawRocher(GraphicsContext gc, Point2D.Double point){
        gc.setFill(Color.web("424242"));
        gc.fillRoundRect(point.x * TAILLE_CARRE,point.y * TAILLE_CARRE,LARGEURSERPENT,LARGEURSERPENT,15,35);
    }
}

