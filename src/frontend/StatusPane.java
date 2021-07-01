package frontend;


import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;

public class StatusPane extends BorderPane {
	private final Label statusLabel;

	public StatusPane() {
		this.setStyle("-fx-background-color: #4EBCF8");
		this.statusLabel = new Label("Paint 1.0");
		this.statusLabel.setAlignment(Pos.CENTER);
		this.statusLabel.setStyle("-fx-font-size: 16");
		this.setCenter(this.statusLabel);
	}

	public void updateStatus(String text) {
		this.statusLabel.setText(text);
	}
}
