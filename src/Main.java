import java.awt.*;
import java.awt.event.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import javax.swing.*;
import Model.Game;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import Controller.Sysdata;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;


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
//		res = Restaurant.getInstance();
		launch(args);
	}



}
