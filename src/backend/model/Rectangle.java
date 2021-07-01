package backend.model;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Rectangle extends Figure {
    private final Point topLeft;
    private final Point bottomRight;
    private Color fillColor;
    private Color lineColor;

    public Rectangle(Point topLeft, Point bottomRight) {
        this.topLeft = topLeft;
        this.bottomRight = bottomRight;
    }

    public Point getTopLeft() {
        return this.topLeft;
    }

    public Point getBottomRight() {
        return this.bottomRight;
    }

    public Point getCenter() {
        return new Point(this.getWidth() / 2.0D, this.getHeight() / 2.0D);
    }

    public double getWidth() {
        return Math.abs(this.topLeft.getX() - this.bottomRight.getX());
    }

    public double getHeight() {
        return Math.abs(this.topLeft.getY() - this.bottomRight.getY());
    }

    public boolean figureBelongs(Point eventPoint) {
        return eventPoint.getX() > this.topLeft.getX() && eventPoint.getX() < this.bottomRight.getX() && eventPoint.getY() > this.topLeft.getY() && eventPoint.getY() < this.bottomRight.getY();
    }

    public GraphicsContext setStrokeAndFill(GraphicsContext gc, Color fillColor, Color strokeColor) {
        gc.setFill(fillColor);
        gc.setStroke(strokeColor);
        gc.fillRect(this.topLeft.getX(), this.topLeft.getY(), this.getWidth(), this.getHeight());
        gc.strokeRect(this.topLeft.getX(), this.topLeft.getY(), this.getWidth(), this.getHeight());
        return gc;
    }

    public void setStrokeColor(Color lineColor) {
        this.lineColor = lineColor;
    }

    public void setFillColor(Color fillColor) {
        this.fillColor = fillColor;
    }

    public Color getFill() {
        return this.fillColor;
    }

    public Color getLine() {
        return this.lineColor;
    }

    public String toString() {
        return String.format("Rectángulo [ %s , %s ]", this.topLeft, this.bottomRight);
    }

    public void moveFigure(double diffX, double diffY) {
        this.topLeft.movePoint(diffX, diffY);
        this.bottomRight.movePoint(diffX, diffY);
    }

    public boolean figureBelongsIn(Rectangle rectangle) {
        return rectangle.figureBelongs(this.topLeft) && rectangle.figureBelongs(this.bottomRight);
    }
}
