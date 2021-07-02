package frontend;

import backend.model.Figure;
import backend.model.Point;
import javafx.scene.control.ToggleButton;

public abstract class FigureButtons {
    private ToggleButton button;

    public FigureButtons(ToggleButton button){
        this.button=button;
    }
    public abstract Figure activate(Point startPoint, Point endPoint);
    public ToggleButton getButton(){
        return button;
    }

}

