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

/**
 * Diese Klasse ermöglicht das Speichern von Produktbildern als PNG-Dateien.
 * <p>
 * Funktionalitäten:
 * <ul>
 *     <li>Erstellt einen Button zum Speichern des aktuell ausgewählten Produkts</li>
 *     <li>Speichert die Bilder in einem Ordner "resources/image/bestellungen/&lt;Produktname&gt;/&lt;Datum&gt;/"</li>
 *     <li>Vergibt automatisch eine fortlaufende ID für jedes gespeicherte Bild</li>
 *     <li>Skaliert die Snapshot-Gruppe vorübergehend, um eine höhere Bildauflösung zu erhalten</li>
 * </ul>
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
        /*------------------JPT------------------*/

        // Кнопки управления
        Button btnSavePng = new Button("💾 Schpeichern wie PNG");
        HBox controls = new HBox(10, btnSavePng);
        controls.setLayoutX(520);
        controls.setLayoutY(500);
        root.getChildren().add(controls);

        btnSavePng.setOnAction(e -> {
            Platform.runLater(() -> {
                try {
                    // берём текущее значение и увеличиваем на 1
                    int id = hauptBody.get().getProdukt().getName().equals("TSchirt")
                            ? idDesFileTSchirt.getAndIncrement()
                            : idDesFilePullower.getAndIncrement();

                    LocalDate dataHeute = LocalDate.now();

                    // Папка для сохранения
                    File dir = new File("resources/image/bestellungen/"
                            + hauptBody.get().getProdukt().getName() + "/"
                            + dataHeute + "/");
                    if (!dir.exists()) dir.mkdirs(); // создаем все промежуточные папки

                    // Имя файла
                    File file = new File(dir, hauptBody.get().getProdukt().getName() + "_"
                            + id +".png");

                    // Временно увеличиваем группу в 3 раза
                    snapshotGroup.setScaleX(3.0);
                    snapshotGroup.setScaleY(3.0);

                    // Делаем snapshot группы (уже увеличенной)
                    WritableImage snapshot = snapshotGroup.snapshot(null, null);

                    // Возвращаем нормальный масштаб
                    snapshotGroup.setScaleX(1.0);
                    snapshotGroup.setScaleY(1.0);

                    // Сохраняем PNG
                    ImageIO.write(SwingFXUtils.fromFXImage(snapshot, null), "png", file);

                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            });
        });

        /*-----------end JPT-----------*/
    }
}
