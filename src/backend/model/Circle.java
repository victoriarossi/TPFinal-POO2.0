package backend.model;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Circle extends Ellipse {

    public Circle(Point topLeft, Point bottomRight) {
        super(topLeft, bottomRight);
    }

    @Override
    public Point getCenter(Point topLeft, Point bottomRight){
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

    @Override
    public String toString() {
        return String.format("Círculo [Centro: %s, Radio: %.2f]", getCenterPoint(), getAxisX());
    }

    public double getDiameter(){return getAxisX()*2;}


    public double getWidth(){
        return getCenterPoint().getX() - getAxisX();
    }


    public double getHeight(){
        return getCenterPoint().getY() - getAxisX();
    }

    @Override
    public boolean figureBelongs(Point eventPoint) {
        return Math.sqrt(Math.pow(getCenterPoint().getX() - eventPoint.getX(), 2) +
                Math.pow(getCenterPoint().getY() - eventPoint.getY(), 2)) < getAxisX();
    }

    @Override
    public GraphicsContext setStrokeAndFill(GraphicsContext gc, Color fillColor, Color strokeColor, double thick) {
        setStrokeFillAndThick(gc,strokeColor,fillColor,thick);
        gc.fillOval(getWidth(),getHeight(),getDiameter(),getDiameter());
        gc.strokeOval(getWidth(),getHeight(),getDiameter(),getDiameter());
        return gc;
    }

}
