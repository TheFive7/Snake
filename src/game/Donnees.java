package game;

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
}
