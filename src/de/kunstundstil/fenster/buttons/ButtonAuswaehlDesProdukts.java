package de.kunstundstil.fenster.buttons;

import de.kunstundstil.pojo.RechteckAufDemProdukt;
import de.kunstundstil.polygons.PolygonDesProdukt;
import de.kunstundstil.produkte.BildAufDemProdukt;
import de.kunstundstil.produkte.ProductGenerator;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Polygon;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Die Klasse ButtonAuswaehlDesProdukts stellt die Benutzeroberfläche zur Produktauswahl bereit.
 * Sie erzeugt kleine Vorschaubilder (ImageViews) der verfügbaren Produkte und ermöglicht es,
 * ein Produkt auszuwählen. Anschließend wird das aktuelle Produkt in der Anwendung aktualisiert.
 */
public class ButtonAuswaehlDesProdukts {

    /** Referenz auf das aktuell ausgewählte Produkt, damit andere Klassen immer auf das aktuelle Produkt zugreifen. */
    private final AtomicReference<ProductGenerator> hauptBody;

    /** Objekt, das das Bild auf dem Produkt verwaltet (verschieben, skalieren, Clip anwenden). */
    private final BildAufDemProdukt bildAufDemProduktObj;

    /** Gruppe für den Hintergrund auf der linken Seite, z.B. Produktabbildung. */
    private final Group backgroundGroupLinks;

    /** Gruppe, in die das aktuelle Produktbild eingefügt wird. */
    private final Group bildAufDemProduktGroup;

    /** Overlay-Gruppe für Buttons, Auswahlpanels und andere UI-Elemente. */
    private final Group overlayGroup;

    /** Das konkrete Bild des Produkts, das angezeigt wird (ImageView). */
    private final ImageView bildAufDemProduktView;

    /** Orientierung des Bildes auf dem Produkt: "horizontal" oder "vertikal". */
    private final String position;

    /** AtomicInteger, das die Grenzwerte für die Größe des Bildes speichert (für Überprüfung und Skalierung). */
    private final AtomicInteger fitZahlFuerVergleichen;

    /** Rechteck, das die sichtbare Fläche des Produkts markiert und als Clip verwendet wird. */
    private RechteckAufDemProdukt rechteckAufDemProdukt;

    /** Gruppe für die Farbauswahlkreise, die beim Produkt angezeigt werden. */
    private final Group farbenCreisePanel;


    /**
     * Konstruktor: Initialisiert alle benötigten Parameter und erstellt die Vorschau-Images der Produkte.
     *
     * @param backgroundGroupLinks Gruppe für Hintergrundbilder
     * @param bildAufDemProduktGroup Gruppe für das Produktbild
     * @param overlayGroup Overlay-Gruppe für Buttons und Panels
     * @param hauptBody AtomicReference auf das aktuelle Produkt
     * @param bildAufDemProduktObj Objekt zum Verwalten des Bildes auf dem Produkt
     * @param bildAufDemProduktView ImageView des Produkts
     * @param position Orientierung ("horizontal" oder "vertikal")
     * @param fitZahlFuerVergleichen AtomicInteger für Größenprüfungen
     * @param rechteckAufDemProdukt Rechteck, das den sichtbaren Bereich des Produkts definiert
     * @param farbenCreisePanel Gruppe für die Farbauswahl
     * @throws IOException bei Fehlern beim Laden der Produktbilder
     */
    public ButtonAuswaehlDesProdukts(Group backgroundGroupLinks, Group bildAufDemProduktGroup, Group overlayGroup,
                                     AtomicReference<ProductGenerator> hauptBody, BildAufDemProdukt bildAufDemProduktObj,
                                     ImageView bildAufDemProduktView, String position, AtomicInteger fitZahlFuerVergleichen,
                                     RechteckAufDemProdukt rechteckAufDemProdukt, Group farbenCreisePanel) throws IOException {
        this.hauptBody = hauptBody;
        this.bildAufDemProduktObj = bildAufDemProduktObj;
        this.bildAufDemProduktGroup = bildAufDemProduktGroup;
        this.backgroundGroupLinks = backgroundGroupLinks;
        this.overlayGroup = overlayGroup;
        this.bildAufDemProduktView = bildAufDemProduktView;
        this.position = position;
        this.fitZahlFuerVergleichen = fitZahlFuerVergleichen;
        this.farbenCreisePanel = farbenCreisePanel;

        Image imageTShirt = new Image("file:resources/image/tshirt/TShirt_Weiss.png");
        Image imagePullover = new Image("file:resources/image/pullover/Pullover_Weiss.png");
        List<Image> productsImages = new ArrayList<>(List.of(imageTShirt, imagePullover));

        PolygonDesProdukt poligon = new PolygonDesProdukt();
        Polygon poligonDesProdukt = poligon.getPolygonDesProdukts();

        imageViewErschtellen(productsImages, poligonDesProdukt);
    }


