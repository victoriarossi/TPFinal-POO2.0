package frontend;

import backend.CanvasState;
import backend.model.*;
import frontend.FigureButtons.*;
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
	Canvas canvas = new Canvas(CANVAS_WIDTH, CANVAS_HEIGHT);
	GraphicsContext gc = canvas.getGraphicsContext2D();


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
	Slider slider = new Slider(SLIDER_MIN, SLIDER_MAX, SLIDER_VALUE);
	Label thick = new Label("Borde:");
	Label color = new Label("Relleno:");
	ColorPicker colorPickerStroke = new ColorPicker(strokeColor);
	ColorPicker colorPickerFill = new ColorPicker(fillColor);

	// Dibujar una figura
	Point startPoint;
	Point endPoint;

	// Seleccion de figuras
	List<Figure> selectedFigure = new ArrayList<>();

	// StatusBar
	StatusPane statusPane;

	private static final Color strokeColor = Color.BLACK;
	private static final Color fillColor = Color.YELLOW;
	private static final Color SelectedColor = Color.RED;
	private static final int INSETS_VALUE=5;
	private static final int VBOX_SPACING=10;
	private static final int LINE_WIDTH=1;
	private static final int PREF_WIDTH=100;
	private static final int CANVAS_WIDTH=800;
	private static final int CANVAS_HEIGHT=600;
	private static final int SLIDER_MIN=1;
	private static final int SLIDER_MAX=50;
	private static final int SLIDER_VALUE=1;

	public PaintPane(CanvasState canvasState, StatusPane statusPane) {
		this.canvasState = canvasState;
		this.statusPane = statusPane;

		// Arreglo de botones
		ToggleButton[] toolsArr = {selectionButton, rectangleButton, circleButton, ellipseButton, squareButton, lineButton,
				deleteButton, toFront,toBack};

		// Arreglo de clases que le dan funcionalidad a los botones de figuras
		FigureButtons[] figureButtons={new RectangleButton(rectangleButton), new CircleButton(circleButton),
            new SquareButton(squareButton), new LineButton(lineButton), new EllipseButton(ellipseButton)};

		ToggleGroup tools = new ToggleGroup();
		for (ToggleButton tool : toolsArr) {
			tool.setMinWidth(90);
			tool.setToggleGroup(tools);
			tool.setCursor(Cursor.HAND);
		}

		VBox buttonsBox = new VBox(VBOX_SPACING);
		buttonsBox.getChildren().addAll(toolsArr);
		slider.setShowTickMarks(true);
		slider.setShowTickLabels(true);
		colorPickerStroke.getStyleClass().add("split-button");
		colorPickerFill.getStyleClass().add("split-button");
		buttonsBox.getChildren().addAll(thick, slider, colorPickerStroke, color, colorPickerFill);
		buttonsBox.setPadding(new Insets(INSETS_VALUE));
		buttonsBox.setStyle("-fx-background-color: #999");
		buttonsBox.setPrefWidth(PREF_WIDTH);
		gc.setLineWidth(LINE_WIDTH);

		canvas.setOnMousePressed(this::mousePressed);
		canvas.setOnMouseMoved(this::mouseMoved);
		canvas.setOnMouseClicked(this::mouseClicked);
		canvas.setOnMouseDragged(this::mouseDragged);

		canvas.setOnMouseReleased(event -> {
			endPoint = new Point(event.getX(), event.getY());
			Figure newFigure = null;
			//Creo la figura segun el boton seleccionado
			for (FigureButtons figure : figureButtons) {
				if (figure.getButton().isSelected()) {
					newFigure = figure.activate(startPoint, endPoint);
				}
			}
			//Al tener creada la figura le doy relleno y bordes
			//La agrego a mi array de figuras en canvasState
			if(!selectionButton.isSelected() && newFigure!=null) {
				newFigure.setFillColor(colorPickerFill.getValue());
				newFigure.setStrokeColor(colorPickerStroke.getValue());
				newFigure.setThickness(slider.getValue());
				canvasState.addFigure(newFigure);
				startPoint = null;
			}
			redrawCanvas();
		});

		//Asignacion de funciones a botones

		//Al soltar el slider setearles el grosor asignado a las figuras seleccionadas
		slider.setOnMouseReleased(event -> {
			for (Figure figure : selectedFigure) {
				figure.setThickness(slider.getValue());
			}
			redrawCanvas();
		});

		//Al hacer click en el boton "Borrar" eliminar las figuras seleccionadas
		deleteButton.setOnMouseClicked(event -> {
			for (Figure figure : selectedFigure) {
				canvasState.deleteFigure(figure);
			}
			redrawCanvas();

		});

		//Al seleccionar los colores de relleno y borde setearlo a las figuras seleccionadas
		colorPickerStroke.setOnAction(event -> {
			for (Figure figure : selectedFigure) {
				figure.setStrokeColor(colorPickerStroke.getValue());
			}
			redrawCanvas();
		});

		colorPickerFill.setOnAction(event -> {
			for (Figure figure : selectedFigure) {
				figure.setFillColor(colorPickerFill.getValue());
			}
			redrawCanvas();
		});

		//Al hacer click en los botones toBack toFront mover las figuras seleccionadas hacia adelante
		//o hacia atras segun corresponda
		toBack.setOnMouseClicked(event -> {
			canvasState.moveToBack(selectedFigure);
			redrawCanvas();
		});

		toFront.setOnMouseClicked(event ->{
			canvasState.moveToFront(selectedFigure);
			redrawCanvas();
		});

		//Reinicio las figuras seleccionadas cada vez que apreto el boton de seleccion
		selectionButton.setOnMouseClicked(event -> {
			selectedFigure.clear();
		});

		setLeft(buttonsBox);
		setRight(canvas);
	}

	private void mousePressed(MouseEvent event) {
		startPoint = new Point(event.getX(), event.getY());
	}

	private void mouseMoved(MouseEvent event){
		Point eventPoint = new Point(event.getX(), event.getY());
		statusPane.updateStatus(eventPoint.toString());

		for (Figure figure : selectedFigure) {
			StringBuilder label = new StringBuilder();
			checkingFigureBelongs(figure, label, eventPoint.toString());
		}
	}

	private void mouseClicked(MouseEvent event){
		if (selectionButton.isSelected()) {
			//Alerta en caso de que la seleccion se realice de derecha a izquierda
			// o de abajo hacia arriba
			if (selectedFigure.isEmpty() && !endPoint.validatePoint(startPoint)) {
				setAlert();
			}

			//Reinicio la seleccion si suelto el mouse por fuera de las figuras seleccionadas
			if(!endPoint.belongsIn(selectedFigure)){
				selectedFigure.clear();
			}

			//Seleccion por medio de un rectangulo
			Rectangle selectedRectangle = new Rectangle(startPoint, endPoint);
			searchingFigures(selectedRectangle);
			StringBuilder label = new StringBuilder("Se seleccionó: ");
			for (Figure figure : selectedFigure) {
				checkingFigureBelongs(figure, label, "Ninguna figura encontrada");
			}
			redrawCanvas();
		}
	}

	private void mouseDragged(MouseEvent event){
		if (selectionButton.isSelected()) {
			Point eventPoint = new Point(event.getX(), event.getY());
			//Arrastro las figuras seleccionadas
			for (Figure figure : selectedFigure) {
				figure.moveFigure(eventPoint.getDiffX(startPoint), eventPoint.getDiffY(startPoint));
			}
			redrawCanvas();
		}
	}

	//Chequeo las figuras seleccionadas y las nombro en el statusPane
	private void checkingFigureBelongs(Figure mySelectedFigure, StringBuilder label, String elseString) {
		boolean found = false;
		for (Figure figure : canvasState.figures()) {
			if (figure == mySelectedFigure) {
				found = true;
				label.append(figure.toString());
			}
		}
		if (found) {
			statusPane.updateStatus(label.toString());
		} else {
			statusPane.updateStatus(elseString);
		}
	}

	private void redrawCanvas() {
		gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
		for (Figure figure : canvasState.figures()) {
			if (selectedFigure.contains(figure)) {
				//Cuando esta seleccionada le asigno un borde selectedColor(ROJO)
				// y el color que corresponda
				gc = figure.setStrokeAndFill(gc, figure.getFill(), SelectedColor, figure.getThickness());
			}
			else{
			gc = figure.setStrokeAndFill(gc, figure.getFill(), figure.getStroke(), figure.getThickness());
		}
		}
	}

	//Se encarga de buscar las figuras seleccionadas en rectangle
	private void searchingFigures(Rectangle rectangle) {
		for (Figure figure : canvasState.figures()) {
			if (figure.figureBelongsIn(rectangle)) {
				selectedFigure.add(figure);
			}
		}
	}

	private void setAlert(){
		Alert alert = new Alert(Alert.AlertType.INFORMATION);
		alert.setTitle("ATENCION");
		alert.setHeaderText("Error de seleccion");
		alert.setContentText("La seleccion debe ser dibujada de arriba a abajo y de izquierda a derecha");
		alert.showAndWait();
	}

}