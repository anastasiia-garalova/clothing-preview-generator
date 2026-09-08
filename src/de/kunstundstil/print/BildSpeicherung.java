package de.kunstundstil.print;

import de.kunstundstil.produkte.ProductGenerator;
import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.HBox;

import javax.imageio.ImageIO;
import java.io.File;
import java.time.LocalDate;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

/*
 * Klasse zum Speichern des fertigen Produktbildes als PNG-Datei.
 *
 * <p>Das Bild wird nach Produktname und Datum gespeichert.
 * Für jedes Produkt wird eine eigene fortlaufende ID verwendet.</p>
 */


public class BildSpeicherung {

    /** Fortlaufende ID für TShirt-Bilder */
    private AtomicInteger idDesFileTSchirt = new AtomicInteger(1);

    /** Fortlaufende ID für Pullower-Bilder */
    private AtomicInteger idDesFilePullower = new AtomicInteger(1);

    /**
     * Konstruktor, der den Speicher-Button erstellt und die Snapshot-Funktionalität bindet.
     *
     * @param root Die Root-Gruppe, in der der Button angezeigt wird
     * @param snapshotGroup Die Gruppe, die als Bild gespeichert werden soll
     * @param hauptBody Referenz auf das aktuelle ProductGenerator-Objekt
     */
    public BildSpeicherung(Group root, Group snapshotGroup, AtomicReference<ProductGenerator>hauptBody) {

        Button btnSavePng = new Button("Als PNG speichern");
        HBox controls = new HBox(10, btnSavePng);
        controls.setLayoutX(550);
        controls.setLayoutY(350);

        btnSavePng.setStyle(
                "-fx-background-color: #A85D45;" +
                        "-fx-text-fill: #FFFFFF;" +
                        "-fx-font-family: 'Arial';" +
                        "-fx-font-size: 16px;" +
                        "-fx-letter-spacing: 1px;" +
                        "-fx-background-radius: 8;" +
                        "-fx-padding: 12 22 12 22;"
        );

        root.getChildren().add(controls);

        btnSavePng.setOnAction(e -> {
            Platform.runLater(() -> {
                try {

                    int id = hauptBody.get().getProdukt().getName().equals("TSchirt")
                            ? idDesFileTSchirt.getAndIncrement()
                            : idDesFilePullower.getAndIncrement();

                    LocalDate dataHeute = LocalDate.now();

                    File dir = new File("resources/image/bestellungen/"
                            + hauptBody.get().getProdukt().getName() + "/"
                            + dataHeute + "/");
                    if (!dir.exists()) dir.mkdirs(); // создаем все промежуточные папки

                    File file = new File(dir, hauptBody.get().getProdukt().getName() + "_"
                            + id +".png");

                    snapshotGroup.setScaleX(3.0);
                    snapshotGroup.setScaleY(3.0);

                    WritableImage snapshot = snapshotGroup.snapshot(null, null);

                    snapshotGroup.setScaleX(1.0);
                    snapshotGroup.setScaleY(1.0);

                    ImageIO.write(SwingFXUtils.fromFXImage(snapshot, null), "png", file);

                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            });
        });
    }
}
