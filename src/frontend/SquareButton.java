package frontend;

import backend.model.Circle;
import backend.model.Figure;
import backend.model.Point;
import backend.model.Square;
import javafx.scene.control.ToggleButton;

public class SquareButton extends FigureButtons{
    public SquareButton(ToggleButton button) {
        super(button);
    }

    @Override
    public Figure activate(Point startPoint, Point endPoint) {
        if (endPoint.validatePoint(startPoint)){
            return new Square(startPoint, endPoint);
        }
        return null;
    }
}
