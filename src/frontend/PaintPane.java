package frontend;

import backend.CanvasState;
import backend.model.*;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;


public class PaintPane extends BorderPane {

	// BackEnd
	CanvasState canvasState;

	// Canvas y relacionados
	Canvas canvas = new Canvas(800, 600);
	GraphicsContext gc = canvas.getGraphicsContext2D();
	private static final Color strokeColor = Color.BLACK;
	private static final Color fillColor = Color.YELLOW;
	private static final Color SelectedColor = Color.RED;

	// Botones Barra Izquierda
	ToggleButton selectionButton = new ToggleButton("Seleccionar");
	ToggleButton rectangleButton = new ToggleButton("Rectángulo");
	ToggleButton circleButton = new ToggleButton("Círculo");
	ToggleButton ellipseButton = new ToggleButton("Elipse");
	ToggleButton squareButton = new ToggleButton("Cuadrado");
	ToggleButton lineButton = new ToggleButton("Linea");
	ToggleButton deleteButton = new ToggleButton("Borrar");
	ToggleButton toBack = new ToggleButton("Al Fondo");
	ToggleButton toFront = new ToggleButton("Al Frente");
	Slider slider = new Slider(1, 50, 1);
	Label thick = new Label("Borde:");
	Label color = new Label("Relleno:");
	ColorPicker colorPickerThick = new ColorPicker(strokeColor);
	ColorPicker colorPickerFill = new ColorPicker(fillColor);
	// Dibujar una figura
	Point startPoint;
	Point endPoint;

	// Seleccion de figuras
	List<Figure> selectedFigure = new ArrayList<>();

	// StatusBar
	StatusPane statusPane;

	public PaintPane(CanvasState canvasState, StatusPane statusPane) {
		this.canvasState = canvasState;
		this.statusPane = statusPane;
		ToggleButton[] toolsArr = {selectionButton, rectangleButton, circleButton, ellipseButton, squareButton, lineButton, deleteButton, toFront,toBack};
		FigureButtons[] figureButtons={new RectangleButton(rectangleButton), new CircleButton(circleButton),
            new SquareButton(squareButton), new LineButton(lineButton), new EllipseButton(ellipseButton)};

		ToggleGroup tools = new ToggleGroup();
		for (ToggleButton tool : toolsArr) {
			tool.setMinWidth(90);
			tool.setToggleGroup(tools);
			tool.setCursor(Cursor.HAND);
		}
		VBox buttonsBox = new VBox(10);
		buttonsBox.getChildren().addAll(toolsArr);
		slider.setShowTickMarks(true);
		slider.setShowTickLabels(true);
		colorPickerThick.getStyleClass().add("split-button");
		colorPickerFill.getStyleClass().add("split-button");
		buttonsBox.getChildren().addAll(thick, slider, colorPickerThick, color, colorPickerFill);
		buttonsBox.setPadding(new Insets(5));
		buttonsBox.setStyle("-fx-background-color: #999");
		buttonsBox.setPrefWidth(100);
		gc.setLineWidth(1);
		canvas.setOnMousePressed(event -> { startPoint = new Point(event.getX(), event.getY()); });
		canvas.setOnMouseReleased(event -> {
			endPoint = new Point(event.getX(), event.getY());
			Figure newFigure = null;
			for (FigureButtons figure : figureButtons) {
				if (figure.getButton().isSelected()) {
					newFigure = figure.activate(startPoint, endPoint);
				}
			}
			if(!selectionButton.isSelected() && newFigure!=null) {
				newFigure.setFillColor(colorPickerFill.getValue());
				newFigure.setStrokeColor(colorPickerThick.getValue());
				newFigure.setThickness(slider.getValue());
				canvasState.addFigure(newFigure);
				startPoint = null;
			}
			redrawCanvas();
		});

		canvas.setOnMouseMoved(event -> {
			Point eventPoint = new Point(event.getX(), event.getY());
			statusPane.updateStatus(eventPoint.toString());
			for (Figure figure : selectedFigure) {
				StringBuilder label = new StringBuilder();
				checkingFigureBelongs(figure, label, eventPoint.toString());
			}
		});

		canvas.setOnMouseClicked(event -> {
			if (selectionButton.isSelected()) {
				Point eventPoint = new Point(event.getX(), event.getY());
				//Si el punto donde hago click no pertenece a una de las figuras, reinicio lo seleccionado
				if(!eventPoint.belongsIn(selectedFigure)){
					selectedFigure.clear();
				}
				Rectangle selectedRectangle = new Rectangle(startPoint, endPoint);
				if (selectedRectangle == null){
					setAlert();
				}
				searchingFigures(selectedRectangle);
				StringBuilder label = new StringBuilder("Se seleccionó: ");
				for (Figure figure : selectedFigure) {
					checkingFigureBelongs(figure, label, "Ninguna figura encontrada");

				}
				redrawCanvas();
			}
		});

		canvas.setOnMouseDragged(event -> {
			if (selectionButton.isSelected()) {
				Point eventPoint = new Point(event.getX(), event.getY());
				for (Figure figure : selectedFigure) {
					figure.moveFigure(eventPoint.getDiffX(startPoint), eventPoint.getDiffY(startPoint));
				}
				redrawCanvas();
			}
		});
		setLeft(buttonsBox);
		setRight(canvas);

		slider.setOnMouseReleased(event -> {
			for (Figure figure : selectedFigure) {
				figure.setThickness(slider.getValue());
			}
			redrawCanvas();
		});

		deleteButton.setOnMouseClicked(event -> {
			for (Figure figure : selectedFigure) {
				canvasState.deleteFigure(figure);
			}
			redrawCanvas();

		});

		colorPickerThick.setOnAction(event -> {
			for (Figure figure : selectedFigure) {
				figure.setStrokeColor(colorPickerThick.getValue());
			}
			redrawCanvas();
		});

		colorPickerFill.setOnAction(event -> {
			for (Figure figure : selectedFigure) {
				figure.setFillColor(colorPickerFill.getValue());
			}
			redrawCanvas();
		});

		toBack.setOnMousePressed(event -> {
			canvasState.moveToBack(selectedFigure);
			redrawCanvas();
		});

		toFront.setOnMousePressed(event ->{
			canvasState.moveToFront(selectedFigure);
			redrawCanvas();
		});
	}

	private void checkingFigureBelongs(Figure mySelectedFigure, StringBuilder label, String elseString) {
		boolean found = false;
		for (Figure figure : canvasState.figures()) {
			if (figure == mySelectedFigure) {
				found = true;
				label.append(figure.toString());
			}
		}
		if (!found) {
			statusPane.updateStatus(elseString);
		} else {
			statusPane.updateStatus(label.toString());
		}
	}

	void redrawCanvas() {
		gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
		for (Figure figure : canvasState.figures()) {
			if (selectedFigure.contains(figure)) {

				gc = figure.setStrokeAndFill(gc, figure.getFill(), SelectedColor, figure.getThickness());
			}
			else{
			gc = figure.setStrokeAndFill(gc, figure.getFill(), figure.getLine(), figure.getThickness());
		}
		}
	}

	void searchingFigures(Rectangle rectangle) {
		for (Figure figure : canvasState.figures()) {
			if (figure.figureBelongsIn(rectangle)) {
				selectedFigure.add(figure);
			}
		}
	}
	public void setAlert(){
		Alert alert = new Alert(Alert.AlertType.INFORMATION);
		alert.setTitle("ATENCION");
		alert.setHeaderText("Error de seleccion");
		alert.setContentText("La seleccion debe ser dibujada de arriba a abajo y de izquierda a derecha");
		alert.showAndWait();
	}
}