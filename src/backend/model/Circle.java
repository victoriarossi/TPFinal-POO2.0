package backend.model;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Circle extends Ellipse {

    public Circle(Point topLeft, Point bottomRight) {
        super(topLeft, bottomRight);
    }

    @Override
    public Point setCenter(Point topLeft, Point bottomRight){
        return topLeft;
    }

    @Override
    public double setAxisX(Point topLeft, Point bottomRight){
        return Math.abs(bottomRight.getX() - topLeft.getX());
    }

    @Override
    public double setAxisY(Point topLeft, Point bottomRight){
        return setAxisX(topLeft, bottomRight);
    }

    //Le doy un ancho
    public double getWidth(){
        return getCenterPoint().getX() - getAxisX();
    }

    //Le doy una altura
    public double getHeight(){
        return getCenterPoint().getY() - getAxisX();
    }

    public double getDiameter(){return getAxisX()*2;}

    @Override
    public boolean figureBelongs(Point eventPoint) {
        return Math.sqrt(Math.pow(getCenterPoint().getX() - eventPoint.getX(), 2) +
                Math.pow(getCenterPoint().getY() - eventPoint.getY(), 2)) <= getAxisX();
    }

    @Override
    public GraphicsContext setStrokeAndFill(GraphicsContext gc, Color fillColor, Color strokeColor, double thick) {
        setStrokeFillAndThick(gc,strokeColor,fillColor,thick);
        gc.fillOval(getWidth(),getHeight(),getDiameter(),getDiameter());
        gc.strokeOval(getWidth(),getHeight(),getDiameter(),getDiameter());
        return gc;
    }

    @Override
    public String toString() {
        return String.format("Círculo [Centro: %s, Radio: %.2f]", getCenterPoint(), getAxisX());
    }

    @Override
    public boolean equals(Object other){
        if(this == other){
            return true;
        }
        if(!(other instanceof Circle)){
            return false;
        }
        Circle circle = (Circle) other;
        return getCenterPoint().equals(circle.getCenterPoint()) && getAxisX() == circle.getAxisX() && getAxisY() == circle.getAxisY();
    }
}