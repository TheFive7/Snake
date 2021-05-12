package game;

import javafx.scene.paint.Color;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import static game.Game.*;
import static game.Game.VITESSE;

public class Menu extends JFrame {
    private JButton play;
    private JCheckBox checkBox;
    private JComboBox<String> comboBoxCouleur;
    private JLabel couleur;
    private JSlider slider;
    private JLabel labelVue;
    private JLabel labelRochers;
    private JComboBox comboBoxVitesse;
    private JPanel panelBase;
    private JCheckBox chkReseau;
    private JLabel vitesse;
    private String cheminGif = "snakeRouge";

    public Menu(){
        ImageIcon icon = new ImageIcon("src/iconSnake.png");
        setIconImage(icon.getImage());

        setTitle("SNAKE");
        setLocation(500,100);
        setSize(930,845);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Image de fond
        Image fond = Toolkit.getDefaultToolkit().getImage("src/fond.png");
        try {
            MediaTracker mt = new MediaTracker(this);
            mt.addImage(fond,0);
            mt.waitForAll();
        } catch(Exception e) { e.printStackTrace(); }
        setContentPane(new ContentPane(fond));
        init();
        add(panelBase);
        setVisible(true);
    }

    public void init(){
        panelBase = new JPanel();

        play = new JButton("Play");
        checkBox = new JCheckBox("IA");
        chkReseau = new JCheckBox("Reseau LAN");

        couleur = new JLabel();
        String[] stringCouleur = new String[]{"ROUGE","BLEU","VERT","ROSE","ORANGE","BLANC"};
        comboBoxCouleur = new JComboBox<>(stringCouleur);

        vitesse = new JLabel();
        String[] stringVitesse = new String[]{"TRES LENT","LENT","NORMAL"};
        comboBoxVitesse = new JComboBox<>(stringVitesse);

        labelRochers = new JLabel();
        slider = new JSlider();
        labelVue = new JLabel();
        labelVue.setPreferredSize(new Dimension(50,50));

        panelBase.add(play);panelBase.add(checkBox);panelBase.add(chkReseau);panelBase.add(couleur);panelBase.add(comboBoxCouleur);
        panelBase.add(vitesse);panelBase.add(comboBoxVitesse);panelBase.add(labelRochers);panelBase.add(slider);panelBase.add(labelVue);

        // Play
/*        invisible(play);
        play.setPreferredSize(new Dimension(80,50));*/
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
                        couleurSnake = javafx.scene.paint.Color.RED;
                        cheminGif = "snakeRouge";
                        gif(labelVue,cheminGif);
                    }
                    case "BLEU" -> {
                        couleurSnake = javafx.scene.paint.Color.BLUE;
                        cheminGif = "snakeBleu";
                        gif(labelVue,cheminGif);
                    }
                    case "VERT" -> {
                        couleurSnake = javafx.scene.paint.Color.GREEN;
                        cheminGif = "snakeVert";
                        gif(labelVue,cheminGif);
                    }
                    case "ROSE" -> {
                        couleurSnake = javafx.scene.paint.Color.PINK;
                        cheminGif = "snakeRose";
                        gif(labelVue,cheminGif);
                    }
                    case "ORANGE" -> {
                        couleurSnake = javafx.scene.paint.Color.ORANGE;
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


    public void gif(JLabel labelVue, String chemin){
        ImageIcon gif = new ImageIcon(this.getClass().getResource("/gifMenu/"+ chemin + ".gif"));
        labelVue.setIcon(gif);
    }

    public void invisible(JButton b){
        b.setOpaque(false);
        b.setContentAreaFilled(false);
        b.setBorderPainted(false);
        b.setText("");
    }

}


