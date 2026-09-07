package de.kunstundstil.fenster.buttons;

import de.kunstundstil.produkte.ProductGenerator;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Die Klasse ButtonFarbe erzeugt farbige Auswahlkreise für ein Produkt.
 * Durch Klicken auf einen Kreis wird die Farbe des aktuellen Produkts geändert.
 */
public class ButtonFarbe {

    /** Referenz auf das aktuelle Produkt, dessen Farbe geändert werden soll. */
    private final AtomicReference<ProductGenerator> hauptBody;
    /** Gruppe, die alle Farbkreise enthält und auf der Szene angezeigt wird. */
    private Group farbenCreisePanel;

    /**
     * Konstruktor: Initialisiert die Farbkreise und fügt sie auf der Background-Gruppe hinzu.
     *
     * @param backgroundGroup Gruppe, auf der die Kreise angezeigt werden
     * @param hauptBody AtomicReference auf das aktuelle Produkt, dessen Farbe geändert werden soll
     */
    public ButtonFarbe(Group backgroundGroup, AtomicReference<ProductGenerator> hauptBody){

        this.hauptBody = hauptBody;

        Image gelberKreis = new Image("file:resources/image/farbe/Farbe_" + Farbe.GELB.getFarbe() + ".png");
        Image blauerKreis = new Image("file:resources/image/farbe/Farbe_" + Farbe.BLAU.getFarbe() + ".png");
        Image schwarzerKreis = new Image("file:resources/image/farbe/Farbe_" + Farbe.SCHWARZ.getFarbe() + ".png");
        Image weisserKreis = new Image("file:resources/image/farbe/Farbe_" + Farbe.WEISS.getFarbe() + ".png");
        Image rosaKreis = new Image("file:resources/image/farbe/Farbe_" + Farbe.ROSA.getFarbe() + ".png");
        Image gruenerKreis = new Image("file:resources/image/farbe/Farbe_" + Farbe.GRUEN.getFarbe() + ".png");

        List<Image> kreises = new ArrayList<>(List.of(
                gelberKreis,
                blauerKreis,
                schwarzerKreis,
                weisserKreis,
                rosaKreis,
                gruenerKreis
        ));

        imageViewErschtellen(backgroundGroup, kreises);
    }

    /**
     * Erstellt ImageViews für die Farbkreise, positioniert sie und fügt sie der Overlay-Gruppe hinzu.
     *
     * @param overlayGroup Gruppe, in der die Farbkreise angezeigt werden
     * @param kreises Liste der Kreis-Bilder
     */
    public void imageViewErschtellen(Group overlayGroup, List<Image> kreises){

        farbenCreisePanel = new Group();
        double kreisSetX = 0;

        for (Image kreis : kreises) {

            ImageView kreisView = new ImageView(kreis);

            /* Größe des Kreises */
            kreisView.setFitWidth(40);
            kreisView.setFitHeight(40);

            /* Position des Kreises */
            kreisView.setX(450 + kreisSetX);
            kreisView.setY(170);
            kreisSetX += 50;

            /* Farbe auswählen */
            eineFarbeAuswaehlen(kreisView);

            /* Kreis zum Farben-Panel hinzufügen */
            farbenCreisePanel.getChildren().add(kreisView);
        }
        overlayGroup.getChildren().add(farbenCreisePanel);
    }

    /**
     * Setzt die Klickaktion auf einen Farbkreis, um die Farbe des Produkts zu ändern.
     *
     * @param kreisView ImageView des Farbkreises
     */
    private void eineFarbeAuswaehlen(ImageView kreisView) {
        /* Das Bild klickbar machen */
        kreisView.setOnMouseClicked(event -> {

            /* Region Autor Susenne */
            ImageView clicked = (ImageView) event.getSource();
            Image imageKreis = clicked.getImage();
            String imagePfad = imageKreis.getUrl();

            String farbeUrl = imagePfad.substring(imagePfad.lastIndexOf("_")+1).replace(".png","");
            /* endRegion  */

            hauptBody.get().getProdukt().setFarbe(farbeUrl);
        });
    }

    /**
     * Gibt die Gruppe mit den Farbkreisen zurück.
     *
     * @return farbenCreisePanel
     */
    public Group getFarbenCreisePanel() {
        return farbenCreisePanel;
    }
}
