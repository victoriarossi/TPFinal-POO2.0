package backend.model;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public abstract class Figure {
    public Figure() {
    }

    public abstract void moveFigure(double var1, double var3);

    public abstract double getWidth();

    public abstract double getHeight();

    public abstract boolean figureBelongs(Point var1);

    public abstract GraphicsContext setStrokeAndFill(GraphicsContext var1, Color var2, Color var3);

    public abstract void setStrokeColor(Color var1);

    public abstract void setFillColor(Color var1);

    public abstract Color getFill();

    public abstract Color getLine();

    public abstract boolean figureBelongsIn(Rectangle var1);
}
