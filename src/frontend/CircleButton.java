package frontend;

import backend.model.Circle;
import backend.model.Figure;
import backend.model.Point;
import javafx.scene.control.ToggleButton;

public class CircleButton extends FigureButtons{
    public CircleButton(ToggleButton button) {
        super(button);
    }

    @Override
    public Figure activate(Point startPoint, Point endPoint) {
        if (endPoint.validatePoint(startPoint)){
            return new Circle(startPoint, endPoint);
        }
        setAlert("Circulo");
        return null;
    }



}
