package de.kunstundstil.produkte;

import javafx.scene.image.Image;


/**
 * Die Klasse TShirt erweitert die Klasse Produkt.
 * Sie repräsentiert ein T-Shirt-Produkt mit spezifischem Namen und Funktionen.
 * Ermöglicht das Setzen der Farbe, indem das passende Bild geladen wird.
 */
public class TShirt extends Produkt{

    /** Name des T-Shirts */
    private String name;

    /**
     * Konstruktor mit individuellem Namen.
     *
     * @param name Name des T-Shirts
     */
    public TShirt(String name) {
        this.name = name;
    }

    /**
     * Standard-Konstruktor.
     * Setzt den Namen auf "TShirt".
     */
    public TShirt() {
        this.name = "TShirt";
    }

    /**
     * Setzt die Farbe des T-Shirts, indem das entsprechende Bild geladen wird.
     *
     * @param farbe Name der Farbe
     */
    @Override
    public void setFarbe(String farbe) {

        String pfad = "file:resources/image/tshirt/TShirt_" + farbe + ".png";
        Image image = new Image(pfad, 400, 400, true, true);
        super.imageDesProduktsView.setImage(image);
    }

    /**
     * Gibt den Namen des T-Shirts zurück.
     *
     * @return Name des Produkts
     */
    @Override
    public String getName() {
        return name;
    }
}
