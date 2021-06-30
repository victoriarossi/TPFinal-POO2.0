package backend.model;

import javafx.scene.canvas.GraphicsContext;

public class Circle extends Figure {

    protected final Point centerPoint;
    protected final double radius;

    public Circle(Point startPoint, Point endPoint) {
        this.centerPoint = startPoint;
        this.radius = Math.abs(endPoint.getX() - startPoint.getX());
    }

    @Override
    public String toString() {
        return String.format("Círculo [Centro: %s, Radio: %.2f]", centerPoint, radius);
    }

    public Point getCenterPoint() {
        return centerPoint;
    }

    public double getRadius() {
        return radius;
    }

    public double getDiameter(){return radius*2;}

    @Override
    public double getWidth(){
        return centerPoint.getX() - radius;
    }

    @Override
    public double getHeight(){
        return centerPoint.getY() - radius;
    }

    @Override
    public boolean figureBelongs(Point eventPoint) {
        return Math.sqrt(Math.pow(centerPoint.getX() - eventPoint.getX(), 2) +
                Math.pow(centerPoint.getY() - eventPoint.getY(), 2)) < radius;
    }

    @Override
    public GraphicsContext setStrokeAndFill(GraphicsContext gc) {
        gc.fillOval(getWidth(),getHeight(),getDiameter(),getDiameter());
        gc.strokeOval(getWidth(),getHeight(),getDiameter(),getDiameter());
        return gc;
    }

    @Override
    public void moveFigure(double diffX, double diffY) {
        centerPoint.movePoint(diffX,diffY);
    }
}
