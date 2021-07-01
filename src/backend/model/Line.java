package backend.model;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Line extends Rectangle {
    private Color lineColor;

    public Line(Point firstPoint, Point secondPoint) {
        super(firstPoint, secondPoint);
    }

    public double getHeight() {
        return 0.0D;
    }

    public String toString() {
        return String.format("Línea [ %s, %s ]", this.getTopLeft(), this.getBottomRight());
    }

    public GraphicsContext setStrokeAndFill(GraphicsContext gc, Color fillColor, Color strokeColor) {
        gc.setStroke(strokeColor);
        gc.strokeLine(this.getTopLeft().getX(), this.getTopLeft().getY(), this.getBottomRight().getX(), this.getBottomRight().getY());
        return gc;
    }

    public void setStrokeColor(Color lineColor) {
        this.lineColor = lineColor;
    }

    public Color getLine() {
        return this.lineColor;
    }
}
