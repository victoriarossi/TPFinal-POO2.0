package frontend.FigureButtons;

import backend.model.Figure;
import backend.model.Point;
import javafx.scene.control.Alert;
import javafx.scene.control.ToggleButton;

public abstract class FigureButtons {
    private final ToggleButton button;

    //Le asigno a cada figura el boton que le corresponde
    public FigureButtons(ToggleButton button){
        this.button=button;
    }

    //Creo la figura correspondiente y en caso de no poder crearla lanzo la alerta
    public abstract Figure activate(Point startPoint, Point endPoint);

    public ToggleButton getButton(){
        return button;
    }

    public void setAlert(String shape){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("ATENCION");
        alert.setHeaderText("Error al dibujar " + shape);
        alert.setContentText("Las formas deben ser dibujadas de arriba a abajo y de izquierda a derecha");
        alert.showAndWait();
    }



}