    /**
     * Erstellt die Vorschau-Bilder (ImageViews) der Produkte und positioniert sie im Auswahlpanel.
     *
     * @param productsImages Liste der Produktbilder
     * @param poligonDesProdukt Polygon, das die Form des Produkts repräsentiert
     * @throws IOException bei Fehlern beim Laden der Polygondaten
     */
    private void imageViewErschtellen(List<Image> productsImages, Polygon poligonDesProdukt) throws IOException {

        Group produktAuswahlPanel  = new Group();
        produktAuswahlPanel.setLayoutX(500); // фиксированное положение панели
        produktAuswahlPanel.setLayoutY(100);

        double produktSetX = 0;

        for (Image produkt : productsImages) {
            ImageView produktWahlView = new ImageView(produkt);

            produktWahlView.setX(produktSetX);
            produktWahlView.setY(0);
            produktWahlView.setFitWidth(50);
            produktWahlView.setFitHeight(50);

            produktSetX += 50;

            einProduktAuswaehlen(produktWahlView);

            produktAuswahlPanel.getChildren().add(produktWahlView);
        }

        Polygon miniPolygonDesProdukts = new Polygon();

        Path path = Paths.get("resources/polygons/pullover.txt");
        List<String> lines = Files.readAllLines(path);

        for (String line : lines) { // die Datei wird zeilenweise eingelesen
            String[] parts = line.split(",");
            for (String part : parts) {
                part = part.trim();
                //Ist der String nicht leer, wird er in eine Zahl konvertiert und dem Polygon hinzugefügt
                if (part.isEmpty()) continue;
                miniPolygonDesProdukts.getPoints().add(Double.parseDouble(part));
            }
        }

        miniPolygonDesProdukts.setLayoutX(-120);
        miniPolygonDesProdukts.setLayoutY(-255);
        miniPolygonDesProdukts.setScaleX(0.14);
        miniPolygonDesProdukts.setScaleY(0.14);
        miniPolygonDesProdukts.setStrokeWidth(1);

        poligonDesProduktAuswaehlen(miniPolygonDesProdukts);

        produktAuswahlPanel .getChildren().add(miniPolygonDesProdukts);
        overlayGroup.getChildren().add(produktAuswahlPanel );
    }


    /**
     * Definiert das Verhalten, wenn das Produkt-Polygon angeklickt wird.
     * Erstellt ein neues ProductGenerator-Objekt und aktualisiert hauptBody.
     *
     * @param miniPolygonDesProdukts Polygon für das anklickbare Produkt
     */

    private void poligonDesProduktAuswaehlen(Polygon miniPolygonDesProdukts) {

        miniPolygonDesProdukts.setOnMouseClicked(event -> {

            /* Farbauswahl beim Polygon ausblenden */
            overlayGroup.getChildren().remove(farbenCreisePanel);

            altenProduktEntfernen();

            ProductGenerator neuesBody;

            try {
                neuesBody = new ProductGenerator(
                        backgroundGroupLinks,
                        overlayGroup,
                        bildAufDemProduktGroup,
                        "Polygon",
                        bildAufDemProduktView,
                        farbenCreisePanel
                );
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            hauptBody.set(neuesBody);
            neuesBody.getRectangle().setVisible(false);

            System.out.println("hauptBody nach Polygon: " + hauptBody.get().getProdukt());
        });
    }

    private void altenProduktEntfernen() {
        if (hauptBody.get() != null && hauptBody.get().getProdukt() != null) {

            ImageView altesProduktBild = hauptBody.get().getProdukt().getImageDesProduktsView();

            if (altesProduktBild != null) {
                backgroundGroupLinks.getChildren().remove(altesProduktBild);
            }

            if (hauptBody.get().getRectangle() != null) {
                backgroundGroupLinks.getChildren().remove(hauptBody.get().getRectangle());
            }
        }
    }

    /**
     * Setzt das Verhalten für die Auswahl eines Produktes durch Klick auf die Produktvorschau.
     * Aktualisiert das aktuelle Produkt (hauptBody) und initialisiert die Interaktion mit dem Bild.
     *
     * @param produktView ImageView der Produktvorschau
     */
    public void einProduktAuswaehlen(ImageView produktView){

        produktView.setOnMouseClicked(event -> {



            /* ---- Nehmen die Name des Produkts ---- */

            /* Das Objekt zurücknehmen, auf das geklickt wurde */
            ImageView clicked = (ImageView) event.getSource();

            /* Nehmen die Image, um Url zu wissen */
            Image imageProdukt = clicked.getImage();

            /* Nehmen die Url, um zu kennen, welches Produkt es ist */
            String imagePfad = imageProdukt.getUrl();

            /* Suchen letzten Slash */
            int lastSlash = imagePfad.lastIndexOf("/"); // indei vor dem Slash

            /* Ersten Unterstrich nach dem letzten Slash suchen */
            int firstUnderscoreAfterSlash = imagePfad.indexOf("_", lastSlash); // Index nah dem T_Schirt

            /* Schneiden den Namen des Produkts aus */
            String produktName = imagePfad.substring(lastSlash + 1, firstUnderscoreAfterSlash);

            ProductGenerator neuerProdukt = null;

            altenProduktEntfernen();

            try {
                neuerProdukt = new ProductGenerator(backgroundGroupLinks, overlayGroup, bildAufDemProduktGroup,
                        produktName, bildAufDemProduktView, farbenCreisePanel);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            hauptBody.set(neuerProdukt);

            if (!overlayGroup.getChildren().contains(farbenCreisePanel)) {
                overlayGroup.getChildren().add(farbenCreisePanel);
            }

            farbenCreisePanel.toFront();

            bildAufDemProduktObj.bildLaufenWird(
                    overlayGroup,                    // In dieser Gruppe alle Buttons steht
                    bildAufDemProduktGroup,          // Die Gruppe, wohin das Bild hinzugefügt werden soll
                    bildAufDemProduktView,           // Bild auf dem Produkt
                    position,                        // horizontal oder vertikal
                    hauptBody.get().getRectangle(),  // Rahmen des Produkts
                    fitZahlFuerVergleichen,          // Grenzwerte der Größe
                    rechteckAufDemProdukt            // Rechteck mit schwaryen Grenzen
            );
        });
    }
}
