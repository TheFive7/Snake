package game;

import javafx.scene.image.Image;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;

import java.awt.geom.Point2D;

import static game.Donnees.*;
import static game.Draw.drawMur;
import static game.Game.*;
import static game.ReadRepertory.listFruit;

public class Item {
    private Point2D.Double position;
    private Image img;

    public Item() {
        this.position = new Point2D.Double(0, 0);
    }

    /**
     * Dessine l'item.
     * @param img : Image de l'item.
     */
    public void draw(Image img) {
        try {
            gc.drawImage(img, position.getX(), position.getY());
        } catch (Exception e) {
            gc.setFill(Color.RED);
            gc.fillRect(position.getX(), position.getY(), LARGEURSERPENT / 2, LARGEURSERPENT / 2);
        }
    }

    /**
     * Génère un item sur la carte.
     */
    public void genere() {
        int shouldBreak = 0;
        do {
            int a = (int) (Math.random() * LARGEUR - LARGEURSERPENT);
            int b = (int) (Math.random() * LARGEUR - LARGEURSERPENT);
            if (a <= 1) {
                a += LARGEURSERPENT;
            }
            if (b <= 1) {
                b += LARGEURSERPENT;
            }
            this.setPosition(a, b);
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

    /**
     * Getter Position de l'item.
     * @return : Position
     */
    public Point2D.Double getPosition() {
        return position;
    }

    /**
     * Setter Position de l'item.
     * @param x : Abscisse
     * @param y : Ordonnee
     */
    public void setPosition(double x, double y) {
        this.position.setLocation(x, y);
    }

    /**
     * Getter Image Item.
     * @return : Image
     */
    public Image getImg() {
        return img;
    }

    /**
     * Setter Image Item.
     * @param img : Image
     */
    public void setImg(Image img) {
        this.img = img;
    }
}

class Nourriture extends Item {

    private int random;

    public Nourriture() {
        super();
    }

    /**
     * Genere un nouveau fruit.
     */
    public void newFruit() {
        this.genere();
        this.setRandom((int) (Math.random() * listFruit.length));
        this.setImg(new Image("file:src/img/imgFruit/" + listFruit[getRandom()], LARGEURSERPENT, LARGEURSERPENT, true, true));
        this.draw(getImg());
    }

    /**
     * Interagit avec le snake.
     *
     * @param snake : Le snake qui interagit avec la nourriture.
     */
    public void affecte(Snake snake) {
        if (voisinage(getPosition().getX(), getPosition().getY(), snake.getTete().getX(), snake.getTete().getY())) {
            setRandom((int) (Math.random() * 3));
            if (sonMode) {
                son_fruit.play();
                son_fruit = new MediaPlayer(m2);
            }
            snake.addTaille((int) ((1/snake.getVitesse())*25)); // ajoute 5 de taille
            snake.setScore(snake.getScore() + 100);
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

    public Bombe(String effet) {
        super();
        setEffet(effet);
    }

    /**
     * Genere une nouvelle bombe et la dessine.
     */
    public void newBombe() {
        this.genere();
        setImg(new Image("file:src/img/imgObstacle/bombe.png", LARGEURSERPENT, LARGEURSERPENT, true, true));
    }

    /**
     * Interagit avec le snake.
     *
     * @param snake : Le snake qui interagit avec la bombe.
     */
    public boolean affecte(Snake snake, int t) {
        if (voisinage(getPosition().getX(), getPosition().getY(), snake.getTete().getX(), snake.getTete().getY())) {
            if (sonMode) {son_bombe.play();son_bombe = new MediaPlayer(m3);}
            setPosition(900, 900);
            switch (getEffet()) {
                case "longueur" -> {
                    if (snake.getTailleSnake().size() >= 20) {
                        snake.supprTaille(snake.getTailleSnake().size() - 15);
                        snake.setScore(snake.getScore() - 300);
                    } else {
                        snake.setGameOver(true);
                    }
                }
                case "vitesse" -> snake.setVitesse(vitesse_initiale * 2);
                case "controle" -> inverseControls = true;
                case "mort" -> snake.setGameOver(true);
            }
            return true;
        } else {
            if (t > 500) {
                snake.setVitesse(vitesse_initiale);
                inverseControls = false;
            }
            return false;
        }
    }

    /**
     * Mets l'effet à la bombe
     */
    void effect(){
        if (time % 600 == 0) {
            double random = Math.random();
            if (random <= 0.25) {
                setEffet("longueur");
            } else if (0.26 < random && random <= 0.50) {
                setEffet("vitesse");
            } else if ((0.51 < random && random <= 0.75) && !modeAngle) {
                setEffet("controle");
            } else if (0.76 < random && random <= 1) {
                setEffet("mort");
            }
            genere();
        }
    }

    public String getEffet() {
        return effet;
    }

    public void setEffet(String effet) {
        this.effet = effet;
        switch (getEffet()) {
            case "longueur" -> setImg(new Image("file:src/img/imgObstacle/bombe_reduction.png", LARGEURSERPENT, LARGEURSERPENT, true, true));
            case "vitesse" -> setImg(new Image("file:src/img/imgObstacle/bombe_vitesse.png", LARGEURSERPENT, LARGEURSERPENT, true, true));
            case "controle" -> setImg(new Image("file:src/img/imgObstacle/bombe_controle.png", LARGEURSERPENT, LARGEURSERPENT, true, true));
            case "mort" -> setImg(new Image("file:src/img/imgObstacle/bombe_mort.png", LARGEURSERPENT, LARGEURSERPENT, true, true));
        }
    }
}
class Mur extends Item {

    public Mur(){
        super();
        setImg(new Image("file:src/img/imgObstacle/mur.png", LARGEURSERPENT, LARGEURSERPENT, true, true));
    }

    public Mur(Point2D p){
        this.setPosition(p.getX(),p.getY());
        setImg(new Image("file:src/img/imgObstacle/mur.png", LARGEURSERPENT, LARGEURSERPENT, true, true));
    }

    /**
     * Génère des murs aléatoirement sur la map.
     */
    void genereMur() {
        if (nbParties == 0) {
            for (int i = 0; i <= nbMurs - 1; i++) {
                genere();
                listMur.add(new Mur(getPosition()));
            }
            nbParties++;
        }
    }

    void drawAll(){
        for (Mur mur : listMur) {
            mur.draw(getImg());
        }
    }
}