package de.kunstundstil.test;

import de.kunstundstil.polygons.PolygonZeichner;
import de.kunstundstil.produkte.ProductGenerator;
import de.kunstundstil.fenster.Header;
import de.kunstundstil.print.BildSpeicherung;
import de.kunstundstil.produkte.BildAufDemProdukt;
import de.kunstundstil.fenster.buttons.BildHerunterladung;
import de.kunstundstil.fenster.buttons.ButtonVergroesernUndVerkleinen;
import de.kunstundstil.fenster.buttons.ButtonFarbe;
import de.kunstundstil.pojo.RechteckAufDemProdukt;
import de.kunstundstil.fenster.buttons.ButtonAuswaehlDesProdukts;
import javafx.application.Application;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.*;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.stage.Stage;

import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Hauptklasse der Anwendung, die die Anzeige und Anpassung von Produkten (T-Shirt, Pullover) demonstriert.
 * <p>
 * Das Projekt zeigt ein Produkt in einem fixierten Fenster. Die Anwendung unterstützt das Laden eines Icons
 * für das Stage sowie eine dynamische Bildausrichtung. Mithilfe von {@code .setClip()} wird sichergestellt,
 * dass das auf dem Produkt platzierte Bild nicht über die Grenzen des vordefinierten Rechtecks hinausragt.
 * </p>
 *
 * <p><b>Entwurfsentscheidung (AtomicReference):</b></p>
 * <ul>
 *     <li>
 *         <b>🇷🇺</b> {@code AtomicReference<ProductGenerator> hauptBody} используется для хранения ссылки на текущий объект продукта.
 *         Это позволяет динамически менять продукт (например, футболку на кофту) во время работы программы.
 *         При этом все остальные элементы интерфейса (например, кнопки выбора цвета или масштабирования) всегда обращаются
 *         к актуальному объекту. Таким образом, мы избегаем проблем с "устаревшими" ссылками и обеспечиваем корректную работу UI.
 *     </li>
 *     <li>
 *         <b>🇩🇪</b> Die Variable {@code AtomicReference<ProductGenerator> hauptBody} dient dazu, eine Referenz auf das aktuell
 *         angezeigte Produkt zu speichern. Dadurch kann das Produkt (z. B. T-Shirt oder Pullover) während der Laufzeit
 *         dynamisch gewechselt werden. Alle anderen UI-Komponenten (wie Farb-Buttons oder Größenänderungen) greifen
 *         dadurch automatisch auf das aktuelle Produkt-Objekt zu, ohne dass veraltete Referenzen ungültig werden.
 *         Dies gewährleistet eine konsistente und dynamische Benutzeroberfläche.
 *     </li>
 * </ul>
 *
 * <p><b>Funktionalitäten:</b></p>
 * <ul>
 *     <li>Erstellt das Hauptfenster mit fixierter Größe und Hintergrundfarbe</li>
 *     <li>Zeigt einen Header-Text an</li>
 *     <li>Lädt und visualisiert Produkte (T-Shirt, Pullover)</li>
 *     <li>Ermöglicht das Verschieben, Skalieren und Ändern der Produktfarben</li>
 *     <li>Unterstützt das Hochladen eines neuen Bildes auf das Produkt</li>
 *     <li>Speichert das aktuell generierte Produkt als PNG-Datei</li>
 *     <li>Bietet eine optionale Polygon-Zeichenfunktion auf der Benutzeroberfläche</li>
 * </ul>
 *
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

    private Polygon polygon;
    private boolean drawing = true;

    private final List<Double> points = new ArrayList<>();
    private final Group farbenKreisePanel = new Group();

    @Override
    public void start(Stage primaryStage) throws IOException {

        /* Hauptgruppe */
        Group root = new Group ();
        Scene scene = new Scene(root, 800, 600, Color.LIGHTBLUE);

        Image icon = new Image("file:resources/image/icon.png");

        /* Header */
        Group headerGroup = new Group();

        Header header = new Header();
        header.headerErstellen("WHOOOA!", headerGroup);

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

        /* AtomicReference verwenden, um unser Produkt aendern zu koennen */
        AtomicReference<ProductGenerator> hauptBody = new AtomicReference<>(new ProductGenerator(backgroundGroup, overlayGroup,
                bildAufDemProduktGroup, "Pullower", bildAufDemProduktView, farbenKreisePanel));

        /* Button mit der Farbe des Produkts */
        new ButtonFarbe(overlayGroup, hauptBody);

        /* Neues Bild wird heruntergeladen */
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

        /* Zeichenfläche für Polygon */
        Pane zeichenFlaeche = new Pane();

        zeichenFlaeche.setPrefSize(800, 600);
        zeichenFlaeche.setMinSize(800, 600);
        zeichenFlaeche.setMaxSize(800, 600);

        zeichenFlaeche.setStyle(
                "-fx-background-color: transparent;"
        );

        /* Polygon-Zeichner erstellen */
        PolygonZeichner polygonZeichner =
                new PolygonZeichner(zeichenFlaeche);

        /* Zeichnen am Anfang ausschalten */
        polygonZeichner.zeichnenBeenden();

        /* Button: Polygon speichern */
        Button polygonSpeichernButton =
                new Button("Polygon speichern");

        polygonSpeichernButton.setLayoutX(185);
        polygonSpeichernButton.setLayoutY(550);

        /* Speichern-Button am Anfang ausblenden */
        polygonSpeichernButton.setVisible(false);


        /* Button: Polygon zeichnen / Zeichnen beenden */
        Button polygonButton =
                new Button("Polygon zeichnen");

        polygonButton.setLayoutX(20);
        polygonButton.setLayoutY(550);

        polygonButton.setOnAction(e -> {

            if (polygonZeichner.istZeichenmodusAktiv()) {

                // Zeichnen beenden
                polygonZeichner.polygonBeenden();

                polygonButton.setText(
                        "Polygon zeichnen"
                );
                polygonSpeichernButton.setVisible(false);

            } else {

                // Zeichnen starten
                polygonZeichner.zeichnenStarten();

                polygonButton.setText(
                        "Zeichnen beenden"
                );
                polygonSpeichernButton.setVisible(true);
            }
        });


        /* Aktion des Buttons „Polygon speichern“ */
        polygonSpeichernButton.setOnAction(e -> {

            /* Polygon speichern */
            polygonZeichner.polygonSpeichern();

            /* Zeichnen beenden */
            polygonZeichner.polygonBeenden();

            /* Button „Polygon zeichnen“ zurücksetzen */
            polygonButton.setText("Polygon zeichnen");

            /* Speichern-Button ausblenden */
            polygonSpeichernButton.setVisible(false);
        });


        /* Buttons zum Overlay hinzufügen */
        overlayGroup.getChildren().addAll(
                polygonButton,
                polygonSpeichernButton
        );


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
        //stage.setFullScreen(true);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
