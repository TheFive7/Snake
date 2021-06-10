package game;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

import static game.Donnees.*;
import static game.Game.*;

public class Snake {
    private double largeur;
    private Color couleurTete;
    private Color couleurCorps;
    private int score;
    private boolean gameOver;
    private double direction;
    private double vitesse;
    private double angle = Math.PI / 6;
    private List<Point2D.Double> tailleSnake;

    public static List<Point2D.Double> listUsedPoints = new ArrayList<>();

    public Snake(double l, Color c1, Color c2) {
        this.setLargeur(l);
        this.setCouleurTete(c1);
        this.setCouleurCorps(c2);
        this.setScore(0);
        this.setGameOver(false);
        this.setDirection(HAUT);

        this.tailleSnake = new ArrayList<>();
    }

    /**
     * Dessine le Snake.
     *
     * @param gc : GraphicsContext
     */
    public void drawSnake(GraphicsContext gc) {
        if (!modeAngle) {
            switch ((int) this.getDirection()) {
                case SPACE, HAUT -> {
                    this.drawCorps(gc, HAUTEURSERPENT, HAUTEURSERPENT);
                    this.drawTete(gc, this.getLargeur(), LARGEURSERPENT);
                    this.drawEyes(gc, 3, 2, MOITIE_CARRE * RATIO - 1, 2, MOITIE_CARRE, MOITIE_CARRE, Color.BLACK);
                    this.drawEyes(gc, 3, 2, MOITIE_CARRE * RATIO - 1, 2, (MOITIE_CARRE / 2.0), (MOITIE_CARRE / 2.0), Color.WHITE);
                }
                case BAS -> {
                    this.drawCorps(gc, HAUTEURSERPENT, HAUTEURSERPENT);
                    this.drawTete(gc, this.getLargeur(), LARGEURSERPENT);
                    this.drawEyes(gc, 3, LARGEURSERPENT - RATIO * 5, MOITIE_CARRE * RATIO - 1, LARGEURSERPENT - 10, MOITIE_CARRE, MOITIE_CARRE, Color.BLACK);
                    this.drawEyes(gc, 3, LARGEURSERPENT - RATIO * 5, MOITIE_CARRE * RATIO - 1, LARGEURSERPENT - 10, (MOITIE_CARRE / 2.0), (MOITIE_CARRE / 2.0), Color.WHITE);
                }
                case GAUCHE -> {
                    this.drawCorps(gc, HAUTEURSERPENT, HAUTEURSERPENT);
                    this.drawTete(gc, LARGEURSERPENT, this.getLargeur());
                    this.drawEyes(gc, 2, 3, 2, MOITIE_CARRE * RATIO - 1, MOITIE_CARRE, MOITIE_CARRE, Color.BLACK);
                    this.drawEyes(gc, 2, 3, 2, MOITIE_CARRE * RATIO - 1, (MOITIE_CARRE / 2.0), (MOITIE_CARRE / 2.0), Color.WHITE);
                }
                case DROITE -> {
                    this.drawCorps(gc, HAUTEURSERPENT, HAUTEURSERPENT);
                    this.drawTete(gc, LARGEURSERPENT, this.getLargeur());
                    this.drawEyes(gc, LARGEURSERPENT - RATIO * 5, 3, LARGEURSERPENT - 10, MOITIE_CARRE * RATIO - 1, MOITIE_CARRE, MOITIE_CARRE, Color.BLACK);
                    this.drawEyes(gc, LARGEURSERPENT - RATIO * 5, 3, LARGEURSERPENT - 10, MOITIE_CARRE * RATIO - 1, (MOITIE_CARRE / 2.0), (MOITIE_CARRE / 2.0), Color.WHITE);
                }
            }
        } else {
            this.drawCorps(gc, HAUTEURSERPENT, HAUTEURSERPENT);
            this.drawTete(gc, HAUTEURSERPENT, HAUTEURSERPENT);
            this.drawEyes(gc, LARGEURSERPENT - RATIO * 5, 3, LARGEURSERPENT - 10, MOITIE_CARRE * RATIO - 1, MOITIE_CARRE, MOITIE_CARRE, Color.BLACK);
            this.drawEyes(gc, LARGEURSERPENT - RATIO * 5, 3, LARGEURSERPENT - 10, MOITIE_CARRE * RATIO - 1, (MOITIE_CARRE / 2.0), (MOITIE_CARRE / 2.0), Color.WHITE);
        }
    }

