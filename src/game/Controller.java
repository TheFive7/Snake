package game;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.input.KeyCode;
import javafx.util.Duration;

import static game.App.scene;
import static game.Donnees.*;
import static game.Draw.drawEtat;
import static game.Game.*;


public class Controller {
    public static boolean isTimelineActive = false;
    public static boolean isTimelinePause = true;
    public static Timeline timeline = new Timeline(new KeyFrame(Duration.millis(nbFramePerSecond), e -> run()));

    public Controller() {
        // Gestion des touches en jeu
        scene.setOnKeyPressed(event -> {

            KeyCode key = event.getCode();
            if (key == KeyCode.UP || key == KeyCode.Z) {
                if (!inverseControls) {
                    snake.avance(HAUT, BAS);
                } else {
                    snake.avance(BAS, HAUT);
                }
            }
            if (key == KeyCode.DOWN || key == KeyCode.S) {
                if (!inverseControls) {
                    snake.avance(BAS, HAUT);
                } else {
                    snake.avance(HAUT, BAS);
                }
            }
            if (key == KeyCode.LEFT || key == KeyCode.Q) {
                if (!inverseControls) {
                    snake.avance(GAUCHE, DROITE);
                } else {
                    snake.avance(DROITE, GAUCHE);
                }
                snake.setAngle(snake.getAngle() - Math.PI / 10);
            }
            if (key == KeyCode.RIGHT || key == KeyCode.D) {
                if (!inverseControls) {
                    snake.avance(DROITE, GAUCHE);
                } else {
                    snake.avance(GAUCHE, DROITE);
                }
                snake.setAngle(snake.getAngle() + Math.PI / 10);
            }
            if (key == KeyCode.SPACE) {
                if (gameOver) {
                    if (snake.getDirection() != SPACE) snake.setDirection(SPACE);
                } else {
                    if (!isTimelineActive) {
                        // Affiche le Snake correctement (FPS)
                        timeline.setCycleCount(Animation.INDEFINITE);
                        isTimelineActive = true;
                        timeline.play();
                    } else {
                        if (!isTimelinePause) {
                            isTimelinePause = true;
                            drawEtat();
                            timeline.pause();
                        } else {
                            isTimelinePause = false;
                            drawEtat();
                            timeline.play();
                        }
                    }
                }
            }

            if (key == KeyCode.ESCAPE) {
                System.exit(0);
            }
        });

        // Debug:
        scene.setOnMouseClicked(e -> {
            snake.addTaille((int) ((1/snake.getVitesse())*100));
        });
    }
}
