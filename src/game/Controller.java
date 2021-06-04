package game;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.input.KeyCode;
import javafx.util.Duration;

import static game.App.*;
import static game.Donnees.*;
import static game.Draw.drawEtat;
import static game.Game.*;


public class Controller {
    public static Timeline timeline = new Timeline(new KeyFrame(Duration.millis(nbFramePerSecond), e -> run()));
    public static boolean isTimelineActive = false;
    public static boolean isTimelinePause = true;

    public Controller(){
        // Gestion des touches en jeu
        scene.setOnKeyPressed(event -> {
            KeyCode key = event.getCode();
            if (key == KeyCode.UP || key == KeyCode.Z){
                snake.avance(HAUT,BAS);
            }
            if (key == KeyCode.DOWN || key == KeyCode.S){
                snake.avance(BAS,HAUT);
            }
            if (key == KeyCode.LEFT || key == KeyCode.Q){
                snake.avance(GAUCHE,DROITE);
                snake.setAngle(snake.getAngle() - Math.PI/10);
            }
            if (key == KeyCode.RIGHT || key == KeyCode.D){
                snake.avance(DROITE,GAUCHE);
                snake.setAngle(snake.getAngle() + Math.PI/10);
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
            snake.addTaille(20);
        });

/*        scene.setOnMouseDragged(e -> {
            double x = e.getSceneX()/TAILLE_CARRE;
            double y = e.getSceneY()/TAILLE_CARRE;

            snake.getTete().x = x;
            snake.getTete().y = y;
        });*/
    }
}
