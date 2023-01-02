package Controller;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import Model.Admin;
import Model.User;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class MainMenuController implements Initializable {

	static String themeSelected; // save the selected theme
	@FXML
	private Button startBt;
	@FXML
	private Button questionBt;
	@FXML
	private Button historyBt;
	@FXML
	private ComboBox<String> theme;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		theme.getItems().addAll("Coral", "Dusk", "Wheat", "Marine", "Emerald", "Sandcastle");
		
		if(LoginController.getUser() instanceof User) {
			questionBt.setVisible(false);
		}
		
		

	}

	public static String getThemeSelected() {
		return themeSelected;
	}

	public static void setThemeSelected(String themeSelected) {
		MainMenuController.themeSelected = themeSelected;
	}

	@FXML
	public void startGame(ActionEvent event) throws IOException {
	    // Close the current stage
	    Stage currentStage = (Stage) startBt.getScene().getWindow();
	    currentStage.close();
		themeSelected = theme.getSelectionModel().getSelectedItem();
		
		if (themeSelected == null) {
		//	theme.getSelectionModel().select("Coral");
			setThemeSelected("Coral");
		}

		// TODO: handle exception
	    //Starts a new stage
		Stage primaryStage = new Stage();
		Parent root = FXMLLoader.load(getClass().getResource("/View/ChessBoard.fxml"));
		Scene scene = new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.setTitle("Sloth Chess Board");
		primaryStage.setMinHeight(800);
		primaryStage.setMinWidth(900);
		primaryStage.show();
		primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
			@Override
			public void handle(WindowEvent t) {
				Platform.exit();
				System.exit(0);
			}
		});
		FileInputStream input;
		try {
			input = new FileInputStream("./src/images/logo.png");
			Image img = new Image(input);
			primaryStage.getIcons().add(img); // icon
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

	}

	@FXML
	public void questionMang(ActionEvent event) throws IOException {
	    // Close the current stage
	    Stage currentStage = (Stage) startBt.getScene().getWindow();
	    currentStage.close();
	    //Starts a new stage
		Stage primaryStage = new Stage();
		Parent root = FXMLLoader.load(getClass().getResource("/View/QuestionMangement.fxml"));
		Scene scene = new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.setTitle("Chess");
		primaryStage.setMinHeight(800);
		primaryStage.setMinWidth(900);
		primaryStage.show();
		primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
			@Override
			public void handle(WindowEvent t) {
				Platform.exit();
				System.exit(0);
			}
		});
		FileInputStream input;
		try {
			input = new FileInputStream("./src/images/logo.png");
			Image img = new Image(input);
			primaryStage.getIcons().add(img); // icon
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	@FXML
	public void login(ActionEvent event) throws IOException {
		Stage primaryStage = new Stage();
		Parent root = FXMLLoader.load(getClass().getResource("/View/Login.fxml"));
		Scene scene = new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.setTitle("Chess");
		primaryStage.setMinHeight(800);
		primaryStage.setMinWidth(900);
		primaryStage.show();
		primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
			@Override
			public void handle(WindowEvent t) {
				Platform.exit();
				System.exit(0);
			}
		});
		FileInputStream input;
		try {
			input = new FileInputStream("./src/images/logo.png");
			Image img = new Image(input);
			primaryStage.getIcons().add(img); // icon
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
