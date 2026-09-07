package de.kunstundstil.fenster.buttons;


import de.kunstundstil.produkte.ProductGenerator;
import javafx.scene.Group;

import java.util.concurrent.atomic.AtomicReference;

/**
 * Die Klasse ButtonFarbeVomTSchirt erweitert ButtonFarbe speziell für T-Shirt-Produkte.
 * Sie erbt alle Funktionalitäten von ButtonFarbe und erstellt Farbkreise,
 * die die Farbe des T-Shirts ändern können.
 */
public class ButtonFarbeVomTSchirt extends ButtonFarbe{

    /**
     * Konstruktor: Initialisiert die Farbkreise für T-Shirt-Produkte.
     *
     * @param backgroundGroupLinks Gruppe, auf der die Farbkreise angezeigt werden
     * @param hauptBody AtomicReference auf das aktuelle T-Shirt-Produkt
     */
    public ButtonFarbeVomTSchirt(Group backgroundGroupLinks, AtomicReference<ProductGenerator> hauptBody) {
        super(backgroundGroupLinks, hauptBody);
    }
}
