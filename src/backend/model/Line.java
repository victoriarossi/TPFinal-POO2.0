package backend.model;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Line extends Rectangle{


    public Line(Point firstPoint, Point secondPoint){
        super(firstPoint,secondPoint);
    }

    //Las lineas son unicamente un borde
    public double getHeight() {
        return 0;
    }

    public double getWidth(){
        return 0;
    }

    @Override
    public boolean figureBelongs(Point eventPoint) {
//        double m=((getTopLeft().getY() - getBottomRight().getY())/(getTopLeft().getX() - getBottomRight().getX()));
//        return (eventPoint.getY() - getTopLeft().getY()) == (m*(eventPoint.getX() - getTopLeft().getX()));
        return true;
    }

    @Override
    public String toString(){
        return String.format("Línea [ %s, %s ]", getTopLeft(),getBottomRight());
    }

    @Override
    public GraphicsContext setStrokeAndFill(GraphicsContext gc, Color fillColor, Color strokeColor, double thick) {
        gc.setStroke(strokeColor);
        gc.setLineWidth(thick);
        gc.strokeLine(getTopLeft().getX(),getTopLeft().getY(),getBottomRight().getX(),getBottomRight().getY());
        return gc;
    }

}