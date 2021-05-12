package game;

import javafx.scene.paint.Color;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import static game.Game.*;

public class Menu0 extends JFrame {
    private JPanel panel1;
    private JButton play;
    private JCheckBox checkBox;
    private JComboBox<String> comboBoxCouleur;
    private JLabel Couleur;
    private JSlider slider;
    private JPanel vueJeu;
    private JLabel labelVue;
    private JLabel labelRochers;
    private JComboBox comboBoxVitesse;
    private JPanel panelBase;
    private JCheckBox chkReseau;
    private String cheminGif = "snakeRouge";

    public Menu0() {
        // Icone
        ImageIcon icon = new ImageIcon("src/iconSnake.png");
        setIconImage(icon.getImage());

        // Ecran centré
        setLocation(500,100);
        setSize(920,845);

        // Image de fond
        Image fond = Toolkit.getDefaultToolkit().getImage("src/fond.png");
        try {
            MediaTracker mt = new MediaTracker(this);
            mt.addImage(fond,0);
            mt.waitForAll();
        } catch(Exception e) { e.printStackTrace(); }
        setContentPane(new ContentPane(fond));

        setVisible(true);
        setTitle("SNAKE");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        add(panel1);

        // Play
        // invisible(play);
        play.addActionListener(e -> {
            setVisible(false);
            App.begin();
            System.exit(0);
        });

        // IA
        checkBox.addChangeListener(e -> versusMode = !versusMode);

        // Rochers
        slider.setValue(0);
        slider.setMinimum(0);slider.setMaximum(5);
        slider.addChangeListener(e -> {
            nbRocher = slider.getValue();
            labelRochers.setText("Nombre de Rochers : " + nbRocher);
        });


        // Couleurs
        ItemListener comboItemListenerCouleur = e1 -> {
            if(e1.getStateChange() == ItemEvent.SELECTED) {
                String color = (String) e1.getItem();
                switch (color) {
                    case "ROUGE" -> {
                        couleurSnake = Color.RED;
                        cheminGif = "snakeRouge";
                        gif(labelVue,cheminGif);
                    }
                    case "BLEU" -> {
                        couleurSnake = Color.BLUE;
                        cheminGif = "snakeBleu";
                        gif(labelVue,cheminGif);
                    }
                    case "VERT" -> {
                        couleurSnake = Color.GREEN;
                        cheminGif = "snakeVert";
                        gif(labelVue,cheminGif);
                    }
                    case "ROSE" -> {
                        couleurSnake = Color.PINK;
                        cheminGif = "snakeRose";
                        gif(labelVue,cheminGif);
                    }
                    case "ORANGE" -> {
                        couleurSnake = Color.ORANGE;
                        cheminGif = "snakeOrange";
                        gif(labelVue,cheminGif);
                    }
                    case "BLANC" -> {
                        couleurSnake = Color.WHITE;
                        cheminGif = "snakeBlanc";
                        gif(labelVue,cheminGif);
                    }
                }
            }
        };
        comboBoxCouleur.addItemListener(comboItemListenerCouleur);

        // Panel animé
        gif(labelVue,cheminGif);

        // Vitesse
        ItemListener comboItemListenerVitesse = e1 -> {
            if(e1.getStateChange() == ItemEvent.SELECTED) {
                String color = (String) e1.getItem();
                switch (color) {
                    case "TRES LENT" -> VITESSE = 0.25;
                    case "LENT" -> VITESSE = 0.5;
                    case "NORMAL" -> VITESSE = 1;
                }
            }
        };
        comboBoxVitesse.setSelectedIndex(2);
        comboBoxVitesse.addItemListener(comboItemListenerVitesse);

        // Reseau
        chkReseau.addChangeListener(e -> reseauMode = !reseauMode);
    }


    /**
     *
     * @param labelVue : Le label auquel on attribue le gif.
     * @param chemin : Le chemin du dossier où se trouve le gif.
     */
    public void gif(JLabel labelVue, String chemin){
        ImageIcon gif = new ImageIcon(this.getClass().getResource("/gifMenu/"+ chemin + ".gif"));
        labelVue.setIcon(gif);
        Border border = BorderFactory.createLineBorder(java.awt.Color.BLACK,5);
        labelVue.setBorder(border);
    }

    /**
     * Pour rendre invisible un JButton.
     * @param b : Le bouton à rendre invisible.
     */
    public void invisible(JButton b){
        b.setOpaque(false);
        b.setContentAreaFilled(false);
        b.setBorderPainted(false);
        b.setText("");
    }
}

/**
 * Pour mettre un fond sur la JFrame.
 */
class ContentPane extends JPanel{
    private final Image image;
    public ContentPane(Image fond){super();image=fond;}
    public void paintComponent(Graphics g){g.drawImage(image,0,0,null);}
}