    /**
     * Dessine le corps.
     *
     * @param gc : GraphicsContext
     * @param l  : Largeur du corps
     * @param h  : Hauteur du corps
     */
    public void drawCorps(GraphicsContext gc, double l, double h) {
        gc.setFill(this.getCouleurCorps());
        for (int i = 1; i < tailleSnake.size(); i++) {
            if (!modeAngle) {
                gc.fillRoundRect(this.getCorps(i).getX(), this.getCorps(i).getY(), l, h, 15, 15);
            } else {
                gc.fillOval(this.getCorps(i).getX(), this.getCorps(i).getY(), l, l);
            }
        }
    }

    /**
     * Dessine la tête.
     *
     * @param gc : GraphicsContext
     * @param l  : Largeur de la tête
     * @param h  : Hauteur de la tête
     */
    public void drawTete(GraphicsContext gc, double l, double h) {
        if (!modeAngle) {
            gc.setFill(this.getCouleurTete());
            gc.fillRoundRect(getTete().getX(), getTete().getY(), l, h, 20, 20);
        } else {
            gc.setFill(this.getCouleurTete());
            gc.fillOval(getTete().getX(), getTete().getY(), l, h);
        }
    }

    /**
     * Dessine les yeux selon la direction choisie.
     *
     * @param gc    : GraphicsContext
     * @param x     : Abscisse 1
     * @param y     : Ordonnéee 1
     * @param x1    : Abscisse 2
     * @param y1    : Ordonnéee 2
     * @param color : Couleur des Yeux
     */

    public void drawEyes(GraphicsContext gc, double x, double y, double x1, double y1, double l, double h, Color color) {
        if (!modeAngle) {
            gc.setFill(color);
            gc.fillRoundRect(getTete().getX() + x, getTete().getY() + y, l, h, 20, 20);
            gc.setFill(color);
            gc.fillRoundRect(getTete().getX() + x1, getTete().getY() + y1, l, h, 20, 20);
        } else {
            gc.setFill(color);
            gc.fillRoundRect((getTete().x + (HAUTEURSERPENT / 2) * 0.8 + (HAUTEURSERPENT / 2 - 3) * Math.cos(angle + Math.PI / 5)), (getTete().y + (HAUTEURSERPENT / 2) * 0.8 + (HAUTEURSERPENT / 2 - 3) * Math.sin(angle + Math.PI / 5)), l, h, 20, 20);
            gc.setFill(color);
            gc.fillRoundRect((getTete().x + (HAUTEURSERPENT / 2) * 0.8 + (HAUTEURSERPENT / 2 - 3) * Math.cos(angle - Math.PI / 5)), (getTete().y + (HAUTEURSERPENT / 2) * 0.8 + (HAUTEURSERPENT / 2 - 3) * Math.sin(angle - Math.PI / 5)), l, h, 20, 20);
        }
    }

    /**
     * Gère la direction du Snake.
     */
    public void direction() {
        if (!modeAngle) {
            switch ((int) this.getDirection()) {
                case HAUT -> this.getTete().setLocation(this.getTete().x - 0, this.getTete().y - vitesse);
                case BAS -> this.getTete().setLocation(this.getTete().x + 0, this.getTete().y + vitesse);
                case GAUCHE -> this.getTete().setLocation(this.getTete().x - vitesse, this.getTete().y + 0);
                case DROITE -> this.getTete().setLocation(this.getTete().x + vitesse, this.getTete().y - 0);
            }
        } else {
            this.getTete().setLocation(this.getTete().x + vitesse * Math.cos(angle), this.getTete().y + vitesse * Math.sin(angle));
        }
    }

    /**
     * Gère les règles de direction.
     *
     * @param a : Premiere direction
     * @param b : Deuxieme direction
     */
    public void avance(int a, int b) {
        if (this.getDirection() != a && this.getDirection() != b) this.setDirection(a);
    }

    /**
     * Pour que le corps suive la tête.
     */
    public void continu() {
        for (int i = this.getSize() - 1; 1 <= i; i--) {
            this.getCorps(i).x = this.getCorps(i - 1).x;
            this.getCorps(i).y = this.getCorps(i - 1).y;
        }
    }

    /**
     * Savoir si la tête du serpent a touché les bords de la map.
     *
     * @param x1 : Abscisse 1
     * @param y1 : Ordonnée 1
     * @param x2 : Abscisse 2
     * @param y2 : Ordonnée 2
     */
    public boolean isBordTouch(double x1, double y1, double x2, double y2) {
        return (getTete().getX() <= x1 || getTete().getY() <= y1) || (getTete().getX() >= x2 || getTete().getY() >= y2);
    }

    /* Rochers */

