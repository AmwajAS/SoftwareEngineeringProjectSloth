
import java.io.IOException;

import Controller.Sysdata;
import Model.User;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class Main extends Application {



	public static Stage primaryStage = null;
	@Override
	public void start(Stage stage) throws IOException {
		primaryStage = stage;
		Parent root =FXMLLoader.load(getClass().getResource("/View/MainMenu.fxml"));
		Scene scene = new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.setTitle("Chess");
		primaryStage.show();

	}

	public static void main(String[] args)throws IOException, ClassNotFoundException {
		launch(args);
	}



}
