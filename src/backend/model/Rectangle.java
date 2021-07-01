package backend.model;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Rectangle extends Figure {

    private final Point topLeft, bottomRight;
    private Color fillColor, lineColor;

    public Rectangle(Point topLeft, Point bottomRight) {
        this.topLeft = topLeft;
        this.bottomRight = bottomRight;
    }

    public Point getTopLeft() {
        return topLeft;
    }

    public Point getBottomRight() {
        return bottomRight;
    }

    public Point getCenter(){
        return new Point(getWidth() / 2 , getHeight() / 2);
    }

    @Override
    public double getWidth(){
        return Math.abs(topLeft.getX() - bottomRight.getX());
    }

    @Override
    public double getHeight(){
        return Math.abs(topLeft.getY() - bottomRight.getY());
    }

    @Override
    public boolean figureBelongs(Point eventPoint) {
        return eventPoint.getX() > topLeft.getX() && eventPoint.getX() < bottomRight.getX() &&
                eventPoint.getY() > topLeft.getY() && eventPoint.getY() < bottomRight.getY();
    }

    @Override
    public GraphicsContext setStrokeAndFill(GraphicsContext gc, Color fillColor, Color strokeColor) {
        gc.setFill(fillColor);
        gc.setStroke(strokeColor);
        gc.fillRect(topLeft.getX(), topLeft.getY(), getWidth(),getHeight());
        gc.strokeRect(topLeft.getX(), topLeft.getY(), getWidth(),getHeight());
        return gc;
    }

    @Override
    public void setStrokeColor(Color lineColor) {
        this.lineColor = lineColor;
    }

    @Override
    public void setFillColor(Color fillColor) {
        this.fillColor = fillColor;
    }

    @Override
    public Color getFill() {
        return fillColor;
    }

    @Override
    public Color getLine() {
        return lineColor;
    }


    @Override
    public String toString() {
        return String.format("Rectángulo [ %s , %s ]", topLeft, bottomRight);
    }

    @Override
    public void moveFigure(double diffX, double diffY) {
        topLeft.movePoint(diffX,diffY);
        bottomRight.movePoint(diffX,diffY);
    }

    @Override
    public boolean figureBelongsIn(Rectangle rectangle){
        return rectangle.figureBelongs(topLeft) && rectangle.figureBelongs(bottomRight);
    }

}
