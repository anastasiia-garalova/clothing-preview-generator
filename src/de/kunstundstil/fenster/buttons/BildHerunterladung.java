package de.kunstundstil.fenster.buttons;

import de.kunstundstil.produkte.ProductGenerator;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Die Klasse BildHerunterladung stellt einen Button bereit, mit dem der Benutzer ein neues Bild
 * auswählen und auf das Produkt laden kann.
 * Das ausgewählte Bild wird dann im ImageView des Produkts angezeigt.
 */
public class BildHerunterladung {

    /** Button, der den Bild-Upload auslöst. */
    private final Button buttonBildUnterladen;


    /**
     * Konstruktor: Erstellt den Button zum Herunterladen eines neuen Bildes
     * und bindet die Aktion, das ausgewählte Bild in das ImageView zu laden.
     *
     * @param primaryStage Die Hauptbühne (Stage), die für den FileChooser benötigt wird
     * @param overlayGroup Gruppe, in die der Button eingefügt wird
     * @param bildAufDemProduktView ImageView, in das das neue Bild geladen wird
     * @param hauptBody AtomicReference auf das aktuelle Produkt (kann für Erweiterungen verwendet werden)
     */
    public BildHerunterladung(Stage primaryStage, Group overlayGroup, ImageView bildAufDemProduktView, AtomicReference<ProductGenerator> hauptBody){

        buttonBildUnterladen = new Button("Neues Bild auswählen");
        buttonBildUnterladen.setLayoutX(550);
        buttonBildUnterladen.setLayoutY(250);

        buttonBildUnterladen.setStyle(
                "-fx-background-color: #F7F4ED;" +
                        "-fx-text-fill: #2C2520;" +
                        "-fx-font-family: 'Arial';" +
                        "-fx-font-size: 16px;" +
                        "-fx-letter-spacing: 1px;" +
                        "-fx-background-radius: 8;" +
                        "-fx-padding: 8 16 8 16;"
        );

        /* Wenn der Button „Bild herunterladen“ geklickt wird */
        buttonBildUnterladen.setOnAction(event -> {

            /* Datei auswählen */
            FileChooser fileChooser = new FileChooser();
            fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter(
                            "Image Files", "*.png", "*.jpg", "*.jpeg", "*.pdf"
                    )
            );

            File ausgewaehlteFile = fileChooser.showOpenDialog(primaryStage);
            if(ausgewaehlteFile != null) {

                /* Ausgewählte Bilddatei laden */
                Image bildAufDemProdukt = new Image(ausgewaehlteFile.toURI().toString());

                /* Bild auf dem Produkt anzeigen */
                bildAufDemProduktView.setImage(bildAufDemProdukt);

            } else {
                /* Keine Datei ausgewählt */
                System.out.println("Keine Datei wurde ausgewählt");
            }
        });

        /* Button zur Overlay-Gruppe hinzufügen */
        overlayGroup.getChildren().add(buttonBildUnterladen);
    }
}
