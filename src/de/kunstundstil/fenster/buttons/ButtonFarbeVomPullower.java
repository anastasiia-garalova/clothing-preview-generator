package de.kunstundstil.fenster.buttons;

import de.kunstundstil.produkte.ProductGenerator;
import javafx.scene.Group;

import java.util.concurrent.atomic.AtomicReference;

/**
 * Die Klasse ButtonFarbeVomPullower erweitert ButtonFarbe speziell für Pullower-Produkte.
 * Sie erbt alle Funktionalitäten von ButtonFarbe und erstellt Farbkreise,
 * die die Farbe des Pullowers ändern können.
 */
public class ButtonFarbeVomPullower extends ButtonFarbe{

    /**
     * Konstruktor: Initialisiert die Farbkreise für Pullower-Produkte.
     *
     * @param backgroundGroupLinks Gruppe, auf der die Farbkreise angezeigt werden
     * @param hauptBody AtomicReference auf das aktuelle Pullower-Produkt
     */
    public ButtonFarbeVomPullower(Group backgroundGroupLinks, AtomicReference<ProductGenerator> hauptBody) {
        super(backgroundGroupLinks, hauptBody);
    }
}
