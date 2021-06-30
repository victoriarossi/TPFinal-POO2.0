package backend.model;

public class Rectangle extends Figure {

    private final Point topLeft, bottomRight;

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

    @Override
    public double getWidth(){
        return Math.abs(topLeft.x - bottomRight.x);
    }

    @Override
    public double getHeight(){
        return Math.abs(topLeft.y - bottomRight.y);
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
}
