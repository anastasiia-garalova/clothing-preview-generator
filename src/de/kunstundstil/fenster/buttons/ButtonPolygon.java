package de.kunstundstil.fenster.buttons;

import de.kunstundstil.polygons.PolygonZeichner;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;

public class ButtonPolygon {

    public ButtonPolygon(Group overlayGroup, Pane zeichenFlaeche) {

        PolygonZeichner polygonZeichner =
                new PolygonZeichner(zeichenFlaeche);

        polygonZeichner.zeichnenBeenden();

        Button polygonSpeichernButton =
                new Button("Polygon speichern");

        polygonSpeichernButton.setLayoutX(167);
        polygonSpeichernButton.setLayoutY(550);

        stilFuerButton(polygonSpeichernButton);
        polygonSpeichernButton.setVisible(false);

        Button polygonButton =
                new Button("Polygon zeichnen");

        polygonButton.setLayoutX(550);
        polygonButton.setLayoutY(300);

        stilFuerButton(polygonButton);

        polygonButton.setOnAction(e -> {

            if (polygonZeichner.istZeichenmodusAktiv()) {

                polygonZeichner.polygonBeenden();
                polygonButton.setText("Polygon zeichnen");
                polygonSpeichernButton.setVisible(false);

            } else {

                polygonZeichner.zeichnenStarten();
                polygonButton.setText("Zeichnen beenden");
                polygonSpeichernButton.setVisible(true);
            }
        });

        polygonSpeichernButton.setOnAction(e -> {

            polygonZeichner.polygonSpeichern();
            polygonZeichner.polygonBeenden();

            polygonButton.setText("Polygon zeichnen");
            polygonSpeichernButton.setVisible(false);
        });

        overlayGroup.getChildren().addAll(
                polygonButton,
                polygonSpeichernButton
        );
    }

    private void stilFuerButton(Button button) {
        button.setStyle(
                "-fx-background-color: #F7F4ED;" +
                        "-fx-text-fill: #2C2520;" +
                        "-fx-font-family: 'Arial';" +
                        "-fx-font-size: 16px;" +
                        "-fx-letter-spacing: 1px;" +
                        "-fx-background-radius: 8;" +
                        "-fx-padding: 8 18 8 18;"
        );
    }
}
