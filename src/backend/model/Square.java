package backend.model;


public class Square extends Rectangle {

    public Square(Point topLeft, Point bottomRight) {
        super(topLeft, bottomRight);
    }


    @Override
    public double getWidth(){
        return getHeight();
    }

    @Override
    protected Point setBottomRight(Point bottomRight){
        double side = Math.abs(bottomRight.getX()-getTopLeft().getX());
        return new Point(getTopLeft().getX(), getTopLeft().getY() + side);
    }

    @Override
    public String toString() {
        return String.format("Cuadrado [ %s , %s ]", getTopLeft().getX(), getBottomRight().getX());
    }

    @Override
    public boolean equals(Object other){
        if(this == other){
            return true;
        }
        if(!(other instanceof Rectangle)){
            return false;
        }
        Square square = (Square) other;
        return getTopLeft().equals(square.getTopLeft()) && getBottomRight().equals(square.getTopLeft());
    }

}