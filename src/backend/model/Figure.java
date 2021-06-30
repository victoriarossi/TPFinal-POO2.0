package backend.model;

import javafx.scene.canvas.GraphicsContext;

import javafx.scene.paint.Color;

public abstract class Figure {
    public abstract void moveFigure (double diffX, double diffY);
    public abstract double getWidth();
    public abstract double getHeight();
    public abstract boolean figureBelongs(Point eventPoint);
    public abstract GraphicsContext setStrokeAndFill(GraphicsContext gc, Color fillColor, Color strokeColor);
}
