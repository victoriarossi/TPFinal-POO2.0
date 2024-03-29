package backend.model;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

//Para dibujar una elipse se tomará al punto inicial (del click) como esquina superior izquierda
//        del rectángulo que encierra a la elipse y al punto final como esquina inferior derecha del rectángulo
//        que encierra a la elipse.
public class Ellipse extends Figure{

    private final Point centerPoint;
    private final Point topLeft;
    private final double axisX;
    private final double axisY;

    public Ellipse(Point topLeft, Point bottomRight){
        this.topLeft=topLeft;
        this.axisX=setAxisX(topLeft, bottomRight);
        this.axisY=setAxisY(topLeft,bottomRight);
        this.centerPoint=setCenter(topLeft, bottomRight);
    }

    //Setters y getters de las axisas de una elipse
    public double setAxisX(Point topLeft, Point bottomRight){
        return Math.abs(topLeft.getX() - bottomRight.getX());
    }

    public double setAxisY(Point topLeft, Point bottomRight){
        return Math.abs(topLeft.getY() - bottomRight.getY());
    }

    public double getAxisX(){
        return axisX;
    }

    public double getAxisY(){
        return axisY;
    }

    //Set y get del punto medio
    public Point setCenter(Point topLeft, Point bottomRight){
        return new Point(topLeft.getX() + (axisX / 2) , topLeft.getY() +(axisY / 2));
    }

    public Point getCenterPoint(){
        return centerPoint;
    }


    //Para mover la figura debo mover cada punto
    @Override
    public void moveFigure(double diffX, double diffY) {
        centerPoint.movePoint(diffX,diffY);
        topLeft.movePoint(diffX, diffY);
    }

    @Override
    public GraphicsContext setStrokeAndFill(GraphicsContext gc, Color fillColor, Color strokeColor, double thick) {
        setStrokeFillAndThick(gc,strokeColor,fillColor,thick);
        gc.fillOval(topLeft.getX(), topLeft.getY(), axisX,axisY);
        gc.strokeOval(topLeft.getX(), topLeft.getY(),axisX,axisY);
        return gc;
    }

    @Override
    public boolean figureBelongsIn(Rectangle rectangle){
        Point topPoint=centerPoint;
        Point bottomPoint=centerPoint;
        Point leftPoint = centerPoint;
        Point rightPoint=centerPoint;
        rightPoint.movePoint(axisX,0);
        leftPoint.movePoint(-axisX,0);
        bottomPoint.movePoint(0,-axisY);
        topPoint.movePoint(0,axisY);
        return rectangle.figureBelongs(topPoint) && rectangle.figureBelongs(bottomPoint) && rectangle.figureBelongs(leftPoint) && rectangle.figureBelongs(rightPoint);
    }

    @Override
    public boolean figureBelongs(Point eventPoint) {
        return ((Math.pow(Math.abs(eventPoint.getX() - centerPoint.getX()),2))/Math.pow(axisX,2)) +
                ((Math.pow(Math.abs(eventPoint.getY() - centerPoint.getY()),2))/Math.pow(axisY,2))<= 1;
    }


    @Override
    public String toString() {
        return String.format("Elipse [Centro: %s, Eje X: %.2f, Eje Y: %.2f]", centerPoint, axisX, axisY);
    }

    @Override
    public boolean equals(Object other){
        if(this == other){
            return true;
        }
        if(!(other instanceof Ellipse)){
            return false;
        }
        Ellipse ellipse = (Ellipse) other;
        return centerPoint.equals(ellipse.getCenterPoint()) && axisX == ellipse.getAxisX() && axisY == ellipse.getAxisY();
    }

}