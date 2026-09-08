package de.kunstundstil.pojo;

public class RechteckAufDemProdukt {
    private final int rectangelX;
    private final int rectangelY;
    private final int rectangleWidth;
    private int rectangelHeight;

    public RechteckAufDemProdukt() {
        this.rectangelX = 170;
        this.rectangelY = 170;
        this.rectangleWidth = 160;
        this.rectangelHeight = 300;
    }

    public RechteckAufDemProdukt(int rectangelX, int rectangelY, int rectangleWidth, int rectangelHeight) {
        this.rectangelX = rectangelX;
        this.rectangelY = rectangelY;
        this.rectangleWidth = rectangleWidth;
        this.rectangelHeight = rectangelHeight;
    }

    public int getRectangelX() {
        return rectangelX;
    }

    public int getRectangelY() {
        return rectangelY;
    }

    public int getRectangleWidth() {
        return rectangleWidth;
    }


    public int getRectangelHeight() {
        return rectangelHeight;
    }

    public void setRectangelHeight(int rectangelHeight) {
        this.rectangelHeight = rectangelHeight;
    }
}
