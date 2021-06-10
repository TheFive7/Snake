package game;

import java.io.File;
import java.util.Objects;

public class ReadRepertory {

    public static String[] listFruit;
    public static String[] listMap;

    public ReadRepertory() {
        String cheminFruit = "src/img/imgFruit";
        String cheminMap = "src/img/imgMap";

        // Lecture du nombre d'images dans le repertoire
        int compteurFruit = 0;
        int compteurMap = 0;
        try {
            // Fruit
            File folderFruit = new File(cheminFruit);
            for (final File ignored : Objects.requireNonNull(folderFruit.listFiles())) {
                compteurFruit++;
            }
            listFruit = new String[compteurFruit];

            // Assignation de chaque fichier image dans un tableau de String
            int cptFruit = 0;
            for (final File fileEntry : Objects.requireNonNull(folderFruit.listFiles())) {
                listFruit[cptFruit] = fileEntry.getName();
                cptFruit++;
            }

            // Map
            File folderMap = new File(cheminMap);
            for (final File ignored : Objects.requireNonNull(folderMap.listFiles())) {
                compteurMap++;
            }
            listMap = new String[compteurMap];

            // Assignation de chaque fichier image dans un tableau de String
            int cptMap = 0;
            for (final File fileEntry : Objects.requireNonNull(folderMap.listFiles())) {
                listMap[cptMap] = fileEntry.getName();
                cptMap++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
