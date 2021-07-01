package frontend;

import backend.model.Figure;
import backend.model.Line;
import backend.model.Point;
import javafx.scene.control.ToggleButton;

public class LineButton extends FigureButtons{
    public LineButton(ToggleButton button) {
        super(button);
    }

    @Override
    public Figure activate(Point startPoint, Point endPoint) {
        return new Line(startPoint, endPoint);
    }

}
