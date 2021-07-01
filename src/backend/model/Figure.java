package backend.model;

import backend.model.Point;
import backend.model.Rectangle;
import javafx.scene.canvas.GraphicsContext;

import javafx.scene.paint.Color;



public abstract class Figure {

    public abstract void moveFigure(double diffX, double diffY);

    public abstract double getWidth();

    public abstract double getHeight();

    public abstract boolean figureBelongs(Point eventPoint);

    public abstract GraphicsContext setStrokeAndFill(GraphicsContext gc, Color fillColor, Color strokeColor, double thick);

    public abstract boolean figureBelongsIn(Rectangle rectangle);

    private Color fillColor, lineColor;

    private double thick;


    public void setStrokeColor(Color lineColor) {
        this.lineColor = lineColor;
    }

    public void setFillColor(Color fillColor) {
        this.fillColor = fillColor;
    }

    public Color getFill() {
        return fillColor;
    }

    public Color getLine() {
        return lineColor;
    }

    public void setThickness(double thick) {
        this.thick = thick;
    }

    public double getThickness() {
        return thick;
    }
}