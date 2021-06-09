package game.reseau;

import game.App;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.awt.geom.Point2D;
import java.util.Objects;

import static game.App.backgroundImage;
import static game.reseau.Controller.isTimelinePause;
import static game.reseau.Donnees.*;
import static game.reseau.Game.*;

public class Draw {

    /**
     * Dessine la map, le score, l'état, le spawn et les rochers.
     *
     * @param gc : GraphicsContext
     */
    static void drawAll(GraphicsContext gc) {
        // BackGround
        gc.drawImage(backgroundImage, 0, 0);
/*        gc.setFill(Color.AQUA);
        gc.fillRect(0, 0, LARGEUR, 865);*/

        // Score
        gc.setFill(snake.getCouleurTete());
        gc.setFont(new Font("arial", 20));
        gc.fillText("Score: " + snake.getScore(), LARGEUR / 30.0, HAUTEUR / 15.0);

        // Spawn
        gc.setFill(snake.getCouleurTete());
        gc.fillRect(LARGEUR >> 1, LARGEUR >> 1, LARGEURSERPENT, LARGEURSERPENT);

    }

    /**
     * Affiche l'état du jeu.
     */
    static void drawEtat() {
        gc.setFill(Color.BLACK);
        gc.setFont(new Font("arial", 20));
        if (isTimelinePause) {
            gc.fillText("Pause", LARGEUR / 2.0, 20);
        }
    }

    /**
     * Dessine un rocher.
     *
     * @param point : Coordonnées du rocher
     */
    static void drawRocher(GraphicsContext gc, Point2D.Double point) {
        gc.setFill(Color.web("424242"));
        gc.fillRoundRect(point.x * TAILLE_CARRE, point.y * TAILLE_CARRE, LARGEURSERPENT, LARGEURSERPENT, 15, 35);
    }
}