    /**
     * Savoir si le serpent a touché un rocher.
     */
    public void isRocherTouch() {
        for (Mur mur : listMur) {
            if (voisinage(this.getTete().getX(), this.getTete().getY(), mur.getPosition().getX(), mur.getPosition().getY())) {
                son_cogne.play();
                son_cogne = new MediaPlayer(m5);
                this.setGameOver(true);
            }
        }
    }

    /**
     * Savoir si il y a un rocher.
     *
     * @param point : Coordonnées du Rocher
     * @param x     : Distance de détection sur l'abscisse
     * @param y     : Distance de détection sur l'ordonnée
     */
    public boolean isRocher(Point2D.Double point, double x, double y) {
        return (this.procheX((int) point.getX()) >= -x && this.procheX((int) point.getX()) <= y) && (this.procheY((int) point.getY()) >= -x && this.procheY((int) point.getY()) <= y);
    }

    /* Game Over */

    /**
     * Vérifie si il y a Game Over ou non. (1P)
     */
    public void isGameOver() {
        if (isBordTouch(0, 0, LIMITE, LIMITE)) {
            musique_principale.stop();
            son_mort.play();
            son_mort = new MediaPlayer(m4);
            setGameOver(true);
        }
        if (collisionMur) {
            isRocherTouch();
        }
        // Selon la vitesse, la distance de detection du corps est plus ou moins grande
        for (int i = (int) ((1/vitesse)*100); i < getSize(); i++) {
            if (voisinage(getTete().getX(), getTete().getY(), getCorps(i).getX(), getCorps(i).getY())) {
                musique_principale.stop();
                son_mort.play();
                son_mort = new MediaPlayer(m4);
                setGameOver(true);
                break;
            }
        }
    }

    /**
     * Vérifie si il y a Game Over ou non. (2P)
     */
    public void isGameOver(Snake snake) {
        isGameOver();
        for (int i = (int) ((1/vitesse)*100); i < getSize(); i++) {
            if (voisinage(getTete().getX(), getTete().getY(), getCorps(i).getX(), getCorps(i).getY())) {
                setGameOver(true);
                break;
            }
        }

        for (int i = 0; i < getSize(); i++){
            // IA sur joueur
            if (voisinage(snake.getTete().getX(), snake.getTete().getY(), getCorps(i).getX(), getCorps(i).getY())) {
                snake.setGameOver(true);
                break;
            }
        }

        for (int i = 0; i < snake.getSize();i++){
            // Joueur sur IA
            if (voisinage(getTete().getX(), getTete().getY(), snake.getCorps(i).getX(), snake.getCorps(i).getY())) {
                setGameOver(true);
                break;
            }
        }
    }

    /* Proche */

    /**
     * Donne la distance entre le X Snake et X arrivée.
     *
     * @param x : Abscisse
     * @return : X
     */
    public int procheX(int x) {
        double a = this.getTete().getX();
        return (int) (x - a);
    }

    /**
     * Donne la distance entre le Y Snake et Y arrivée.
     *
     * @param y : Ordonnée
     * @return : Y
     */
    public int procheY(int y) {
        double b = this.getTete().getY();
        return (int) (y - b);
    }

    /* Intelligence Artificielle */

    /**
     * Les points utilisés dans la map.
     */
    public static void pointsUsed() {
        listUsedPoints.clear();
        for (Mur mur : listMur){
            listUsedPoints.add(mur.getPosition());
        }
        listUsedPoints.addAll(snake.getTailleSnake());
        for (int i=15; i < snake1.getSize()-1; i++) {
            listUsedPoints.add(snake1.getCorps(i));
        }
        listUsedPoints.add((Point2D.Double) bombe.getPosition());
    }

    /**
     * Detecte les obstacles.
     * @return
     */
    public boolean detect(){
        boolean result = false;
        switch ((int) this.getDirection()) {
            case DROITE:
                for (Point2D point : listUsedPoints) {
                    if (voisinage(point.getX(), point.getY(), this.getTete().getX()+ vitesse, this.getTete().getY())) result = true;
                    //System.out.println(point);
                }

                if (this.isBordTouch(0, 0, LIMITE - vitesse, LIMITE)) result = true;
                break;

            case GAUCHE:
                for (Point2D point : listUsedPoints) {
                    if (voisinage(point.getX(), point.getY(), this.getTete().getX()- vitesse, this.getTete().getY())) result = true;
                    //System.out.println(point);
                }

                if (this.isBordTouch(vitesse, 0, LIMITE, LIMITE)) result = true;
                break;

            case HAUT:
                for (Point2D point : listUsedPoints) {
                    if (voisinage(point.getX(), point.getY(), this.getTete().getX(), this.getTete().getY()- vitesse)) result = true;
                    //System.out.println(point);
                }

                if (this.isBordTouch(0, vitesse, LIMITE, LIMITE)) result = true;
                break;

            case BAS:
                for (Point2D point : listUsedPoints) {
                    if (voisinage(point.getX(), point.getY(), this.getTete().getX(), this.getTete().getY()+ vitesse)) result = true;
                    //System.out.println(point);
                }

                if (this.isBordTouch(0, 0, LIMITE, LIMITE - vitesse)) result = true;
                break;
        }
        return result;
    }

