package game;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;


// 60fps
public class Main extends Application {

    private Balle balle;
    private final List<Point2D> liste = new ArrayList<>();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        Group root = new Group();
        Scene scene = new Scene(root,800,800);

        // Balle
        balle = new Balle(100,150,10,3);
        balle.setFill(Color.BLACK);

        root.getChildren().addAll(balle);

        // Timeline
        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(1000/60.0), e -> run()));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();

        // Stage
        primaryStage.setResizable(false);
        primaryStage.setTitle("Projet arcs parametres");
        primaryStage.setScene(scene);
        primaryStage.show();

        // Mouse
        scene.setOnKeyPressed(event -> {
            KeyCode key = event.getCode();
            if (key == KeyCode.Q) {
                balle.setAngle(balle.getAngle() - Math.PI / 6);
            }
            if (key == KeyCode.D) {
                balle.setAngle(balle.getAngle() + Math.PI / 6);
            }
            if (key == KeyCode.SPACE) {
                balle.setCenterX(400);
                balle.setCenterY(400);
                balle.setCentreX(400);
                balle.setCentreY(400);
            }
        });

        scene.setOnMouseClicked(e -> {
            balle.setVitesse(balle.getVitesse() + 0.1);
            System.out.println(balle.getVitesse());
        });
    }

    public void run(){
        balle.moveBalle();
    }
}

class Balle extends Circle {
    private double vitesse;
    private double  angle = 2 * Math.PI;
    private double centreX = 400;
    private double centreY = 400;

    public Balle(double posX, double posY, double radius, double v){
        super(posX,posY,radius);
        vitesse = v;
    }

    public void moveBalle(){
        setCenterX(getCentreX() + vitesse * Math.cos(angle));
        setCenterY(getCentreY() + vitesse * Math.sin(angle));
        setCentreX(getCenterX());
        setCentreY(getCenterY());

        // Gauche
        if (this.getCentreX() <= 0 + this.getRadius()){
            vitesse = -vitesse;
            angle = -angle;
        }

        // Droite
        if (this.getCentreX() >= 800 - this.getRadius()){
            vitesse = -vitesse;
            angle = -angle;
        }

        // Haut
        if (this.getCentreY() <= 0 + this.getRadius()){
            vitesse = -vitesse;
        }

        // Bas
        if (this.getCentreY() >= 800 - this.getRadius()){
            vitesse = -vitesse;
        }
    }

    public double getVitesse() {
        return vitesse;
    }

    public void setVitesse(double v) {
        this.vitesse = v;
    }

    public double getAngle() {
        return angle;
    }

    public void setAngle(double angle) {
        this.angle = angle;
    }

    public double getCentreX() {
        return centreX;
    }

    public void setCentreX(double centreX) {
        this.centreX = centreX;
    }

    public double getCentreY() {
        return centreY;
    }

    public void setCentreY(double centreY) {
        this.centreY = centreY;
    }
}


