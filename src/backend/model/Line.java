package backend.model;

import javafx.scene.canvas.GraphicsContext;

public class Line extends Rectangle{

    public Line(Point firstPoint, Point secondPoint){
        super(firstPoint,secondPoint);
    }

    @Override
    public double getHeight() {
        return 0;
    }

    @Override
    public String toString(){
        return String.format("Línea [ %s, %s ]", getTopLeft(),getBottomRight());
    }

    @Override
    public GraphicsContext setStrokeAndFill(GraphicsContext gc) {
        gc.strokeLine(getTopLeft().getX(),getTopLeft().getY(),getBottomRight().getX(),getBottomRight().getY());
        return gc;
    }
}