    /**
     * Mange et évite les rochers et les bords.
     * Ne prends pas en compte son corps.
     */
    public void iaBasique() {
        if (detect()){setDirection(DROITE);}
        allerA((int) food.getPosition().getX(), (int) food.getPosition().getY());
    }

    /**
     * IA qui se dirige vers la nourriture.
     */
    public void allerA(int x, int y) {
        double a,b;
        a = this.procheX(x);
        b = this.procheY(y);
        if (b != 0) {
            if (b > 0) {this.setDirection(BAS);}
            if (b < 0) {this.setDirection(HAUT);}
            if (-vitesse < b && b < vitesse){
                if (a > 0) {this.setDirection(DROITE);}
                if (a < 0) {this.setDirection(GAUCHE);}
            }
        } else if (a != 0){
            if (a > 0) {this.setDirection(DROITE);}
            if (a < 0) {this.setDirection(GAUCHE);}
        }
    }

    /**
     * Le Snake tourne de 90°.
     */
    public void rotationDroite() {
        switch ((int) this.getDirection()) {
            case HAUT -> this.setDirection(DROITE);
            case BAS -> this.setDirection(GAUCHE);
            case GAUCHE -> this.setDirection(HAUT);
            case DROITE -> this.setDirection(BAS);
        }
    }

    /**
     * Le Snake tourne de -90°.
     */
    public void rotationGauche() {
        switch ((int) this.getDirection()) {
            case HAUT -> this.setDirection(GAUCHE);
            case BAS -> this.setDirection(DROITE);
            case GAUCHE -> this.setDirection(BAS);
            case DROITE -> this.setDirection(HAUT);
        }
    }

    /**
     * Reset un snake mort et le remet à son spawn.
     */
    public void resetSnake() {
        vitesse = vitesse_initiale;
        inverseControls = false;
        obstacle = false;
        setGameOver(false);
        tailleSnake = new ArrayList<>();
        setDirection(HAUT);
        setScore(0);
        setAngle(3 * Math.PI / 2);
    }

    /* set et get de la classe */

    public void addTaillePosition(double x, double y) {
        tailleSnake.add(new Point2D.Double(x, y));
    }

    public void addTaille() {
        tailleSnake.add(new Point2D.Double(900, 900));
    }

    public void addTaille(int n) {
        for (int i = 0; i < n; i++) {
            tailleSnake.add(new Point2D.Double(900, 900));
        }
    }

    public void supprTaille(int n) {
        for (int i = 0; i < n; i++) {
            tailleSnake.remove(tailleSnake.size() - 1);
        }
    }

    public Point2D.Double getTete() {
        return tailleSnake.get(0);
    }

    public Point2D.Double getCorps(int i) {
        return tailleSnake.get(i);
    }

    public int getSize() {
        return tailleSnake.size();
    }

    public void setCouleur(Color c) {
        this.couleurTete = c;
        this.couleurCorps = c;
    }

    public double getLargeur() {
        return this.largeur;
    }

    public void setLargeur(double l) {
        this.largeur = l;
    }

    public Color getCouleurTete() {
        return this.couleurTete;
    }

    public void setCouleurTete(Color c) {
        this.couleurTete = c;
    }

    public Color getCouleurCorps() {
        return this.couleurCorps;
    }

    public void setCouleurCorps(Color c) {
        this.couleurCorps = c;
    }

    public int getScore() {
        return this.score;
    }

    public void setScore(int s) {
        this.score = s;
    }

    public boolean getGameOver() {
        return this.gameOver;
    }

    public void setGameOver(boolean g) {
        this.gameOver = g;
    }

    public double getDirection() {
        return this.direction;
    }

    public void setDirection(int d) {
        this.direction = d;
    }

    public List<Point2D.Double> getTailleSnake() {
        return tailleSnake;
    }

    public double getAngle() {
        return angle;
    }

    public void setAngle(double angle) {
        this.angle = angle;
    }

    public void setDirection(double direction) {
        this.direction = direction;
    }

    public double getVitesse() {
        return vitesse;
    }

    public void setVitesse(double vitesse) {
        this.vitesse = vitesse;
    }

    public void setTailleSnake(List<Point2D.Double> tailleSnake) {
        this.tailleSnake = tailleSnake;
    }
}