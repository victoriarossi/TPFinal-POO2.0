package frontend;

import backend.CanvasState;
import backend.model.*;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
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
	private static Color strokeColor = Color.BLACK;
	private static Color fillColor = Color.YELLOW;

	// Botones Barra Izquierda
	ToggleButton selectionButton = new ToggleButton("Seleccionar");
	ToggleButton rectangleButton = new ToggleButton("Rectángulo");
	ToggleButton circleButton = new ToggleButton("Círculo");
	ToggleButton ellipseButton = new ToggleButton("Elipse");
	ToggleButton squareButton = new ToggleButton("Cuadrado");
	ToggleButton lineButton = new ToggleButton("Linea");
	ToggleButton deleteButton = new ToggleButton("Borrar");
	Slider slider = new Slider(1, 50, 1);
	Label thick = new Label("Borde:");
	Label color = new Label("Relleno:");
	ColorPicker colorPickerThick = new ColorPicker(strokeColor);
	ColorPicker colorPickerFill = new ColorPicker(fillColor);
	// Dibujar una figura
	Point startPoint;
	Point endPoint;

	//Selecionar Varias figuras en rectangulo
	Point selectedStartPoint, selectedEndPoint;

	// Seleccion de figuras
	List<Figure> selectedFigure = new ArrayList<>();

	// StatusBar
	StatusPane statusPane;

	public PaintPane(CanvasState canvasState, StatusPane statusPane) {
		this.canvasState = canvasState;
		this.statusPane = statusPane;
		ToggleButton[] toolsArr = {selectionButton, rectangleButton, circleButton, ellipseButton, squareButton, lineButton,deleteButton };
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
		canvas.setOnMousePressed(event -> {
			startPoint = new Point(event.getX(), event.getY());
		});
		canvas.setOnMouseReleased(event -> {
			endPoint = new Point(event.getX(), event.getY());
			Figure newFigure=null;
			if (!lineButton.isSelected() && endPoint.validatePoint(startPoint)) {
				//Queda horrible, hay que cambiarlo. Funciona para todos
				if (rectangleButton.isSelected()) {
					newFigure = new Rectangle(startPoint, endPoint);
				} else if (circleButton.isSelected()) {
					newFigure = new Circle(startPoint, endPoint);
				} else if (squareButton.isSelected()){
					newFigure = new Square(startPoint, endPoint);
				}else if (ellipseButton.isSelected()){
					newFigure = new Ellipse(startPoint, endPoint);
				}else{
					return;
				}
			} else if(lineButton.isSelected()){
				newFigure = new Line(startPoint, endPoint);
			}
			newFigure.setFillColor(colorPickerFill.getValue());
			newFigure.setStrokeColor(colorPickerThick.getValue());
			newFigure.setThickness(slider.getValue());
			canvasState.addFigure(newFigure);
			startPoint = null;
			redrawCanvas();
		});

		canvas.setOnMouseMoved(event -> {
			Point eventPoint = new Point(event.getX(), event.getY());
			for(Figure figure : selectedFigure){
				StringBuilder label = new StringBuilder();
				checkingFigureBelongs(figure,label,eventPoint.toString());
			}
		});

		canvas.setOnMouseClicked(event -> {
			if (selectionButton.isSelected()) {
				selectedFigure.clear();
				Rectangle selectedRectangle = new Rectangle(startPoint,endPoint);
				searchingFigures(selectedRectangle);
				StringBuilder label = new StringBuilder("Se seleccionó: ");
				for (Figure figure : selectedFigure){
					checkingFigureBelongs(figure,label,"Ninguna figura encontrada");
					redrawCanvas();
				}
			}
		});

		canvas.setOnMouseDragged(event -> {
			if (selectionButton.isSelected()) {
				Point eventPoint = new Point(event.getX(), event.getY());
				for(Figure figure : selectedFigure){
					figure.moveFigure(eventPoint.getDiffX(startPoint), eventPoint.getDiffY(startPoint));
				}
				redrawCanvas();
			}
		});
		setLeft(buttonsBox);
		setRight(canvas);

		slider.setOnMouseReleased(event -> {
			for( Figure figure : selectedFigure){
				figure.setThickness(slider.getValue());
			}
			redrawCanvas();
		});

		deleteButton.setOnMousePressed(event -> {
			for ( Figure figure : selectedFigure){
				canvasState.deleteFigure(figure);
			}
			redrawCanvas();

		});
		colorPickerThick.setOnAction(event -> {
			for(Figure figure : selectedFigure){
				figure.setStrokeColor(colorPickerThick.getValue());
			}
			redrawCanvas();
		});
		colorPickerFill.setOnAction(event -> {
			for(Figure figure : selectedFigure){
				figure.setFillColor(colorPickerFill.getValue());
			}
			redrawCanvas();
		});
		slider.setOnMouseClicked(event -> {

		});
	}

	private void checkingFigureBelongs(Figure mySelectedFigure, StringBuilder label, String elseString){
		boolean found = false;
		for (Figure figure : canvasState.figures()) {
			if (figure==mySelectedFigure) {
				found=true;
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
			if (selectedFigure.contains(figure)) {
				gc.setStroke(Color.RED);
				gc.setFill(fillColor);
				figure.setFillColor(colorPickerFill.getValue());
				figure.setStrokeColor(colorPickerThick.getValue());
			}
			gc = figure.setStrokeAndFill(gc, figure.getFill() , figure.getLine(), figure.getThickness());
		}
	}

	void searchingFigures(Rectangle rectangle){
		for(Figure figure : canvasState.figures()){
			if(figure.figureBelongsIn(rectangle)){
				selectedFigure.add(figure);
			}
		}
	}

}
