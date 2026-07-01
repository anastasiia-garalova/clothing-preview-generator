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
    private Button buttonBildUnterladen;


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

        buttonBildUnterladen = new Button("Ein neues Bild unterzuladen");

        buttonBildUnterladen.setLayoutX(570);
        buttonBildUnterladen.setLayoutY(250);

        //Wenn Button "Bild herunterladen" geklickt war
        buttonBildUnterladen.setOnAction(event -> {
            // File auswaehlen
            FileChooser fileChooser = new FileChooser();
            fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg", "*.pdf"));

            File ausgewaehlteFile = fileChooser.showOpenDialog(primaryStage);
            if(ausgewaehlteFile != null) {
                Image bildAufDemProdukt = new Image(ausgewaehlteFile.toURI().toString());

                bildAufDemProduktView.setImage(bildAufDemProdukt);

            } else {
                System.out.println("File war icht ausgewaehlt");
            }
        });

        // Button zur Overlay-Gruppe hinzufügen
        overlayGroup.getChildren().add(buttonBildUnterladen);

    }

}
