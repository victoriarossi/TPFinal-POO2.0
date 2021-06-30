package backend.model;

public class Square extends Rectangle {

    public Square(Point topLeft, Point bottomRight) {
        super(topLeft, new Point(bottomRight.getX(), topLeft.getY() + bottomRight.getX()));
    }

    @Override
    public String toString() {
        return String.format("Cuadrado [ %s , %s ]", getTopLeft().getX(), getBottomRight().getX());
    }

}
