package backend.model;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Ellipse extends Rectangle {
    private Color fillColor;
    private Color lineColor;

    public Ellipse(Point topLeft, Point bottomRight) {
        super(topLeft, bottomRight);
    }

    public boolean figureBelongs(Point eventPoint) {
        return Math.pow(eventPoint.getX() - this.getCenter().getX(), 2.0D) / Math.pow(this.getWidth(), 2.0D) + Math.pow(eventPoint.getY() - this.getCenter().getY(), 2.0D) / Math.pow(this.getHeight(), 2.0D) <= 1.0D;
    }

    public double getAxisMay() {
        return this.getHeight() > this.getWidth() ? this.getHeight() : this.getWidth();
    }

    public double getAxisMen() {
        return this.getHeight() > this.getWidth() ? this.getWidth() : this.getHeight();
    }

    public String toString() {
        return String.format("Elipse [Centro: %s, Eje Mayor: %.2f, Eje Menor: %.2f]", this.getCenter(), this.getAxisMay(), this.getAxisMen());
    }

    public void moveFigure(double diffX, double diffY) {
        this.getCenter().movePoint(diffX, diffY);
    }

    public GraphicsContext setStrokeAndFill(GraphicsContext gc, Color fillColor, Color strokeColor) {
        gc.setFill(fillColor);
        gc.setStroke(strokeColor);
        gc.fillOval(this.getWidth(), this.getHeight(), this.getAxisMay(), this.getAxisMen());
        gc.strokeOval(this.getWidth(), this.getHeight(), this.getAxisMay(), this.getAxisMen());
        return gc;
    }

    public void setFillColor(Color fillColor) {
        this.fillColor = fillColor;
    }

    public void setStrokeColor(Color lineColor) {
        this.lineColor = lineColor;
    }

    public Color getFill() {
        return this.fillColor;
    }

    public Color getLine() {
        return this.lineColor;
    }
}
