package frontend;

import backend.model.Circle;
import backend.model.Ellipse;
import backend.model.Figure;
import backend.model.Point;
import javafx.scene.control.ToggleButton;

public class EllipseButton extends FigureButtons{
    public EllipseButton(ToggleButton button) {
        super(button);
    }

    @Override
    public Figure activate(Point startPoint, Point endPoint) {
        if (endPoint.validatePoint(startPoint)){
            return new Ellipse(startPoint, endPoint);
        }
        setAlert("Elipse");
        return null;
    }
}
