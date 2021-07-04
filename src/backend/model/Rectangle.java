package backend.model;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Rectangle extends Figure {

    private final Point topLeft, bottomRight;

    public Rectangle(Point topLeft, Point bottomRight) {
        this.topLeft = topLeft;
        this.bottomRight = setBottomRight(bottomRight);
    }

    public Point getTopLeft() {
        return topLeft;
    }

    protected Point setBottomRight(Point bottomRight) {
        return bottomRight;
    }

    public Point getBottomRight(){return bottomRight;}

    //Le doy un ancho
    public double getWidth(){
        return Math.abs(topLeft.getX() - bottomRight.getX());
    }

    //Le doy una altura
    public double getHeight(){
        return Math.abs(topLeft.getY() - bottomRight.getY());
    }


    @Override
    public GraphicsContext setStrokeAndFill(GraphicsContext gc, Color fillColor, Color strokeColor, double thick) {
        setStrokeFillAndThick(gc,strokeColor,fillColor,thick);
        gc.fillRect(topLeft.getX(), topLeft.getY(), getWidth(),getHeight());
        gc.strokeRect(topLeft.getX(), topLeft.getY(), getWidth(),getHeight());
        return gc;
    }

    @Override
    public void moveFigure(double diffX, double diffY) {
        topLeft.movePoint(diffX,diffY);
        bottomRight.movePoint(diffX,diffY);
    }

    @Override
    public boolean figureBelongs(Point eventPoint) {
        return eventPoint.getX() >= topLeft.getX() && eventPoint.getX() <= bottomRight.getX() &&
                eventPoint.getY() >= topLeft.getY() && eventPoint.getY() <= bottomRight.getY();
    }

    @Override
    public boolean figureBelongsIn(Rectangle rectangle){
        return rectangle.figureBelongs(topLeft) && rectangle.figureBelongs(bottomRight);
    }

    @Override
    public String toString() {
        return String.format("Rectángulo [ %s , %s ]", topLeft, bottomRight);
    }

    @Override
    public boolean equals(Object other){
        if(this == other){
            return true;
        }
        if(!(other instanceof Rectangle)){
            return false;
        }
        Rectangle rect = (Rectangle) other;
        return topLeft.equals(rect.getTopLeft()) && bottomRight.equals(rect.getTopLeft());
    }
}