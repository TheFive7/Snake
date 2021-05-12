package game;

import java.io.File;
import java.util.Objects;

public class ReadRepertoryFruit {

    public static String[] listFruit;

    public ReadRepertoryFruit(){

        String chemin = "src/img";

        // Lecture du nombre d'images dans le repertoire
        int compteur = 0;
        try{
            File folder=new File(chemin);
            for (final File ignored : Objects.requireNonNull(folder.listFiles())) {
                compteur++;
            }

            listFruit = new String[compteur];

            // Assignation de chaque fichier image dans un tableau de String
            int cpt=0;
            for (final File fileEntry : Objects.requireNonNull(folder.listFiles())) {
                listFruit[cpt]=fileEntry.getName();
                cpt++ ;
            }
        }catch(Exception e){ e.printStackTrace(); }
    }
}
