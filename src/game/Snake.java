package game;

import javafx.scene.canvas.GraphicsContext;
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
    private double angle = Math.PI / 10;
    private List<Point2D.Double> tailleSnake;

    public Snake (double l,Color c1,Color c2){
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
     * @param gc : GraphicsContext
     * @param l : Largeur du corps
     * @param h : Hauteur du corps
     */
    public void drawCorps(GraphicsContext gc, double l, double h){
        gc.setFill(this.getCouleurCorps());
        for (int i=1; i < tailleSnake.size(); i++) {
            gc.fillRoundRect(this.getCorps(i).getX(), this.getCorps(i).getY(), l, h,15,15);
        }
    }

    /**
     * Dessine la tête.
     * @param gc : GraphicsContext
     * @param l : Largeur de la tête
     * @param h : Hauteur de la tête
     */
    public void drawTete(GraphicsContext gc,double l, double h){
        if (!modeAngle) {
            gc.setFill(this.getCouleurTete());
            gc.fillRoundRect(getTete().getX(), getTete().getY(), l, h, 20, 20);
        } else {
            gc.setFill(this.getCouleurTete());
            gc.fillRoundRect(getTete().getX(), getTete().getY(), l, h,15,15);
        }
    }

    /**
     * Dessine les yeux selon la direction choisie.
     * @param gc : GraphicsContext
     * @param x : Abscisse 1
     * @param y : Ordonnéee 1
     * @param x1 : Abscisse 2
     * @param y1 : Ordonnéee 2
     * @param color : Couleur des Yeux
     */
    public void drawEyes(GraphicsContext gc,double x,double y,double x1,double y1,double l,double h,Color color){
        if (!modeAngle) {
            gc.setFill(color);
            gc.fillRoundRect(getTete().getX() + x, getTete().getY() + y, l, h, 20, 20);
            gc.setFill(color);
            gc.fillRoundRect(getTete().getX() + x1, getTete().getY() + y1, l, h, 20, 20);
        } else {
            gc.setFill(color);
            gc.fillRoundRect((getTete().x + x), (getTete().y + y), l, h, 20, 20);
            gc.setFill(color);
            gc.fillRoundRect((getTete().x + x1), (getTete().y + y1), l, h, 20, 20);
        }
    }

    /**
     * Gère la direction du Snake.
     */
    public void direction(){
        if (!modeAngle) {
            switch ((int) this.getDirection()) {
                case HAUT -> this.getTete().setLocation(this.getTete().x - 0, this.getTete().y - VITESSE);
                case BAS -> this.getTete().setLocation(this.getTete().x + 0, this.getTete().y + VITESSE);
                case GAUCHE -> this.getTete().setLocation(this.getTete().x - VITESSE, this.getTete().y + 0);
                case DROITE -> this.getTete().setLocation(this.getTete().x + VITESSE, this.getTete().y - 0);
            }
        } else {
            this.getTete().setLocation(this.getTete().x + VITESSE * Math.cos(angle), this.getTete().y + VITESSE * Math.sin(angle));
        }
    }

    /**
     * Gère les règles de direction.
     * @param a : Premiere direction
     * @param b : Deuxieme direction
     */
    public void avance(int a,int b){
        if (this.getDirection() != a && this.getDirection() != b) this.setDirection(a);
    }

    /**
     * Pour que le corps suive la tête.
     */
    public void continu(){
        for (int i = this.getSize()-1; 1 <= i; i--){
            this.getCorps(i).x = this.getCorps(i-1).x;
            this.getCorps(i).y = this.getCorps(i-1).y;
        }
    }

    /**
     * Savoir si la tête du serpent a touché les bords de la map.
     * @param x1 : Abscisse 1
     * @param y1 : Ordonnée 1
     * @param x2 : Abscisse 2
     * @param y2 : Ordonnée 2
     */
    public boolean isBordTouch(double x1,double y1,double x2,double y2){
        return (getTete().getX() <= x1 || getTete().getY() <= y1 ) || (getTete().getX() >= x2 || getTete().getY() >= y2);
    }

    /* Rochers */
    /**
     * Savoir si le serpent a touché un rocher.
     */
    public void isRocherTouch(){
        for (Point2D.Double point : listRocher) {
            if (voisinage(this.getTete().getX(),this.getTete().getY(),point.x,point.y)){
                this.setGameOver(true);
            }
        }
    }

    /**
     * Savoir si il y a un rocher.
     * @param point : Coordonnées du Rocher
     * @param x : Distance de détection sur l'abscisse
     * @param y : Distance de détection sur l'ordonnée
     */
    public boolean isRocher(Point2D.Double point,double x,double y){
        boolean bool = false;
        if ((this.procheX((int)point.getX()) >= -x && this.procheX((int)point.getX()) <= y) && (this.procheY((int)point.getY()) >= -x && this.procheY((int)point.getY()) <= y)){
            bool = true;
        }
        return bool;
    }

    /* Game Over */
    /**
     * Vérifie si il y a Game Over ou non. (1P)
     */
    public void isGameOver() {
        if (isBordTouch(0,0,LIMITE,LIMITE)){setGameOver(true);}
        if (collisionRocher){isRocherTouch();}
        for (int i=15; i < getSize(); i++) {
            if (voisinage(getTete().getX(),getTete().getY(),getCorps(i).getX(),getCorps(i).getY())) {
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
        for (int i=15; i < snake.getSize(); i++) {
            if (voisinage(snake.getTete().getX(),snake.getTete().getY(),snake.getCorps(i).getX(),snake.getCorps(i).getY())) {
                snake.setGameOver(true);
                break;
            }
        }
    }

    /* Proche */
    /**
     * Donne la distance entre le X Snake et X arrivée.
     * @param x : Abscisse
     * @return : X
     */
    public int procheX(int x){
        double a = this.getTete().getX();
        return (int) (x-a);
    }

    /**
     * Donne la distance entre le Y Snake et Y arrivée.
     * @param y : Ordonnée
     * @return : Y
     */
    public int procheY(int y){
        double b = this.getTete().getY();
        return (int) (y-b);
    }

    /* Intelligence Artificielle */

    /**
     * Mange et évite les rochers et les bords.
     * Ne prends pas en compte son corps.
     */
    public void iaBasique() {
        if (nbRocher > 0){
            for (Point2D.Double point : listRocher) {
                if (obstacle) {
                    if (this.isRocher(point, 4, 4) || this.isRocher(point, -4, -4) || !this.isBordTouch(1, 1, LIMITE - TAILLE_CARRE, LIMITE - TAILLE_CARRE)) {
                        obstacle = false;
                    }
                }
                if ((this.isRocher(point, 3, 3) || this.isRocher(point, -3, -3)) || this.isBordTouch(1, 1, LIMITE - TAILLE_CARRE, LIMITE - TAILLE_CARRE)) {
                    obstacle = true;
                    this.rotationDroite();
                }
                if (!obstacle) {
                    allerA((int)food.getPosition().getX(),(int)food.getPosition().getY());
                }
            }
        } else {
            allerA((int)food.getPosition().getX(),(int)food.getPosition().getY());
        }
    }

    /**
     * IA qui se dirige vers la nourriture.
     */
    public void allerA(int x,int y){
        if (voisinage(y,0,getTete().getY(),0)) {
            // Droite + Gauche
            if (procheX(x) >= 0) {setDirection(DROITE);}
            if (procheX(x) < 0) {setDirection(GAUCHE);}
        }

        if (voisinage(x,0,getTete().getX(),0)) {
            // Bas + Haut
            if (procheY(y) >= 0) {setDirection(BAS);}
            if (procheY(y) < 0) {setDirection(HAUT);}
        }
    }

    /**
     * Le Snake tourne de 90°.
     */
    public void rotationDroite(){
        switch ((int) this.getDirection()) {
            case HAUT -> this.setDirection(DROITE);
            case BAS -> this.setDirection(GAUCHE);
            case GAUCHE -> this.setDirection(HAUT);
            case DROITE -> this.setDirection(BAS);
        }
    }

    /**
     * Reset un snake mort et le remet à son spawn.
     */
    public void resetSnake(){
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

    public void addTaille(){
        tailleSnake.add(new Point2D.Double(900, 900));
    }

    public void addTaille(int n){
        for (int i = 0;i<n;i++) {
            tailleSnake.add(new Point2D.Double(900, 900));
        }
    }

    public void supprTaille(int n){
        for (int i = 0;i<n;i++) {
            tailleSnake.remove(tailleSnake.size()-1);
        }
    }

    public void setLargeur(double l){
        this.largeur = l;
    }

    public Point2D.Double getTete(){
        return tailleSnake.get(0);
    }

    public Point2D.Double getCorps(int i){
        return tailleSnake.get(i);
    }

    public int getSize(){ return tailleSnake.size(); }

    public void setCouleurTete(Color c){
        this.couleurTete = c;
    }

    public void setCouleurCorps(Color c){
        this.couleurCorps = c;
    }

    public void setCouleur(Color c) { this.couleurTete = c; this.couleurCorps = c;}

    public void setScore(int s){
        this.score = s;
    }

    public void setGameOver(boolean g){
        this.gameOver = g;
    }

    public void setDirection(int d){
        this.direction = d;
    }

    public double getLargeur(){
        return this.largeur;
    }

    public Color getCouleurTete(){
        return this.couleurTete;
    }

    public Color getCouleurCorps(){
        return this.couleurCorps;
    }

    public int getScore(){
        return this.score;
    }

    public boolean getGameOver(){
        return this.gameOver;
    }

    public double getDirection(){
        return this.direction;
    }

    public List<Point2D.Double> getTailleSnake(){
        return tailleSnake;
    }

    public double getAngle() {
        return angle;
    }

    public void setAngle(double angle) {
        this.angle = angle;
    }

}
