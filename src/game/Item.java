package game;

import javafx.scene.image.Image;
import javafx.scene.paint.Color;

import java.awt.geom.Point2D;

import static game.Donnees.*;
import static game.Game.*;
import static game.ReadRepertoryFruit.listFruit;

public class Item {
    private Point2D position;
    private Image img;

    public Item(){
        this.position = new Point2D.Double(0,0);
    }

    public void draw(Image img){
        try {
            gc.drawImage(img, position.getX(), position.getY());
        } catch (Exception e) {
            gc.setFill(Color.RED);
            gc.fillRect(position.getX(),position.getY(),LARGEURSERPENT/2,LARGEURSERPENT/2);
        }
    }

    /**
     * Génère un consommable sur la carte.
     */
    public void genere() {
        int shouldBreak = 0;
        do {
            int a = (int) (Math.random() * LARGEUR - LARGEURSERPENT);
            int b = (int) (Math.random() * LARGEUR - LARGEURSERPENT);
            if (a <= 1){a += LARGEURSERPENT;}
            if (b <= 1){b +=  LARGEURSERPENT;}
            this.setPosition(a,b);
            for (Point2D point : snake.getTailleSnake()) {
                if (point.getX() != position.getX() && point.getY() != position.getY()) {
                    shouldBreak = 1;
                }
            }
            if (versusMode) {
                for (Point2D point : snake1.getTailleSnake()) {
                    if (point.getX() != position.getX() && point.getY() != position.getY()) {
                        shouldBreak = 1;
                    }
                }
            }
        } while (shouldBreak != 1);
    }

    public void setPosition(Point2D position) { this.position = position; }

    public Point2D getPosition() {
        return position;
    }

    public void setPosition(double a,double b) {
        this.position.setLocation(a,b);
    }

    public Image getImg() {
        return img;
    }

    public void setImg(Image img) {
        this.img = img;
    }
}

class Nourriture extends Item {

    private int random;

    public Nourriture(){
        super();
    }

    /**
     * Genere un nouveau fruit.
     */
    public void newFruit(){
        this.genere();
        this.setRandom((int) (Math.random() * listFruit.length));
        this.setImg(new Image("file:src/imgFruit/"+ listFruit[getRandom()],LARGEURSERPENT,LARGEURSERPENT,true,true));
        this.draw(getImg());
    }

    /**
     * Interagit avec le snake.
     * @param snake : Le snake qui interagit avec la nourriture.
     */
    public void affecte(Snake snake){
        if (voisinage(getPosition().getX(),getPosition().getY(),snake.getTete().getX(),snake.getTete().getY())) {
            setRandom((int) (Math.random() * 3));
            if(sonMode){if(getRandom()%2==0){StdAudio.play("src/sons/sonMange.wav");}}
            snake.addTaille(2);
            snake.setScore(snake.getScore()+100);
            newFruit();
        }
    }

    public int getRandom() {
        return random;
    }

    public void setRandom(int random) {
        this.random = random;
    }
}

class Bombe extends Item {

    private String effet;

    public Bombe(){
        super();
    }

    /**
     * Genere une nouvelle bombe et la dessine.
     */
    public void newBombe(){
        genere();
        setImg(new Image("file:src/img/bombe.png",LARGEURSERPENT,LARGEURSERPENT,true,true));
        setEffet("Malus_longueur");
    }

    /**
     * Interagit avec le snake.
     * @param snake : Le snake qui interagit avec la bombe.
     */
    public void affecte(Snake snake){
        if (voisinage(getPosition().getX(),getPosition().getY(),snake.getTete().getX(),snake.getTete().getY())) {
            if(sonMode){StdAudio.play("src/sons/sonBombe.wav");}
            setPosition(900,900);
            if(snake.getTailleSnake().size()>=20){
                snake.supprTaille(snake.getTailleSnake().size() - 15);
            }else{
                snake.setGameOver(true);
            }
        }
    }

    public String getEffet() {
        return effet;
    }

    public void setEffet(String effet) {
        this.effet = effet;
    }
}