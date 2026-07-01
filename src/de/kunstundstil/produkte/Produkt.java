package de.kunstundstil.produkte;

import de.kunstundstil.pojo.RechteckAufDemProdukt;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;

import java.io.IOException;

/**
 * Die Klasse Produkt repräsentiert ein Produkt mit Bild, Hintergrund und optionalem Rechteck-Clip.
 * Sie ermöglicht das Erstellen eines Produkt-Views auf einer JavaFX-Gruppe und das Setzen der Farbe.
 */
public class Produkt {

    /** Name des Produkts */
    private String name;

    /** ImageView des Produktbildes */
    protected ImageView imageDesProduktsView;

    /** Hintergrundbild (Fabric) des Produkts */
    private Image fabric;

    public ImageView getImageDesProduktsView() {
        return imageDesProduktsView;
    }

    /**
     * Erstellt das Produkt auf den angegebenen Gruppen.
     * Setzt Position, Größe, Farbe und optional den Clip für das Bild.
     *
     * @param backgroundGroupLinks Gruppe für den Hintergrund des Produkts
     * @param overlayGroup Overlay-Gruppe für zusätzliche Elemente
     * @param bildAufDemProduktGroup Gruppe, in die das Bild des Produkts eingefügt wird
     * @param rectangle Rechteckrahmen des Produkts
     * @param rechteckAufDemProdukt Rechteck zum Clippen des Bildes
     * @param bildAufDemProduktView ImageView des Produkts
     * @param farbenCreisePanel Gruppe für Farbauswahl-Kreise
     * @throws IOException Wenn das Laden von Bildern fehlschlägt
     */
    public void produktErstellen(Group backgroundGroupLinks, Group overlayGroup, Group bildAufDemProduktGroup, Rectangle rectangle,
                                 RechteckAufDemProdukt rechteckAufDemProdukt, ImageView bildAufDemProduktView, Group farbenCreisePanel) throws IOException {

        imageDesProduktsView = new ImageView();
        imageDesProduktsView.setX(50);
        imageDesProduktsView.setY(100);
        imageDesProduktsView.setFitWidth(400);
        imageDesProduktsView.setFitHeight(400);

        // Loeschen alter Produkt wenn er ist
        if (!backgroundGroupLinks.getChildren().isEmpty()) {
            backgroundGroupLinks.getChildren().clear();
        }


        backgroundGroupLinks.getChildren().add(imageDesProduktsView);
        backgroundGroupLinks.getChildren().add(rectangle);

        setFarbe("Weiss");

        /*die laufen auf dem T_Schirt wird*/
        if(rechteckAufDemProdukt != null){
            Rectangle clipDasBild = new Rectangle(
                    rechteckAufDemProdukt.getRectangelX(),
                    rechteckAufDemProdukt.getRectangelY(),
                    rechteckAufDemProdukt.getRectangleWidth(),
                    rechteckAufDemProdukt.getRectangelHeight());
            bildAufDemProduktView.setClip(clipDasBild);
        }

        bildAufDemProduktGroup.setVisible(true);

    }

    /**
     * Setzt die Farbe des Produkts.
     *
     * @param farbe Name der Farbe
     */
    public void setFarbe(String farbe) {

    }

    /**
     * Gibt den Namen des Produkts zurück.
     *
     * @return Name des Produkts
     */
    public String getName() {
        return name;
    }

    /**
     * Setzt das Hintergrundbild (Fabric) des Produkts.
     *
     * @param fabric Hintergrundbild des Produkts
     */
    public void setFabric(Image fabric) {
        this.fabric = fabric;
    }
}
