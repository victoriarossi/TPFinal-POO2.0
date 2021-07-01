package frontend;


import backend.CanvasState;
import backend.model.Circle;
import backend.model.Ellipse;
import backend.model.Figure;
import backend.model.Line;
import backend.model.Point;
import backend.model.Rectangle;
import backend.model.Square;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class PaintPane extends BorderPane {
	CanvasState canvasState;
	Canvas canvas = new Canvas(800.0D, 600.0D);
	GraphicsContext gc;
	private static Color strokeColor;
	private static Color fillColor;
	ToggleButton selectionButton;
	ToggleButton rectangleButton;
	ToggleButton circleButton;
	ToggleButton ellipseButton;
	ToggleButton squareButton;
	ToggleButton lineButton;
	ToggleButton deleteButton;
	Slider slider;
	Label thick;
	Label color;
	ColorPicker colorPickerThick;
	ColorPicker colorPickerFill;
	Point startPoint;
	Point endPoint;
	Point selectedStartPoint;
	Point selectedEndPoint;
	List<Figure> selectedFigure;
	StatusPane statusPane;

	public PaintPane(CanvasState canvasState, StatusPane statusPane) {
		this.gc = this.canvas.getGraphicsContext2D();
		this.selectionButton = new ToggleButton("Seleccionar");
		this.rectangleButton = new ToggleButton("Rectángulo");
		this.circleButton = new ToggleButton("Círculo");
		this.ellipseButton = new ToggleButton("Elipse");
		this.squareButton = new ToggleButton("Cuadrado");
		this.lineButton = new ToggleButton("Linea");
		this.deleteButton = new ToggleButton("Borrar");
		this.slider = new Slider(1.0D, 50.0D, 1.0D);
		this.thick = new Label("Borde:");
		this.color = new Label("Relleno:");
		this.colorPickerThick = new ColorPicker(strokeColor);
		this.colorPickerFill = new ColorPicker(fillColor);
		this.selectedFigure = new ArrayList();
		this.canvasState = canvasState;
		this.statusPane = statusPane;
		ToggleButton[] toolsArr = new ToggleButton[]{this.selectionButton, this.rectangleButton, this.circleButton, this.ellipseButton, this.squareButton, this.lineButton, this.deleteButton};
		ToggleGroup tools = new ToggleGroup();
		ToggleButton[] var5 = toolsArr;
		int var6 = toolsArr.length;

		for(int var7 = 0; var7 < var6; ++var7) {
			ToggleButton tool = var5[var7];
			tool.setMinWidth(90.0D);
			tool.setToggleGroup(tools);
			tool.setCursor(Cursor.HAND);
		}

		VBox buttonsBox = new VBox(10.0D);
		buttonsBox.getChildren().addAll(toolsArr);
		this.slider.setShowTickMarks(true);
		this.slider.setShowTickLabels(true);
		this.colorPickerThick.getStyleClass().add("split-button");
		this.colorPickerFill.getStyleClass().add("split-button");
		buttonsBox.getChildren().addAll(new Node[]{this.thick, this.slider, this.colorPickerThick, this.color, this.colorPickerFill});
		buttonsBox.setPadding(new Insets(5.0D));
		buttonsBox.setStyle("-fx-background-color: #999");
		buttonsBox.setPrefWidth(100.0D);
		this.gc.setLineWidth(1.0D);
		this.canvas.setOnMousePressed((event) -> {
			this.startPoint = new Point(event.getX(), event.getY());
		});
		this.canvas.setOnMouseReleased((event) -> {
			this.endPoint = new Point(event.getX(), event.getY());
			Figure newFigure = null;
			if (!this.lineButton.isSelected() && this.endPoint.validatePoint(this.startPoint)) {
				if (this.rectangleButton.isSelected()) {
					newFigure = new Rectangle(this.startPoint, this.endPoint);
				} else if (this.circleButton.isSelected()) {
					newFigure = new Circle(this.startPoint, this.endPoint);
				} else if (this.squareButton.isSelected()) {
					newFigure = new Square(this.startPoint, this.endPoint);
				} else {
					if (!this.ellipseButton.isSelected()) {
						return;
					}

					newFigure = new Ellipse(this.startPoint, this.endPoint);
				}
			} else if (this.lineButton.isSelected()) {
				newFigure = new Line(this.startPoint, this.endPoint);
			}

			((Figure)newFigure).setFillColor(fillColor);
			canvasState.addFigure((Figure)newFigure);
			this.startPoint = null;
			this.redrawCanvas();
		});
		this.canvas.setOnMouseMoved((event) -> {
			Point eventPoint = new Point(event.getX(), event.getY());
			Iterator var3 = this.selectedFigure.iterator();

			while(var3.hasNext()) {
				Figure figure = (Figure)var3.next();
				StringBuilder label = new StringBuilder();
				this.checkingFigureBelongs(figure, label, eventPoint.toString());
			}

		});
		this.canvas.setOnMouseClicked((event) -> {
			if (this.selectionButton.isSelected()) {
				this.selectedFigure.clear();
				Rectangle selectedRectangle = new Rectangle(this.startPoint, this.endPoint);
				this.searchingFigures(selectedRectangle);
				StringBuilder label = new StringBuilder("Se seleccionó: ");
				Iterator var4 = this.selectedFigure.iterator();

				while(var4.hasNext()) {
					Figure figure = (Figure)var4.next();
					this.checkingFigureBelongs(figure, label, "Ninguna figura encontrada");
					this.redrawCanvas();
				}
			}

		});
		this.canvas.setOnMouseDragged((event) -> {
			if (this.selectionButton.isSelected()) {
				Point eventPoint = new Point(event.getX(), event.getY());
				Iterator var3 = this.selectedFigure.iterator();

				while(var3.hasNext()) {
					Figure figure = (Figure)var3.next();
					figure.moveFigure(eventPoint.getDiffX(this.startPoint), eventPoint.getDiffY(this.startPoint));
				}

				this.redrawCanvas();
			}

		});
		this.canvas.setOnMouseDragReleased((event) -> {
		});
		this.setLeft(buttonsBox);
		this.setRight(this.canvas);
		this.colorPickerThick.setOnAction((event) -> {
			Iterator var2 = this.selectedFigure.iterator();

			while(var2.hasNext()) {
				Figure figure = (Figure)var2.next();
				figure.setStrokeColor((Color)this.colorPickerThick.getValue());
			}

			this.redrawCanvas();
		});
		this.colorPickerFill.setOnAction((event) -> {
			Iterator var2 = this.selectedFigure.iterator();

			while(var2.hasNext()) {
				Figure figure = (Figure)var2.next();
				figure.setFillColor((Color)this.colorPickerFill.getValue());
			}

			this.redrawCanvas();
		});
	}

	private void checkingFigureBelongs(Figure mySelectedFigure, StringBuilder label, String elseString) {
		boolean found = false;
		Iterator var5 = this.canvasState.figures().iterator();

		while(var5.hasNext()) {
			Figure figure = (Figure)var5.next();
			if (figure == mySelectedFigure) {
				found = true;
				label.append(figure.toString());
			}
		}

		if (!found) {
			this.statusPane.updateStatus(elseString);
		} else {
			this.statusPane.updateStatus(label.toString());
		}

	}

	void redrawCanvas() {
		this.gc.clearRect(0.0D, 0.0D, this.canvas.getWidth(), this.canvas.getHeight());

		Figure figure;
		for(Iterator var1 = this.canvasState.figures().iterator(); var1.hasNext(); this.gc = figure.setStrokeAndFill(this.gc, figure.getFill(), figure.getLine())) {
			figure = (Figure)var1.next();
			if (this.selectedFigure.contains(figure)) {
				this.gc.setStroke(Color.RED);
				this.gc.setFill(fillColor);
				figure.setFillColor((Color)this.colorPickerFill.getValue());
				figure.setStrokeColor((Color)this.colorPickerThick.getValue());
			}
		}

	}

	void searchingFigures(Rectangle rectangle) {
		Iterator var2 = this.canvasState.figures().iterator();

		while(var2.hasNext()) {
			Figure figure = (Figure)var2.next();
			if (figure.figureBelongsIn(rectangle)) {
				this.selectedFigure.add(figure);
			}
		}

	}

	static {
		strokeColor = Color.BLACK;
		fillColor = Color.YELLOW;
	}
}
