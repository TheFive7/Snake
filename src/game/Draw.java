package game;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.awt.geom.Point2D;
import java.util.Objects;

import static game.App.backgroundImage;
import static game.Controller.isTimelinePause;
import static game.Donnees.*;
import static game.Game.*;

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
        gc.setFont(Font.loadFont(
                Objects.requireNonNull(App.class.getResource("minecraft_font.ttf")).toExternalForm(),
                20
        ));
        gc.fillText("Score: " + snake.getScore(), LARGEUR / 30.0, HAUTEUR / 15.0);
        if (versusMode || curseur < 300) {
            gc.setFill(snake1.getCouleurTete());
            gc.fillText("Score: " + snake1.getScore(), LARGEUR / 4.0, HAUTEUR / 15.0);
        }

        // Spawn
        gc.setFill(snake.getCouleurTete());
        gc.fillRect(LARGEUR >> 1, LARGEUR >> 1, LARGEURSERPENT, LARGEURSERPENT);
        if (versusMode || curseur < 300) {
            gc.setFill(snake1.getCouleurTete());
            gc.fillRect(LARGEUR >> 2, LARGEUR >> 1, LARGEURSERPENT, LARGEURSERPENT);
            if (snake1.getGameOver()){
                if (curseur % 60 == 0) {
                    gc.setFont(Font.loadFont(
                            Objects.requireNonNull(App.class.getResource("minecraft_font.ttf")).toExternalForm(),
                            20
                    ));
                }
                gc.setFill(Color.BLACK);
                gc.fillText(""+curseur / 60, (LARGEUR >> 2) + 10, (LARGEUR >> 1) + 25);
            }
        }
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

