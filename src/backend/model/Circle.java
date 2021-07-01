package backend.model;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Circle extends Figure {
    protected final Point centerPoint;
    protected final double radius;
    private Color fillColor;
    private Color lineColor;

    public Circle(Point startPoint, Point endPoint) {
        this.centerPoint = startPoint;
        this.radius = Math.abs(endPoint.getX() - startPoint.getX());
    }

    public String toString() {
        return String.format("Círculo [Centro: %s, Radio: %.2f]", this.centerPoint, this.radius);
    }

    public Point getCenterPoint() {
        return this.centerPoint;
    }

    public double getRadius() {
        return this.radius;
    }

    public double getDiameter() {
        return this.radius * 2.0D;
    }

    public double getWidth() {
        return this.centerPoint.getX() - this.radius;
    }

    public double getHeight() {
        return this.centerPoint.getY() - this.radius;
    }

    public boolean figureBelongs(Point eventPoint) {
        return Math.sqrt(Math.pow(this.centerPoint.getX() - eventPoint.getX(), 2.0D) + Math.pow(this.centerPoint.getY() - eventPoint.getY(), 2.0D)) < this.radius;
    }

    public GraphicsContext setStrokeAndFill(GraphicsContext gc, Color fillColor, Color strokeColor) {
        gc.setFill(fillColor);
        gc.setStroke(strokeColor);
        gc.fillOval(this.getWidth(), this.getHeight(), this.getDiameter(), this.getDiameter());
        gc.strokeOval(this.getWidth(), this.getHeight(), this.getDiameter(), this.getDiameter());
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

    public void moveFigure(double diffX, double diffY) {
        this.centerPoint.movePoint(diffX, diffY);
    }

    public boolean figureBelongsIn(Rectangle rectangle) {
        Point topPoint = this.centerPoint;
        Point bottomPoint = this.centerPoint;
        Point leftPoint = this.centerPoint;
        Point rightPoint = this.centerPoint;
        rightPoint.movePoint(this.radius, 0.0D);
        leftPoint.movePoint(-this.radius, 0.0D);
        bottomPoint.movePoint(0.0D, -this.radius);
        topPoint.movePoint(0.0D, this.radius);
        return rectangle.figureBelongs(topPoint) && rectangle.figureBelongs(bottomPoint) && rectangle.figureBelongs(leftPoint) && rectangle.figureBelongs(rightPoint);
    }
}
