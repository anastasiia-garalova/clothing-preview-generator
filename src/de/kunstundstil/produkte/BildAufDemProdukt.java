package de.kunstundstil.produkte;

import de.kunstundstil.pojo.RechteckAufDemProdukt;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Diese Klasse verwaltet ein Bild, das auf einem Produkt platziert wird.
 * <p>
 * Sie ermöglicht:
 * <ul>
 *     <li>Erstellung des Bildes mit automatischer Anpassung der Größe (horizontal/vertikal)</li>
 *     <li>Verschieben des Bildes innerhalb eines Rechtecks (Produktbereichs) per Maus</li>
 *     <li>Integration des Bildes in verschiedene JavaFX-Gruppen</li>
 * </ul>
 *
 * <p>
 * 🇷🇺 Этот класс управляет изображением, которое размещается на продукте.
 * <p>
 * Он позволяет:
 * <ul>
 *     <li>Создавать изображение с автоматической подгонкой размера (по горизонтали или вертикали)</li>
 *     <li>Перетаскивать изображение внутри прямоугольника (области продукта) с помощью мыши</li>
 *     <li>Добавлять изображение в различные JavaFX-группы</li>
 * </ul>
 */
public class BildAufDemProdukt {

    /** Horizontales Offset beim Verschieben */
    private double offsetX;

    /** Vertikales Offset beim Verschieben */
    private double offsetY;

    /** Das Bild, das auf dem Produkt platziert wird */
    private final Image bildAufDemProdukt;

    /** ImageView für das Produktbild */
    private final ImageView bildAufDemProduktView;

    /**
     * Konstruktor mit Bild und ImageView.
     *
     * @param bildAufDemProdukt Das Bild, das auf dem Produkt angezeigt wird
     * @param bildAufDemProduktView Die zugehörige ImageView
     */
    public BildAufDemProdukt(Image bildAufDemProdukt, ImageView bildAufDemProduktView) {
        this.bildAufDemProdukt = bildAufDemProdukt;
        this.bildAufDemProduktView = bildAufDemProduktView;
    }

    /**
     * Standard-Konstruktor, lädt ein Standardbild.
     */
    public BildAufDemProdukt() {

        this.bildAufDemProdukt   = new Image("file:resources/image/bilder/img.jpg"); //Shark.png img.jpg new_year.png
        this.bildAufDemProduktView = new ImageView(bildAufDemProdukt);
    }

    /**
     * Erstellt die ImageView und passt Größe/Position an
     * basierend auf dem Seitenverhältnis des Bildes.
     *
     * @return Die ImageView des Bildes
     */
    public ImageView erstellen(){

        /* Pruefen welches Bild wir haben: horizontal oder vertikal*/
        if(bildAufDemProdukt.getWidth() > bildAufDemProdukt.getHeight()){
            bildAufDemProduktView.setFitWidth(140);
            bildAufDemProduktView.setPreserveRatio(true);
            /* Bild auf dem Center des Produkt */
            bildAufDemProduktView.setX(180);
            bildAufDemProduktView.setY(200);
        }else{
            bildAufDemProduktView.setFitHeight(140);
            bildAufDemProduktView.setPreserveRatio(true);
            /* Bild auf dem Center des Produkt */
            bildAufDemProduktView.setX(185);
            bildAufDemProduktView.setY(200);

        }

        return bildAufDemProduktView;
    }

    /**
     * Aktiviert das Verschieben des Bildes innerhalb des Rechtecks des Produkts.
     *
     * @param overlayGroup Die Overlay-Gruppe für zusätzliche Elemente
     * @param bildAufDemProduktGroup Die Gruppe, in die das Bild eingefügt wird
     * @param bildAufDemProduktView Die ImageView des Bildes
     * @param position Positionierung (horizontal/vertikal)
     * @param rectangle Rechteck des Produkts, innerhalb dessen das Bild bewegt werden kann
     * @param fitZahlFuerVergleichen AtomicInteger für Vergleichsgrößen
     * @param rechteckAufDemProdukt Rechteckdaten für das Produkt
     */
    public void bildLaufenWird(Group overlayGroup, Group bildAufDemProduktGroup, ImageView bildAufDemProduktView, String position,
                               Rectangle rectangle, AtomicInteger fitZahlFuerVergleichen, RechteckAufDemProdukt rechteckAufDemProdukt){

        bildAufDemProduktView.setOnMousePressed(mausEvent -> {
            offsetX = mausEvent.getSceneX() - bildAufDemProduktView.getX();
            offsetY = mausEvent.getSceneY() - bildAufDemProduktView.getY();
        });

        bildAufDemProduktView.setOnMouseDragged(mausEvent -> {

            double newX = mausEvent.getSceneX() - offsetX;
            double newY = mausEvent.getSceneY() - offsetY;

            double rectMinX = rectangle.getX();
            double rectMinY = rectangle.getY();
            double rectMaxX = rectangle.getX() + rectangle.getWidth();
            double rectMaxY = rectangle.getY() + rectangle.getHeight();

            double imgWidth = bildAufDemProduktView.getBoundsInParent().getWidth();
            double imgHeight = bildAufDemProduktView.getBoundsInParent().getHeight();

            if (newX < rectMinX) newX = rectMinX;
            if (newX + imgWidth > rectMaxX) newX = rectMaxX - imgWidth;

            if (newY < rectMinY) newY = rectMinY;
            if (newY + imgHeight > rectMaxY) newY = rectMaxY - imgHeight;

            bildAufDemProduktView.setX(newX);
            bildAufDemProduktView.setY(newY);
        });

        // Überprüft, ob das Objekt bildAufDemProduktView bereits in der Liste enthalten ist
        if (!bildAufDemProduktGroup.getChildren().contains(bildAufDemProduktView)) {
            bildAufDemProduktGroup.getChildren().add(bildAufDemProduktView);
        }

    }

}
