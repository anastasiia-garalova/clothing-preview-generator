package de.kunstundstil.polygons;

import de.kunstundstil.pojo.RechteckAufDemProdukt;
import de.kunstundstil.produkte.Pullover;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class PolygonDesProdukt extends Pullover {

    private Polygon polygonDesProdukts;
    private double offsetX; // Das für bildZiehenMitMaus. Erste PositionX von Maus
    private double offsetY; // Das für bildZiehenMitMaus. Erste PositionY von Maus
    private double patternOffsetX = 0; // Das für bildZiehenMitMaus. PositionX von Maus, die zichet
    private double patternOffsetY = 0; // Das für bildZiehenMitMaus. PositionY von Maus, die zichet
    private double currentZoom = 1.0; // wie Viel mal Das Groß von unerem Bild verändert
    private double fabricWidth; // Width vom Bild mit der Änderung
    private double fabricHeight; //Height vom Bild mit der Änderung

    private Image fabric; // Fon (Background) unseres Poligon


    public void setFabric(Image fabric) {
        this.fabric = fabric;
    }


    @Override
    public void produktErstellen(Group backgroundGroupLinks, Group overlayGroup, Group bildAufDemProduktGroup,
                                 Rectangle rectangle, RechteckAufDemProdukt rechteckAufDemProdukt,
                                 ImageView bildAufDemProduktView, Group farbenCreisePanel) throws IOException {

        /* Größe des Produkts anpassen */
        if(imageDesProduktsView != null){
            imageDesProduktsView.setFitHeight(350);
            rectangle.setHeight(230);
            rechteckAufDemProdukt.setRectangelHeight(230);
        }

        /* Polygon des Produkts erstellen */
        polygonDesProdukts = new Polygon();

        /* Koordinaten des Polygons aus der Datei laden */
        Path path = Paths.get("resources/polygons/pullower.txt");
        List<String> lines = Files.readAllLines(path);

        /* Datei zeilenweise einlesen */
        for (String line : lines) { // die Datei wird zeilenweise eingelesen
            String[] parts = line.split(",");
            for (String part : parts) {
                part = part.trim();
                //Ist der String nicht leer, wird er in eine Zahl konvertiert und dem Polygon hinzugefügt
                if (part.isEmpty()) continue;
                polygonDesProdukts.getPoints().add(Double.parseDouble(part));
            }
        }

        fabric = bildAufDemProduktView.getImage();

        fabricWidth = fabric.getWidth();
        fabricHeight = fabric.getHeight();

        fabricPatternErstellen();

        polygonDesProdukts.setStroke(Color.BLACK);
        polygonDesProdukts.setStrokeWidth(2);

        rectangle.setVisible(false);
        bildAufDemProduktGroup.setVisible(false);

        backgroundGroupLinks.getChildren().add(polygonDesProdukts);

        /* Bild mit der Maus bewegen und zoomen */
        bildZiehenMitMaus();
        bildZoomen();
    }


    @Override
    public void setFarbe(String farbe) {

        String pfad = "file:resources/image/pullower/Pullower_" + farbe + ".png";
        Image image = new Image(pfad, 400, 400, true, true);
        super.imageDesProduktsView.setImage(image);
    }


    private void bildZoomen() {

        polygonDesProdukts.setOnScroll(event -> {

            double zoomFactor = 1.05;

            if(event.getDeltaY() < 0) {
                zoomFactor = 1 / zoomFactor;

            } else if (event.getDeltaY() > 0) {
                zoomFactor = 1 * zoomFactor;
            }

            currentZoom = currentZoom * zoomFactor;

            fabricWidth = fabric.getWidth() * currentZoom;
            fabricHeight = fabric.getHeight() * currentZoom;

            fabricPatternErstellen();
        });
    }


    private void bildZiehenMitMaus() {

        polygonDesProdukts.setOnMousePressed(event -> {
            if( event.getButton() == MouseButton.PRIMARY ){ //MouseButton.PRIMARY - Linke Button von Maus
                offsetX = event.getX();
                offsetY = event.getY();
            }
        });

        polygonDesProdukts.setOnMouseDragged(event -> {

            double positionPoligonXunterschid =  event.getX() - offsetX;
            double positionPoligonYunterschid =  event.getY() - offsetY;

            patternOffsetX = patternOffsetX + positionPoligonXunterschid;
            patternOffsetY = patternOffsetY + positionPoligonYunterschid;

            fabricPatternErstellen();

            offsetX = event.getX();
            offsetY = event.getY();
        });
    }


    public  void fabricPatternErstellen(){
        /* Erstellen des Stoffmusters */
        ImagePattern fabricPattern = new ImagePattern(
                fabric,
                patternOffsetX, patternOffsetY, /* Position X und Y */
                fabricWidth,                    /* Breite des Musters */
                fabricHeight,                   /* Höhe des Musters */
                false                           /* false = Bild wird angezeigt, true = Bild wird nicht angezeigt */
        );

        /* Stoffmuster auf das Produkt anwenden */
        polygonDesProdukts.setFill(fabricPattern);
    }


    public Polygon getPolygonDesProdukts() {
        return polygonDesProdukts;
    }
}
