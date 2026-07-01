package de.kunstundstil.pojo;

public class RechteckAufDemProdukt {
    private int rectangelX;
    private int rectangelY;
    private int rectangleWidth;
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

    public void setRectangelX(int rectangelX) {
        this.rectangelX = rectangelX;
    }

    public int getRectangelY() {
        return rectangelY;
    }

    public void setRectangelY(int rectangelY) {
        this.rectangelY = rectangelY;
    }

    public int getRectangleWidth() {
        return rectangleWidth;
    }

    public void setRectangleWidth(int rectangleWidth) {
        this.rectangleWidth = rectangleWidth;
    }

    public int getRectangelHeight() {
        return rectangelHeight;
    }

    public void setRectangelHeight(int rectangelHeight) {
        this.rectangelHeight = rectangelHeight;
    }
}
