package de.kunstundstil.test;

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
 * Das Projekt demonstriert ein weißen TßSchirt in einem Fenster
 * Unser Stage wird auch icon haben
 * Realisiere ich Bildausrichtung
 * Ich möchte das Bild hinter der Rechtange nicht gesehen wird. Es kann mir .setClip ereichen helfen
 *
 * 🇷🇺 AtomicReference<Body> hauptBody используется для хранения ссылки на текущий объект Body.
 * Это позволяет динамически менять продукт (например, футболку на пуловер) во время работы программы,
 *и при этом все остальные классы (например, кнопки выбора цвета) всегда обращаются к актуальному Body.
 * Таким образом, мы избегаем проблем со "старыми" ссылками и обеспечиваем корректную работу интерфейса.
 *
 * 🇩🇪 Die Variable AtomicReference<Body> hauptBody dient dazu, eine Referenz auf das aktuelle Body-Objekt zu speichern.
 * Dadurch kann das Produkt (z. B. T-S
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 * hirt oder Pullover) während der Laufzeit dynamisch gewechselt werden,
 * und andere Klassen (z. B. Farb-Buttons) greifen immer auf das aktuelle Body-Objekt zu.
 * So vermeiden wir Probleme mit veralteten Referenzen und stellen sicher, dass die Benutzeroberfläche korrekt funktioniert.
 *
 * Die Variable AtomicReference<ProductGenerator> hauptBody speichert eine Referenz auf das aktuell angezeigte Produkt (z.B. T-Shirt oder Pullover).
 *  Durch die Verwendung von AtomicReference kann die Referenz während der Laufzeit sicher ausgetauscht werden,
 *  sodass alle anderen Klassen und Bedienelemente (wie Farb-Buttons oder Größenänderungen) automatisch auf das aktuelle Produkt zugreifen,
 *  ohne dass alte Referenzen ungültig werden. Dies gewährleistet eine konsistente und dynamische Benutzeroberfläche.
 */
/**
 * Hauptklasse der Anwendung, die die Anzeige von Produkten (T-Shirt, Pullover) demonstriert.
 * <p>
 * Funktionalitäten:
 * <ul>
 *     <li>Erstellt das Hauptfenster mit fixierter Größe und Hintergrundfarbe</li>
 *     <li>Zeigt Header-Text an</li>
 *     <li>Lädt und zeigt Produkte (T-Shirt, Pullover) an</li>
 *     <li>Ermöglicht das Verschieben, Skalieren und Ändern der Farben des Produkts</li>
 *     <li>Unterstützt das Hochladen eines neuen Bildes auf das Produkt</li>
 *     <li>Speichert das aktuell angezeigte Produkt als PNG-Datei</li>
 *     <li>Optional: Polygon-Zeichenfunktion auf der Oberfläche</li>
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


    //private final List<Double> points = new ArrayList<>();
    private Polygon polygon;
    private boolean drawing = true;

    private List<Double> points = new ArrayList<>();
    private Polygon currentPolygon;
    private Pane canvas;

    private Group farbenCreisePanel;

    @Override
    public void start(Stage primaryStage) throws IOException {

        /* Haupt Gruppe */
        Group root = new Group ();
        Scene scene = new Scene(root, 800, 600, Color.LIGHTBLUE);

        Image icon = new Image("file:resources/image/icon.png");

        /* Header*/
        Group headerGroup = new Group();

        Header header = new Header();
        header.headerErstellen("WHOOOA!", headerGroup);

        /* Linke Seite */
        Group backgroundGroup = new Group();

        /* Bilder Auf dem produkt */
        Group bildAufDemProduktGroup = new Group();

        /* Alle andere Objekte auf dem Fenster */
        Group overlayGroup = new Group();

        /* Objekt des BildAufDemProdukt  */
        BildAufDemProdukt bildAufDemProduktObjekt = new BildAufDemProdukt();
        ImageView bildAufDemProduktView = bildAufDemProduktObjekt.erstellen();

        /* Pruefen welches Bild wir haben: horizontal oder vertikal, es hilft uns richtig das Bild "Clip" tun*/
        String position;
        if(bildAufDemProduktView.getBoundsInParent().getWidth() == 140){
            position = "horizontal";
        }else{
            position = "vertikal";
        }

        // Verwenden AtomicReference um unser Produkt aendern zu koennen
        AtomicReference<ProductGenerator> hauptBody = new AtomicReference<>(new ProductGenerator(backgroundGroup, overlayGroup,
                bildAufDemProduktGroup, "Pullower", bildAufDemProduktView, farbenCreisePanel));

        // Botton mit Farbe der Produkte
        new ButtonFarbe(overlayGroup, hauptBody);


        // Neues Bild  wird herunterladen
        new BildHerunterladung(primaryStage, overlayGroup, bildAufDemProduktView, hauptBody);

        // Schwarzer  Rechteck auf dem Produkt
        RechteckAufDemProdukt rechteckAufDemProdukt = new RechteckAufDemProdukt();

        // Вas Bild verschieben wird
        AtomicInteger fitZahlFuerVergleichen = new AtomicInteger(140);
        bildAufDemProduktObjekt.bildLaufenWird(overlayGroup, bildAufDemProduktGroup, bildAufDemProduktView, position,
                hauptBody.get().getRectangle(), fitZahlFuerVergleichen, rechteckAufDemProdukt);

        // Button "Verkleinen" und "VeRgrösern"
        ButtonVergroesernUndVerkleinen buttonAufDemFenster = new ButtonVergroesernUndVerkleinen();
        buttonAufDemFenster.buttonsVergroesernUndVerkleinenErstellen(overlayGroup, bildAufDemProduktView, fitZahlFuerVergleichen);

        // Produkt auswaehlen, der interessiert uns
        new ButtonAuswaehlDesProdukts(backgroundGroup,  bildAufDemProduktGroup, overlayGroup, hauptBody,
                bildAufDemProduktObjekt, bildAufDemProduktView,  position,  fitZahlFuerVergleichen, rechteckAufDemProdukt, farbenCreisePanel);


        Group snapshotGroup = new Group();
        // Button, um generiertes Bild speichern
        BildSpeicherung bildSpeicherung = new BildSpeicherung(root, snapshotGroup,hauptBody);


        snapshotGroup.getChildren().addAll(backgroundGroup, bildAufDemProduktGroup);

        root.getChildren().addAll(headerGroup, snapshotGroup, overlayGroup);










