package game;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;

public class Donnees {

    // Fenetre
    public static final int LARGEUR = 800;
    public static final int HAUTEUR = 800;
    public static final int LIGNE = 50;
    public static final int COLONNE = 50;
    public static final int TAILLE_CARRE = LARGEUR / LIGNE;
    public static final double MOITIE_CARRE = TAILLE_CARRE / 2.0;

    // Snake
    public static final double RATIO = 2;
    public static final double LARGEURSERPENT = TAILLE_CARRE * 2;
    public static final double HAUTEURSERPENT = LARGEURSERPENT * 0.8;
    public static final double LIMITE = LARGEUR - LARGEURSERPENT;

    // Controles
    public static final int HAUT = 0;
    public static final int BAS = 1;
    public static final int GAUCHE = 2;
    public static final int DROITE = 3;
    public static final int SPACE = 4;

    // Sons
    public static Media m1 = new Media(new File("src/sons/play.wav").toURI().toString());
    public static MediaPlayer musique_principale = new MediaPlayer(m1);
    public static Media m2 = new Media(new File("src/sons/sonMange.wav").toURI().toString());
    public static MediaPlayer son_fruit = new MediaPlayer(m2);
    public static Media m3 = new Media(new File("src/sons/sonBombe.wav").toURI().toString());
    public static MediaPlayer son_bombe = new MediaPlayer(m3);
    public static Media m4 = new Media(new File("src/sons/sonMort.wav").toURI().toString());
    public static MediaPlayer son_mort = new MediaPlayer(m4);
    public static Media m5 = new Media(new File("src/sons/sonCogne.wav").toURI().toString());
    public static MediaPlayer son_cogne = new MediaPlayer(m5);
}
