package de.kunstundstil.test;

import de.kunstundstil.fenster.buttons.*;
import de.kunstundstil.produkte.ProductGenerator;
import de.kunstundstil.fenster.Header;
import de.kunstundstil.print.BildSpeicherung;
import de.kunstundstil.produkte.BildAufDemProdukt;
import de.kunstundstil.pojo.RechteckAufDemProdukt;
import javafx.application.Application;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

/*
 * Hauptklasse der JavaFX-Anwendung zur Darstellung und Anpassung von Produkten.
 *
 * <p>Die Anwendung ermöglicht unter anderem das Auswählen eines Produkts,
 * Ändern der Produktfarbe, Hochladen eines Bildes, Verschieben und Skalieren
 * des Bildes sowie das Speichern des fertigen Ergebnisses.</p>
 *
 * <p>Zusätzlich steht eine Polygon-Zeichenfunktion zur Verfügung,
 * mit der Produktbereiche definiert und gespeichert werden können.</p>
 */


/*
 * @see de.kunstundstil.produkte.ProductGenerator
 * @see de.kunstundstil.produkte.BildAufDemProdukt
 * @see de.kunstundstil.print.BildSpeicherung
 * @see de.kunstundstil.fenster.Header
 * @see de.kunstundstil.fenster.buttons.ButtonFarbe
 * @see de.kunstundstil.fenster.buttons.BildHerunterladung
 * @see de.kunstundstil.fenster.buttons.ButtonVergroesernUndVerkleinen
 * @see de.kunstundstil.fenster.buttons.ButtonAuswaehlDesProdukts
 */
public class BildanzeigeAppTest extends Application {

    private final Group farbenKreisePanel = new Group();

    @Override
    public void start(Stage primaryStage) throws IOException {

        /* Hauptgruppe */
        Group root = new Group();
        Scene scene = new Scene(root, 800, 600, Color.web("#C8BBAA"));

        Image icon = new Image("file:resources/image/icon.png");

        /* Header */
        Group headerGroup = new Group();


        Header header = new Header();
        header.headerErstellen("Gestalte deinen Stil!", headerGroup);


        /* Linke Seite */
        Group backgroundGroup = new Group();

        /* Bilder auf dem Produkt */
        Group bildAufDemProduktGroup = new Group();

        /* Alle anderen Objekte auf dem Fenster */
        Group overlayGroup = new Group();

        /* Objekt des Bildes auf dem Produkt */
        BildAufDemProdukt bildAufDemProduktObjekt = new BildAufDemProdukt();
        ImageView bildAufDemProduktView = bildAufDemProduktObjekt.erstellen();

        /* Prüfen, welches Bild wir haben: horizontal oder vertikal.
            Das hilft uns, das Bild richtig zu clippen. */
        String position;
        if(bildAufDemProduktView.getBoundsInParent().getWidth() == 140){
            position = "horizontal";
        }else{
            position = "vertikal";
        }

        /* Aktuelles Produkt über AtomicReference verwalten */
        AtomicReference<ProductGenerator> hauptBody = new AtomicReference<>(new ProductGenerator(backgroundGroup, overlayGroup,
                bildAufDemProduktGroup, "Pullower", bildAufDemProduktView, farbenKreisePanel));

        /* Button mit der Farbe des Produkts */
        new ButtonFarbe(overlayGroup, hauptBody);

        /* Funktion zum Hochladen eines neuen Bildes */
        new BildHerunterladung(primaryStage, overlayGroup, bildAufDemProduktView, hauptBody);

        /* Schwarzes Rechteck auf dem Produkt */
        RechteckAufDemProdukt rechteckAufDemProdukt = new RechteckAufDemProdukt();

        /* Das Bild wird verschoben */
        AtomicInteger fitZahlFuerVergleichen = new AtomicInteger(140);
        bildAufDemProduktObjekt.bildLaufenWird(overlayGroup, bildAufDemProduktGroup, bildAufDemProduktView, position,
                hauptBody.get().getRectangle(), fitZahlFuerVergleichen, rechteckAufDemProdukt);

        /* Button "Verkleinern" und "Vergroessern" */
        ButtonVergroesernUndVerkleinen buttonAufDemFenster = new ButtonVergroesernUndVerkleinen();
        buttonAufDemFenster.buttonsVergroesernUndVerkleinenErstellen(overlayGroup, bildAufDemProduktView, fitZahlFuerVergleichen);

        /* Produkt auswaehlen, das uns interessiert */
        new ButtonAuswaehlDesProdukts(backgroundGroup,  bildAufDemProduktGroup, overlayGroup, hauptBody,
                bildAufDemProduktObjekt, bildAufDemProduktView,  position,  fitZahlFuerVergleichen, rechteckAufDemProdukt, farbenKreisePanel);

        /* Gruppe fuer den Snapshot */
        Group snapshotGroup = new Group();

        /* Button, um das generierte Bild zu speichern */
        BildSpeicherung bildSpeicherung = new BildSpeicherung(root, snapshotGroup,hauptBody);

        /* Polygon-Zeichenfunktion */
        Pane zeichenFlaeche = erstellenPolygonBereich(overlayGroup);

        /* Gruppe für den Snapshot */
        snapshotGroup.getChildren().addAll(
                backgroundGroup,
                bildAufDemProduktGroup
        );

        /* Ebenen in der richtigen Reihenfolge */
        root.getChildren().addAll(
                headerGroup,
                snapshotGroup,
                zeichenFlaeche,
                overlayGroup
        );

        primaryStage.getIcons().add(icon);
        primaryStage.setTitle("Kunst&Stil");

        /* Grosse von unser Fenster wird fix */
        primaryStage.setResizable(false);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private Pane erstellenPolygonBereich(Group overlayGroup) {

        Pane zeichenFlaeche = new Pane();
        zeichenFlaeche.setPrefSize(800, 600);
        zeichenFlaeche.setMinSize(800, 600);
        zeichenFlaeche.setMaxSize(800, 600);
        zeichenFlaeche.setStyle("-fx-background-color: transparent;");

        new ButtonPolygon(
                overlayGroup,
                zeichenFlaeche
        );
        return zeichenFlaeche;
    }
}
