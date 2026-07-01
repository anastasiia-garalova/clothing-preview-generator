package de.kunstundstil.produkte;

import de.kunstundstil.fenster.buttons.ButtonFarbe;
import de.kunstundstil.pojo.RechteckAufDemProdukt;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;

import java.io.IOException;

/**
 * Die Klasse Pullower erweitert die Klasse Produkt.
 * Sie repräsentiert ein Pullover-Produkt mit spezifischen Maßen und Funktionen.
 * Ermöglicht das Erstellen des Pullover-Views und das Setzen der Farbe.
 */
public class Pullower extends Produkt{

    /** Name des Pullover-Produkts */
    private String name;

    /**
     * Konstruktor mit individuellem Namen.
     *
     * @param name Name des Pullovers
     */
    public Pullower(String name) {
        this.name = name;
    }

    /**
     * Standard-Konstruktor.
     * Setzt den Namen auf "Pullower".
     */
    public Pullower() {
        this.name = "Pullower";
    }

    /**
     * Erstellt das Pullover-Produkt auf den angegebenen Gruppen.
     * Passt Höhe des Bildes, Rechteckrahmen und Clip entsprechend an.
     *
     * @param backgroundGroupLinks Gruppe für den Hintergrund des Produkts
     * @param overlayGroup Overlay-Gruppe für zusätzliche Elemente
     * @param bildAufDemProduktGroup Gruppe, in die das Bild des Produkts eingefügt wird
     * @param rectangle Rechteckrahmen des Produkts
     * @param rechteckAufDemProdukt Rechteck für das Clipping des Bildes
     * @param bildAufDemProduktView ImageView des Produkts
     * @param farbenCreisePanel Gruppe für Farbauswahl-Kreise
     * @throws IOException Wenn das Laden von Bildern fehlschlägt
     */
    @Override
    public void produktErstellen(Group backgroundGroupLinks, Group overlayGroup, Group bildAufDemProduktGroup, Rectangle rectangle, RechteckAufDemProdukt rechteckAufDemProdukt,
                                 ImageView bildAufDemProduktView, Group farbenCreisePanel) throws IOException {

        super.produktErstellen(backgroundGroupLinks, overlayGroup, bildAufDemProduktGroup, rectangle, rechteckAufDemProdukt, bildAufDemProduktView,
                 farbenCreisePanel);

        if(imageDesProduktsView != null){
            imageDesProduktsView.setFitHeight(350);
            rectangle.setHeight(230);
            rechteckAufDemProdukt.setRectangelHeight(230);
        }

        // Optional: Bild clippen, damit es innerhalb des Rechtecks bleibt
        if(rechteckAufDemProdukt != null){
            Rectangle clipDasBild = new Rectangle(
                    rechteckAufDemProdukt.getRectangelX(),
                    rechteckAufDemProdukt.getRectangelY(),
                    rechteckAufDemProdukt.getRectangleWidth(),
                    rechteckAufDemProdukt.getRectangelHeight());
            bildAufDemProduktView.setClip(clipDasBild);
        }

    }

    /**
     * Setzt die Farbe des Pullovers, indem das entsprechende Bild geladen wird.
     *
     * @param farbe Name der Farbe
     */
    @Override
    public void setFarbe(String farbe) {

        String pfad = "file:resources/image/pullower/Pullower_" + farbe + ".png";
        Image image = new Image(pfad, 400, 400, true, true);
        super.imageDesProduktsView.setImage(image);
    }

    /**
     * Gibt den Namen des Pullovers zurück.
     *
     * @return Name des Produkts
     */
    @Override
    public String getName() {
        return name;
    }
}
