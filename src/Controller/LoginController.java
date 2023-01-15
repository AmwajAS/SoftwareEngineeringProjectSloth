package Controller;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import Alerts.Alerts;
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
import javafx.scene.control.ButtonType;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class LoginController implements Initializable {

	private static User user; // we save the current user/player

	@FXML
	private AnchorPane loginframe;
	@FXML
	private Button signupBt;
	@FXML
	private Button loginBt;
	@FXML
	private PasswordField password;
	@FXML
	private TextField username;
	@FXML
	private Hyperlink resetBt;
	@FXML
	private MediaView mv;

	public static javafx.scene.media.MediaPlayer play= null;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub

	}

	@FXML
	void login(ActionEvent event) throws IOException {

		// reading the fields
		String name = username.getText();
		String pass = password.getText();
		// checking if all fields not empty
		if (name == null || name.isEmpty()) {
			Alerts.showAlert(AlertType.ERROR, "Sloth - LogIn", "Please enter you'r username.", ButtonType.OK);
		} else if (pass == null) {
			Alerts.showAlert(AlertType.ERROR, "Sloth - LogIn", "Please enter you'r password.", ButtonType.OK);
		} else {
			// checks if the user are user / player
			if (isUser(name, pass)) {
				// Close the current stage
				Stage currentStage = (Stage) loginframe.getScene().getWindow();
				currentStage.close();
				// Starts a new stage
				Stage primaryStage = new Stage();
				Parent root = FXMLLoader.load(getClass().getResource("/View/MainMenu.fxml"));
				Scene scene = new Scene(root);
				primaryStage.setScene(scene);
				primaryStage.setTitle("Sloth - Main Menu");
				primaryStage.setResizable(false);
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
			} else if (isAdmin(name, pass)) {// checks if the user is an admin
				Stage currentStage = (Stage) loginframe.getScene().getWindow();
				currentStage.close();
				Stage primaryStage = new Stage();
				Parent root = FXMLLoader.load(getClass().getResource("/View/MainMenu.fxml"));
				Scene scene = new Scene(root);
				primaryStage.setScene(scene);
				primaryStage.setTitle("Sloth - Main Menu");
				primaryStage.setResizable(false);
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
			} else {
				Alerts.showAlert(AlertType.ERROR, "Sloth - LogIn", "No User Founds", ButtonType.OK);
				clearning();
			}
		}

	}

	@FXML
	public void signup(ActionEvent event) throws IOException {
		// Close the current stage
		Stage currentStage = (Stage) loginframe.getScene().getWindow();
		currentStage.close();
		// Starts a new stage
		Stage primaryStage = new Stage();
		Parent root = FXMLLoader.load(getClass().getResource("/View/Signup.fxml"));
		Scene scene = new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.setTitle("Chess");
		primaryStage.setResizable(false);
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
	public void resetPassword(ActionEvent event) throws IOException {
		// Close the current stage
		Stage currentStage = (Stage) loginframe.getScene().getWindow();
		currentStage.close();
		// Starts a new stage
		Stage primaryStage = new Stage();
		Parent root = FXMLLoader.load(getClass().getResource("/View/Reset.fxml"));
		Scene scene = new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.setTitle("Chess");
		primaryStage.setResizable(false);
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

	/*
	 * this method to authorize if the admin is logging in.
	 */
	private boolean isAdmin(String name, String pass) {

		for (User u : Sysdata.getThPlayers()) {
			if (u.getUsername().equals(name) && u.getPassword().equals(pass) && (u.isAdmin())) {
				this.user = u;
				return true;
			}

		}

		return false;

	}

	/*
	 * search the player, by checking if the user name and the password match for
	 * this user.
	 */
	private boolean isUser(String name, String pass) {

		for (User u : Sysdata.getThPlayers()) {
			if (u.isAdmin() == false) {
				if (u.getUsername().equals(name) && u.getPassword().equals(pass) && (!u.isAdmin())) {
					this.user = u;
					return true;
				}
			}

		}

		return false;

	}

	/*
	 * this function used to clear all fields after adding / not adding / setting
	 * the user
	 */
	public void clearning() {

		if (username != null) {
			username.setText("");
		}
		if (password != null) {
			password.setText("");
		}
	}

	public static User getUser() {
		return user;
	}

	public static void setUser(User user) {
		LoginController.user = user;
	}

}
