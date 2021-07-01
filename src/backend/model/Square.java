package backend.model;


public class Square extends Rectangle {
    public Square(Point topLeft, Point bottomRight) {
        super(topLeft, new Point(bottomRight.getX(), bottomRight.getX()));
    }

    public String toString() {
        return String.format("Cuadrado [ %s , %s ]", this.getTopLeft().getX(), this.getBottomRight().getX());
    }

    public double getHeight() {
        return Math.abs(this.getTopLeft().getY());
    }

    public double getWidth() {
        return this.getHeight();
    }
}
