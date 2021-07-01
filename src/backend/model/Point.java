package backend.model;

public class Point {

    private double x, y;

    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public void movePoint(double diffX, double diffY){
        x+=diffX;
        y+=diffY;
    }
    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    @Override
    public String toString() {
        return String.format("{%.2f , %.2f}", x, y);
    }

    public boolean validatePoint(Point startPoint){
        return !(startPoint == null || x < startPoint.getX() || y < startPoint.getY());
    }

    public double getDiffX(Point p2){
        return (x-p2.getX())/100;
    }

    public double getDiffY(Point p2){
        return (y-p2.getY())/100;
    }
}