//        canvas = new Pane();
//        canvas.setStyle("-fx-background-color: lightblue;");
//
//
//        root.setOnMouseClicked(e -> {
//            // ЛКМ — добавляем точку
//            if (e.getButton() == MouseButton.PRIMARY) {
//                points.add(e.getX());
//                points.add(e.getY());
//
//                Circle dot = new Circle(e.getX(), e.getY(), 3, Color.DARKRED);
//                canvas.getChildren().add(dot);
//
//                if (currentPolygon == null) {
//                    currentPolygon = new Polygon();
//                    currentPolygon.setStroke(Color.BLACK);
//                    currentPolygon.setStrokeWidth(2);
//                    currentPolygon.setFill(Color.color(Math.random(), Math.random(), Math.random(), 0.5));
//                    canvas.getChildren().add(currentPolygon);
//                }
//
//                currentPolygon.getPoints().setAll(points);
//
//                // ПКМ — если двойной клик, завершаем фигуру
//            } else if (e.getButton() == MouseButton.SECONDARY && e.getClickCount() == 2) {
//                if (currentPolygon != null && points.size() > 4) {
//                    savePolygon(currentPolygon);
//                    System.out.println("✅ Полигон сохранён как SVG");
//                }
//                currentPolygon = null;
//                points.clear();
//            }
//        });




//        Pane rootPane = new Pane();
//        rootPane.setPrefSize(800, 600);
//
//        polygon = new Polygon();
//        polygon.setFill(Color.color(0, 0.5, 1, 0.3)); // прозрачный синий
//        polygon.setStroke(Color.DARKBLUE);
//        polygon.setStrokeWidth(2);
//
//        root.getChildren().add(polygon);
//
//
//        root.setOnMouseClicked(e -> handleMouseClick(e, rootPane));
        primaryStage.getIcons().add(icon);
        primaryStage.setTitle("Kunst&Stil");
        /* Grosse von unser Fenster wird fix */
        primaryStage.setResizable(false);
        //stage.setFullScreen(true);
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    //**************************************

    private void handleMouseClick(MouseEvent e, Pane root) {
        if (!drawing) return;

        // ЛКМ — добавляем точку
        if (e.getButton() == MouseButton.PRIMARY) {
            double x = e.getX();
            double y = e.getY();
            points.add(x);
            points.add(y);

            // Рисуем маленький кружок для каждой точки
            Circle point = new Circle(x, y, 3, Color.RED);
            root.getChildren().add(point);

            polygon.getPoints().setAll(points);
        }

        // ПКМ (дважды) — завершение
        if (e.getButton() == MouseButton.SECONDARY && e.getClickCount() == 2) {
            drawing = false;
            System.out.println("✅ Полигон завершён.");
            savePolygon(points);
        }
    }
    /** Сохраняем координаты полигона в файл */
    private void savePolygon(List<Double> pts) {
        try (FileWriter writer = new FileWriter("polygon_pullower.txt")) {
            for (int i = 0; i < pts.size(); i += 2) {
                writer.write(pts.get(i) + "," + pts.get(i + 1) + "\n");
            }
            System.out.println("💾 Сохранено в polygon_points.txt");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }



}
