package backend.model;

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
}
