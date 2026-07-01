package de.kunstundstil.fenster.buttons;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Die Klasse ButtonVergroesernUndVerkleinen erstellt zwei Buttons "+" und "-",
 * um die Größe eines Bildes auf dem Produkt dynamisch zu ändern.
 * Der "+"-Button vergrößert das Bild, der "-"-Button verkleinert es.
 */
public class ButtonVergroesernUndVerkleinen {

    /**
     * Erstellt die Buttons für das Vergrößern und Verkleinern des Produktbildes
     * und fügt sie der angegebenen Gruppe hinzu.
     *
     * @param root Gruppe, auf der die Buttons angezeigt werden
     * @param bildAufDemProduktView Das ImageView, dessen Größe geändert wird
     * @param fitZahlFuerVergleichen AtomicInteger, der den aktuellen Grenzwert der Bildgröße speichert
     */
    public void buttonsVergroesernUndVerkleinenErstellen(Group root, ImageView bildAufDemProduktView, AtomicInteger fitZahlFuerVergleichen){
        Button vergroessen = new Button("+");
        Button verkleinen = new Button("-");

        vergroessen.setLayoutX(210);
        vergroessen.setLayoutY(500);
        verkleinen.setLayoutX(260);
        verkleinen.setLayoutY(500);

        Font font = Font.font("Courier New", FontWeight.BOLD, 20);
        vergroessen.setFont(font);
        verkleinen.setFont(font);

        // Action für Vergrößern
        vergroessen.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                if(bildAufDemProduktView.getFitWidth() <= 150){

                    fitZahlFuerVergleichen.addAndGet(10);

                    double bildWidth = bildAufDemProduktView.getFitWidth() + 10;
                    double bildSetX = bildAufDemProduktView.getX() - 5;

                    bildAufDemProduktView.setFitWidth(bildWidth);
                    bildAufDemProduktView.setPreserveRatio(true);

                    bildAufDemProduktView.setX(bildSetX);

                }

            }
        });

        // Action für Verkleinern
        verkleinen.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                if(bildAufDemProduktView.getFitWidth() >= 50){
                    fitZahlFuerVergleichen.addAndGet(-10);

                    double bildWidth = bildAufDemProduktView.getFitWidth() - 10;
                    double bildSetX = bildAufDemProduktView.getX() + 5;

                    bildAufDemProduktView.setFitWidth(bildWidth);
                    bildAufDemProduktView.setPreserveRatio(true);

                    bildAufDemProduktView.setX(bildSetX);

                }
            }
        });

        // Buttons Vergroessen und Verkleinen in der Gruppe root auf der Fenster
        root.getChildren().add(vergroessen);
        root.getChildren().add(verkleinen);
    }


}
