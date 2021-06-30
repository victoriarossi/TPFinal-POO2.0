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

public class PaintPane extends BorderPane {

	// BackEnd
	CanvasState canvasState;

	// Canvas y relacionados
	Canvas canvas = new Canvas(800, 600);
	GraphicsContext gc = canvas.getGraphicsContext2D();
	Color lineColor = Color.BLACK;
	Color fillColor = Color.YELLOW;

	// Botones Barra Izquierda
	ToggleButton selectionButton = new ToggleButton("Seleccionar");
	ToggleButton rectangleButton = new ToggleButton("Rectángulo");
	ToggleButton circleButton = new ToggleButton("Círculo");
	ToggleButton ellipseButton = new ToggleButton("Elipse");
	ToggleButton squareButton = new ToggleButton("Cuadrado");
	ToggleButton lineButton = new ToggleButton("Linea");
	Slider slider = new Slider(1, 50, 1);
	Label thick = new Label("Borde:");
	Label color = new Label("Relleno:");
	ColorPicker colorPickerThick = new ColorPicker();
	ColorPicker colorPickerFill = new ColorPicker();
	// Dibujar una figura
	Point startPoint;

	// Seleccionar una figura
	Figure selectedFigure;

	// StatusBar
	StatusPane statusPane;

	public PaintPane(CanvasState canvasState, StatusPane statusPane) {
		this.canvasState = canvasState;
		this.statusPane = statusPane;
		ToggleButton[] toolsArr = {selectionButton, rectangleButton, circleButton, ellipseButton, squareButton, lineButton };
		ToggleGroup tools = new ToggleGroup();
		for (ToggleButton tool : toolsArr) {
			tool.setMinWidth(90);
			tool.setToggleGroup(tools);
			tool.setCursor(Cursor.HAND);
		}
		VBox buttonsBox = new VBox(10);
		buttonsBox.getChildren().addAll(toolsArr);
		buttonsBox.getChildren().add(thick);
		slider.setShowTickMarks(true);
		slider.setShowTickLabels(true);
		buttonsBox.getChildren().add(slider);
		colorPickerThick.getStyleClass().add("split-button");
		buttonsBox.getChildren().add(colorPickerThick);
		buttonsBox.getChildren().add(color);
		colorPickerFill.getStyleClass().add("split-button");
		buttonsBox.getChildren().add(colorPickerFill);
		buttonsBox.setPadding(new Insets(5));
		buttonsBox.setStyle("-fx-background-color: #999");
		buttonsBox.setPrefWidth(100);
		gc.setLineWidth(1);
		canvas.setOnMousePressed(event -> {
			startPoint = new Point(event.getX(), event.getY());
		});
		canvas.setOnMouseReleased(event -> {
			Point endPoint = new Point(event.getX(), event.getY());
			if (endPoint.validatePoint(startPoint)) {
				Figure newFigure;
				//Queda horrible, hay que cambiarlo. Funciona para todos menos la linea
				if (rectangleButton.isSelected()) {
					newFigure = new Rectangle(startPoint, endPoint);
				} else if (circleButton.isSelected()) {
					newFigure = new Circle(startPoint, endPoint);
				} else if (lineButton.isSelected()){ //Agregar elipse, linea y cuadrado
					newFigure = new Line(startPoint, endPoint);
				}else {
					return ;
				}
				canvasState.addFigure(newFigure);
			}
			startPoint = null;
			redrawCanvas();
		});
		canvas.setOnMouseMoved(event -> {
			Point eventPoint = new Point(event.getX(), event.getY());
			Figure mySelectedFigure=selectedFigure;
			StringBuilder label = new StringBuilder();
			checkingFigureBelongs(eventPoint,label,eventPoint.toString());
			selectedFigure=mySelectedFigure;
		});

		canvas.setOnMouseClicked(event -> {
			if (selectionButton.isSelected()) {
				Point eventPoint = new Point(event.getX(), event.getY());
				StringBuilder label = new StringBuilder("Se seleccionó: ");
				checkingFigureBelongs(eventPoint,label,"Ninguna figura encontrada");
				redrawCanvas();
			}
		});
		canvas.setOnMouseDragged(event -> {
			if (selectionButton.isSelected()) {
				Point eventPoint = new Point(event.getX(), event.getY());
				if(selectedFigure!=null) {
					selectedFigure.moveFigure(eventPoint.getDiffX(startPoint), eventPoint.getDiffY(startPoint));
				}
				redrawCanvas();
			}
		});
		setLeft(buttonsBox);
		setRight(canvas);
	}

	private void checkingFigureBelongs(Point eventPoint , StringBuilder label, String elseString){
		boolean found = false;
		for (Figure figure : canvasState.figures()) {
			if (figure.figureBelongs(eventPoint)) {
				found=true;
				selectedFigure = figure;
				label.append(figure.toString());
			}
		}
		if(!found){
			statusPane.updateStatus(elseString);
		}else {
			statusPane.updateStatus(label.toString());
		}
	}

	void redrawCanvas() {
		gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
		for (Figure figure : canvasState.figures()) {
			if (figure == selectedFigure) {
				gc.setStroke(Color.RED);
			} else {
				gc.setStroke(lineColor);
			}
			gc.setFill(fillColor);
			gc = figure.setStrokeAndFill(gc);
		}
	}
}