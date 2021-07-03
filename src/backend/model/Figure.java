package backend.model;

import javafx.scene.canvas.GraphicsContext;

import javafx.scene.paint.Color;



public abstract class Figure{

    private Color fillColor, strokeColor;

    private double thick;

    public abstract void moveFigure(double diffX, double diffY);

    //Chequea si el punto eventPoint pertenece a la figura
    public abstract boolean figureBelongs(Point eventPoint);

    public abstract GraphicsContext setStrokeAndFill(GraphicsContext gc, Color fillColor, Color strokeColor, double thick);

    //Chequea si la figura pertenece al rectangula pasado por parametro
    public abstract boolean figureBelongsIn(Rectangle rectangle);

    public void setStrokeFillAndThick(GraphicsContext gc,Color strokeColor, Color fillColor, double thick){
        gc.setFill(fillColor);
        gc.setStroke(strokeColor);
        gc.setLineWidth(thick);
    }
    public void setStrokeColor(Color strokeColor) {
        this.strokeColor = strokeColor;
    }

    public void setFillColor(Color fillColor) {
        this.fillColor = fillColor;
    }

    public Color getFill() {
        return fillColor;
    }

    public Color getLine() {
        return strokeColor;
    }

    public void setThickness(double thick) {
        this.thick = thick;
    }

    public double getThickness() {
        return thick;
    }

}