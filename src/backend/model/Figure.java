package backend.model;

public abstract class Figure {
    public abstract void moveFigure (double diffX, double diffY);
    public abstract double getWidth();
    public abstract double getHeight();
    public abstract boolean figureBelongs(Point eventPoint);
}
