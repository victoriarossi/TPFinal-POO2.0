package backend.model;

public class Point {
    private double x;
    private double y;

    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public void movePoint(double diffX, double diffY) {
        this.x += diffX;
        this.y += diffY;
    }

    public double getX() {
        return this.x;
    }

    public double getY() {
        return this.y;
    }

    public String toString() {
        return String.format("{%.2f , %.2f}", this.x, this.y);
    }

    public boolean validatePoint(Point startPoint) {
        return startPoint != null && !(this.x < startPoint.getX()) && !(this.y < startPoint.getY());
    }

    public double getDiffX(Point p2) {
        return (this.x - p2.getX()) / 100.0D;
    }

    public double getDiffY(Point p2) {
        return (this.y - p2.getY()) / 100.0D;
    }
}
