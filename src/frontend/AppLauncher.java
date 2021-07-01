package frontend;


import backend.CanvasState;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class AppLauncher extends Application {


	public static void main(String[] args) {
		launch(args);
	}

	public void start(Stage primaryStage) {
		CanvasState canvasState = new CanvasState();
		MainFrame frame = new MainFrame(canvasState);
		Scene scene = new Scene(frame);
		primaryStage.setResizable(false);
		primaryStage.setScene(scene);
		primaryStage.show();
		primaryStage.setOnCloseRequest((event) -> {
			System.exit(0);
		});
	}
}
