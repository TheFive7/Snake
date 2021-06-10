module Snake {
    requires javafx.graphics;
    requires javafx.controls;
    requires javafx.media;
    requires java.desktop;

    opens game;
    opens game.reseau;
}