package backend.model;

public class Square extends Rectangle {
    public Square(Point topLeft, Point bottomRight) {
        super(topLeft, bottomRight);
    }

    @Override
    public double getWidth() {
        return this.getHeight();
    }

    @Override
    protected Point setBottomRight(Point bottomRight) {
        double side = getSide(bottomRight);
        return new Point(bottomRight.getX(), getTopLeft().getY() + side);
    }

    private double getSide(Point bottomRight){
        return Math.abs(bottomRight.getX() - getTopLeft().getX());
    }
    @Override
    public String toString() {
        return String.format("Cuadrado [ %s , %s ]", getTopLeft().getX(), getBottomRight().getX());
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        } else if (!(other instanceof Square)) {
            return false;
        } else {
            Square square = (Square)other;
            return getTopLeft().equals(square.getTopLeft()) && getBottomRight().equals(square.getTopLeft());
        }
    }
}
