package frontend.FigureButtons;

import backend.model.Figure;
import backend.model.Line;
import backend.model.Point;
import javafx.scene.control.ToggleButton;

public class LineButton extends FigureButtons{
    public LineButton(ToggleButton button) {
        super(button);
    }

    //Puedo crear la linea en cualquier direccion por ende nunca lanzo una alerta
    @Override
    public Figure activate(Point startPoint, Point endPoint) {
        return new Line(startPoint, endPoint);
    }

}
