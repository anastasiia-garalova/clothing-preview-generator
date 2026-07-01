package de.kunstundstil.fenster.buttons;
/**
 * Enum Farbe definiert die möglichen Farben für Produkte.
 * Jede Konstante repräsentiert eine Farbe mit dem zugehörigen String-Namen.
 */
public enum Farbe {

    /** Gelbe Farbe */
    GELB("Gelb"),

    /** Blaue Farbe */
    BLAU("Blau"),

    /** Schwarze Farbe */
    SCHWARZ("Schwarz"),

    /** Weiße Farbe */
    WEISS("Weiss"),

    /** Grüne Farbe */
    GRUEN("Gruen"),

    /** Rosa Farbe */
    ROSA("Rosa");

    /** Der String-Name der Farbe */
    private final String farbe;


    /**
     * Konstruktor für die Enum-Konstanten.
     *
     * @param farbe Der String-Name der Farbe
     */
    Farbe(String farbe) {
        this.farbe = farbe;
    }

    /**
     * Gibt den Namen der Farbe als String zurück.
     *
     * @return String-Name der Farbe
     */
    public String getFarbe() {
        return farbe;
    }
}
