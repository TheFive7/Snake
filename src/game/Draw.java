package game;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.awt.geom.Point2D;

import static game.App.canvas;
import static game.Controller.isTimelinePause;
import static game.Donnees.*;
import static game.Game.*;

public class Draw {

    /**
     * Dessine la map, le score, l'état, le spawn et les rochers.
     * @param gc : GraphicsContext
     */
    static void drawAll(GraphicsContext gc) {
        // BackGround
        gc.setFill(Color.web("55A053"));
        gc.fillRect(0,0,canvas.getWidth(),canvas.getHeight());

        // Score
        gc.setFill(snake.getCouleurTete());
        gc.setFont(new Font("arial",20));
        gc.fillText("Score: " + snake.getScore(), LARGEUR / 30.0 + 2, HAUTEUR / 15.0);
        if(versusMode){ gc.setFill(snake1.getCouleurTete());gc.fillText("Score: " + snake1.getScore(), LARGEUR / 5.0 + 2, HAUTEUR / 15.0);}

        // Spawn
        gc.setFill(snake.getCouleurTete());
        gc.fillRect(LARGEUR >> 1 ,LARGEUR >> 1, LARGEURSERPENT, LARGEURSERPENT);
        if (versusMode) {
            gc.setFill(snake1.getCouleurTete());
            gc.fillRect(LARGEUR >> 2, LARGEUR >> 1, LARGEURSERPENT, LARGEURSERPENT);
        }
    }

    /**
     * Affiche l'état du jeu.
     */
    static void drawEtat(){
        gc.setFill(Color.BLACK);
        gc.setFont(new Font("arial",20));
        if (isTimelinePause){gc.fillText("Pause",LARGEUR/2.0,20);}
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

