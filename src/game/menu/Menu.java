package game.menu;

import game.App;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Objects;

import static game.Donnees.*;
import static game.Game.*;

public class Menu extends JFrame {
    private JPanel panel1;
    private JPanel panelBase;
    private JPanel vueJeu;
    private JButton play;
    private JCheckBox chkReseau;
    private JCheckBox checkBox;
    private JCheckBox chkAngle;
    private JComboBox<String> comboBoxCouleur;
    private JComboBox<String> comboBoxVitesse;
    private JSlider slider;
    private JLabel labelVue;
    private JLabel labelMurs;
    private JLabel Couleur;
    private JCheckBox checkBoxSon;
    private String cheminGif = "snakeRouge";

    public Menu() {
        // Sons
        musique_principale.setVolume(0.8);

        musique_principale.setAutoPlay(true);

        setTitle("SNAKE");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        // Icone
        try {
            ImageIcon icon = new ImageIcon("src/img/menu/iconSnake.png");
            setIconImage(icon.getImage());
        } catch (Exception e) {
            msg("Icone Manquante.");
        }


        // Image de fond
        try {
            Image fond = Toolkit.getDefaultToolkit().getImage("src/img/menu/fond.png");
            MediaTracker mt = new MediaTracker(this);
            mt.addImage(fond, 0);
            mt.waitForAll();
            setContentPane(new ContentPane(fond));
        } catch (Exception e) {
            msg("Fond Manquant.");
        }

        // Ecran centré
        setLocation(500, 100);
        setSize(940, 845);

        add(panel1);

        // Play
        invisible(play);
        play.addActionListener(e -> {
            setVisible(false);
            App.begin();
        });

        // IA
        checkBox.setSize(new Dimension(20, 20));
        ImageIcon iconCheckBoxIABase = new ImageIcon(new ImageIcon("src/img/menu/iaNon.png").getImage().getScaledInstance(20, 20, Image.SCALE_DEFAULT));
        checkBox.setIcon(iconCheckBoxIABase);
        checkBox.addActionListener(e -> {
            if (versusMode) {
                ImageIcon iconCheckBoxIAOui = new ImageIcon(new ImageIcon("src/img/menu/iaOui.png").getImage().getScaledInstance(20, 20, Image.SCALE_DEFAULT));
                checkBox.setIcon(iconCheckBoxIAOui);
            } else {
                ImageIcon iconCheckBoxIANon = new ImageIcon(new ImageIcon("src/img/menu/iaNon.png").getImage().getScaledInstance(20, 20, Image.SCALE_DEFAULT));
                checkBox.setIcon(iconCheckBoxIANon);
            }
        });
        checkBox.addChangeListener(e -> versusMode = !versusMode);

        // Murs
        slider.setValue(0);
        slider.setMinimum(0);
        slider.setMaximum(5);
        slider.addChangeListener(e -> {
            nbMurs = slider.getValue();
            labelMurs.setText("Nombre de Murs : " + nbMurs);
        });


        // Couleurs
        ItemListener comboItemListenerCouleur = e1 -> {
            if (e1.getStateChange() == ItemEvent.SELECTED) {
                String color = (String) e1.getItem();
                switch (color) {
                    case "ROUGE" -> {
                        couleurSnake = javafx.scene.paint.Color.RED;
                        cheminGif = "snakeRouge";
                        gif(labelVue, cheminGif);
                    }
                    case "BLEU" -> {
                        couleurSnake = javafx.scene.paint.Color.BLUE;
                        cheminGif = "snakeBleu";
                        gif(labelVue, cheminGif);
                    }
                    case "VERT" -> {
                        couleurSnake = javafx.scene.paint.Color.GREEN;
                        cheminGif = "snakeVert";
                        gif(labelVue, cheminGif);
                    }
                    case "ROSE" -> {
                        couleurSnake = javafx.scene.paint.Color.PINK;
                        cheminGif = "snakeRose";
                        gif(labelVue, cheminGif);
                    }
                    case "ORANGE" -> {
                        couleurSnake = javafx.scene.paint.Color.ORANGE;
                        cheminGif = "snakeOrange";
                        gif(labelVue, cheminGif);
                    }
                    case "BLANC" -> {
                        couleurSnake = javafx.scene.paint.Color.WHITE;
                        cheminGif = "snakeBlanc";
                        gif(labelVue, cheminGif);
                    }
                }
            }
        };
        comboBoxCouleur.addItemListener(comboItemListenerCouleur);

        // Panel animé
        gif(labelVue, cheminGif);

        // Vitesse
        ItemListener comboItemListenerVitesse = e1 -> {
            if (e1.getStateChange() == ItemEvent.SELECTED) {
                String color = (String) e1.getItem();
                switch (color) {
                    case "TROP LENT" -> vitesse_initiale = 1;
                    case "LENT" -> vitesse_initiale = 2.5;
                    case "NORMAL" -> vitesse_initiale = 5;
                    case "RAPIDE" -> vitesse_initiale = 8;
                    case "HARDCORE" -> vitesse_initiale = 10;
                }
            }
        };
        comboBoxVitesse.setSelectedIndex(2);
        comboBoxVitesse.addItemListener(comboItemListenerVitesse);

        // Reseau
        chkReseau.setSize(new Dimension(20, 20));
        ImageIcon iconCheckBoxReseauBase = new ImageIcon(new ImageIcon("src/img/menu/croix.png").getImage().getScaledInstance(20, 20, Image.SCALE_DEFAULT));
        chkReseau.setIcon(iconCheckBoxReseauBase);
        chkReseau.addActionListener(e -> {
            if (reseauMode) {
                ImageIcon iconCheckBoxReseauOui = new ImageIcon(new ImageIcon("src/img/menu/valide.png").getImage().getScaledInstance(20, 20, Image.SCALE_DEFAULT));
                chkReseau.setIcon(iconCheckBoxReseauOui);
            } else {
                ImageIcon iconCheckBoxReseauNon = new ImageIcon(new ImageIcon("src/img/menu/croix.png").getImage().getScaledInstance(20, 20, Image.SCALE_DEFAULT));
                chkReseau.setIcon(iconCheckBoxReseauNon);
            }
        });
        chkReseau.addChangeListener(e -> reseauMode = !reseauMode);

        // CheckBox Son
        checkBoxSon.setSize(new Dimension(30, 30));
        ImageIcon iconCheckBoxSonBase = new ImageIcon(new ImageIcon("src/img/menu/son.png").getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT));
        checkBoxSon.setIcon(iconCheckBoxSonBase);
        checkBoxSon.addActionListener(e -> {
            if (sonMode) {
                musique_principale.play();
                ImageIcon iconCheckBoxSonOui = new ImageIcon(new ImageIcon("src/img/menu/son.png").getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT));
                checkBoxSon.setIcon(iconCheckBoxSonOui);
            } else {
                musique_principale.pause();
                ImageIcon iconCheckBoxSonNon = new ImageIcon(new ImageIcon("src/img/menu/sonCoupe.png").getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT));
                checkBoxSon.setIcon(iconCheckBoxSonNon);
            }
        });
        checkBoxSon.addChangeListener(e -> sonMode = !sonMode);

        // Mode Angle
        chkAngle.setSize(new Dimension(20, 20));
        ImageIcon iconCheckBoxAngleBase = new ImageIcon(new ImageIcon("src/img/menu/croix.png").getImage().getScaledInstance(20, 20, Image.SCALE_DEFAULT));
        chkAngle.setIcon(iconCheckBoxAngleBase);
        chkAngle.addActionListener(e -> {
            if (modeAngle) {
                ImageIcon iconCheckBoxAngleOui = new ImageIcon(new ImageIcon("src/img/menu/valide.png").getImage().getScaledInstance(20, 20, Image.SCALE_DEFAULT));
                chkAngle.setIcon(iconCheckBoxAngleOui);
            } else {
                ImageIcon iconCheckBoxAngleNon = new ImageIcon(new ImageIcon("src/img/menu/croix.png").getImage().getScaledInstance(20, 20, Image.SCALE_DEFAULT));
                chkAngle.setIcon(iconCheckBoxAngleNon);
            }
        });
        chkAngle.addChangeListener(e -> modeAngle = !modeAngle);

        setVisible(true);
    }


    /**
     * @param labelVue : Le label auquel on attribue le gif.
     * @param chemin   : Le chemin du dossier où se trouve le gif.
     */
    public void gif(JLabel labelVue, String chemin) {
        try {
            ImageIcon gif = new ImageIcon(Objects.requireNonNull(this.getClass().getResource("/gifMenu/" + chemin + ".gif")));
            labelVue.setIcon(gif);
            Border border = BorderFactory.createLineBorder(java.awt.Color.BLACK, 5);
            labelVue.setBorder(border);
        } catch (Exception e) {
            labelVue.setPreferredSize(new Dimension(400, 400));
            labelVue.setText("Gif Manquant.");
        }
    }

    /**
     * Pour rendre invisible un JButton.
     *
     * @param b : Le bouton à rendre invisible.
     */
    public void invisible(JButton b) {
        b.setOpaque(false);
        b.setContentAreaFilled(false);
        b.setBorderPainted(false);
        b.setText("");
    }
}

/**
 * Pour mettre un fond sur la JFrame.
 */
class ContentPane extends JPanel {
    private final Image image;

    public ContentPane(Image fond) {
        super();
        image = fond;
    }

    public void paintComponent(Graphics g) {
        g.drawImage(image, 0, 0, null);
    }
}