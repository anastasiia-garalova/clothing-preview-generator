package de.kunstundstil.produkte;

import de.kunstundstil.pojo.RechteckAufDemProdukt;
import de.kunstundstil.polygons.PolygonDesProdukt;
import javafx.scene.Group;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.io.IOException;

/**
 * Diese Klasse erzeugt dynamisch ein Produkt-Objekt (z.B. TShirt, Pullower oder Polygon)
 * und erstellt das dazugehörige Rechteck, das als Clip für das Bild des Produkts verwendet werden kann.
 * <p>
 * Sie verwaltet:
 * <ul>
 *     <li>das aktuelle Produkt</li>
 *     <li>das rechteckige Feld (Rectangle) für das Produkt</li>
 * </ul>
 * und fügt das Produkt in die angegebenen JavaFX-Gruppen ein.
 *
 * <p>
 * 🇷🇺 Этот класс динамически создает объект продукта (например, TShirt, Pullower или Polygon)
 * и создает соответствующий прямоугольник (Rectangle), который используется как clip для изображения продукта.
 * Класс управляет:
 * <ul>
 *     <li>текущим продуктом</li>
 *     <li>прямоугольным полем для продукта</li>
 * </ul>
 * и добавляет продукт в указанные JavaFX-группы.
 */
public class ProductGenerator {
    /** Das aktuell erzeugte Produkt */
    private Produkt produkt;

    /** Rechteck, das als Clip für das Produktbild dient */
    private Rectangle rectangle;

    /**
     * Konstruktor, der ein Produkt anhand seines Namens erstellt und auf der Szene platziert.
     *
     * @param backgroundGroupLinks Gruppe, in die das Produktbild gelegt wird
     * @param overlayGroup Gruppe für Overlay-Elemente
     * @param bildAufDemProduktGroup Gruppe für das Bild des Produkts
     * @param nameDesProdukts Name des zu erzeugenden Produkts ("TSchirt", "Pullower", "Polygon")
     * @param bildAufDemProduktView ImageView, in dem das Produkt angezeigt wird
     * @param farbenCreisePanel Gruppe für Farbauswahlkreise
     * @throws IOException bei Problemen beim Laden der Bilddateien
     */
    public ProductGenerator(Group backgroundGroupLinks, Group overlayGroup, Group bildAufDemProduktGroup, String nameDesProdukts,
                            ImageView bildAufDemProduktView, Group farbenCreisePanel) throws IOException {

        RechteckAufDemProdukt rechteckAufDemProdukt = new RechteckAufDemProdukt();

        /* Sone wo wir das Bild einlegen */
        rectangle = new Rectangle(
                rechteckAufDemProdukt.getRectangelX(),
                rechteckAufDemProdukt.getRectangelY(),
                rechteckAufDemProdukt.getRectangleWidth(),
                rechteckAufDemProdukt.getRectangelHeight());
        rectangle.setFill(Color.TRANSPARENT);
        rectangle.setStrokeWidth(1);
        rectangle.setStroke(Color.BLACK);
        rectangle.setArcHeight(20);
        rectangle.setArcWidth(30);

        // Löschen alten Produkt aus Group
        if (produkt != null && produkt.getImageDesProduktsView() != null) {
            backgroundGroupLinks.getChildren().remove(produkt.getImageDesProduktsView());
        }

        // Создаём новый продукт по имени
        switch (nameDesProdukts) {
            case "TSchirt":
                produkt = new TShirt();
                break;
            case "Pullower":
                produkt = new Pullover();
                break;
            case "Polygon":
                produkt = new PolygonDesProdukt();
                break;
            default:
                produkt = new TShirt();
        }

        // Добавляем новый продукт на сцену

        produkt.produktErstellen(backgroundGroupLinks, overlayGroup, bildAufDemProduktGroup,rectangle, rechteckAufDemProdukt,
                bildAufDemProduktView, farbenCreisePanel);

    }

    /** Gibt das Rectangle zurück, das als Clip für das Produktbild verwendet wird */
    public Rectangle getRectangle() {
        return rectangle;
    }

    /** Gibt das aktuell erzeugte Produkt zurück */
    public Produkt getProdukt() {
        return produkt;
    }

}
