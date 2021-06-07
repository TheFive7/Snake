module Snake {
    requires javafx.graphics;
    requires javafx.controls;
    requires java.desktop;

    opens game;
    opens game.reseau;
}