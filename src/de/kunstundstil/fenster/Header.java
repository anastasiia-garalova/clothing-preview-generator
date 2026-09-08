package de.kunstundstil.fenster;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

/**
 * Die Klasse Header dient dazu, einen Text-Header auf einer JavaFX-Gruppe anzuzeigen.
 * Sie erstellt ein Textobjekt mit definierter Schriftart, Farbe und Position.
 */
public class Header {

    /**
     * Erstellt einen Header-Text und fügt ihn der angegebenen Gruppe hinzu.
     *
     * @param begriff Der Text, der als Header angezeigt werden soll
     * @param headerGroup Die JavaFX-Gruppe, in die der Header-Text eingefügt wird
     */
    public void headerErstellen(String begriff, Group headerGroup){
        Text titel = new Text(begriff);
        titel.setX(200);
        titel.setY(50);
        titel.setFont(Font.font("Georgia", 36));
        titel.setFill(Color.web("#2C2520"));

        headerGroup.getChildren().add(titel);
    }
}
