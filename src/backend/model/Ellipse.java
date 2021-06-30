package backend.model;

import javafx.scene.canvas.GraphicsContext;

//Para dibujar una elipse se tomará al punto inicial (del click) como esquina superior izquierda
//        del rectángulo que encierra a la elipse y al punto final como esquina inferior derecha del rectángulo
//        que encierra a la elipse.
public class Ellipse extends Rectangle{

    public Ellipse(Point topLeft, Point bottomRight){
        super(topLeft,bottomRight);
    }

    @Override
    public boolean figureBelongs(Point eventPoint) {
        return ((Math.pow((eventPoint.getX() - getCenter().getX()),2))/Math.pow(getWidth(),2)) +
                ((Math.pow((eventPoint.getY() - getCenter().getY()),2))/Math.pow(getHeight(),2))<=1;
    }

    public double getAxisMay(){
        if(getHeight()>getWidth()){
            return getHeight();
        }
        return getWidth();
    }

    public double getAxisMen(){
        if(getHeight()>getWidth()){
            return getWidth();
        }
        return getHeight();
    }

    @Override
    public String toString() {
        return String.format("Elipse [Centro: %s, Eje Mayor: %.2f, Eje Menor: %.2f]", getCenter(), getAxisMay(), getAxisMen());
    }

    @Override
    public void moveFigure(double diffX, double diffY) {
        getCenter().movePoint(diffX,diffY);
    }

    @Override
    public GraphicsContext setStrokeAndFill(GraphicsContext gc) {
        gc.fillOval(getWidth(),getHeight(),getAxisMay(),getAxisMen());
        gc.strokeOval(getWidth(),getHeight(),getAxisMay(),getAxisMen());
        return gc;
    }
}
