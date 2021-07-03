package frontend;

import backend.model.Figure;
import backend.model.Point;
import backend.model.Rectangle;
import javafx.scene.control.ToggleButton;

public class RectangleButton extends FigureButtons {

    public RectangleButton(ToggleButton button) {
        super(button);
    }

    @Override
    public Figure activate(Point startPoint, Point endPoint) {
        if (endPoint.validatePoint(startPoint)){
            return new Rectangle(startPoint, endPoint);
        }
        setAlert("Rectangulo");
        return null;
    }


}
