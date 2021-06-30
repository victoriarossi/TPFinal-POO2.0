package backend.model;

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
        return centerPoint.x - radius;
    }

    @Override
    public double getHeight(){
        return centerPoint.y - radius;
    }

    @Override
    public void moveFigure(double diffX, double diffY) {
        centerPoint.movePoint(diffX,diffY);
    }
}
