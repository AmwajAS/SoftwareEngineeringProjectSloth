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
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class SignupController implements Initializable {

	@FXML
	private Button signupBt;
	@FXML
	private PasswordField password;
	@FXML
	private TextField username;
	@FXML
	private Button back;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub

		try {
			if (Sysdata.getThPlayers().isEmpty()) {
				Sysdata.importUsersFromJSON();
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(Sysdata.thPlayers);

	}

	/*
	 * 
	 * this function read the fields and do some checks and adds the user to the
	 * system.
	 */
	@FXML
	void signup(ActionEvent event) throws IOException {

		// reading the fields
		String uname = username.getText().toString();
		String passw = password.getText().toString();
		// checking if all fields not empty
		if (uname == null || uname.isEmpty()) {
			Alerts.showAlert(AlertType.ERROR, "Sloth - Signup", "Please enter you'r username.", ButtonType.OK);
		} else if (passw == null) {
			Alerts.showAlert(AlertType.ERROR, "Sloth - Signup", "Please enter you'r password.", ButtonType.OK);
		} else {
			User user = new User(uname, passw);
			if (Sysdata.getThPlayers().contains(user)) { //user object (username and password) already used by other user
				Alerts.showAlert(AlertType.WARNING, "Sloth - Signup",
						"This user is alreday used by other player. ", ButtonType.OK);
				clearning();
			} else if (!checkUser(user)) { //the username is already used by other user
				Alerts.showAlert(AlertType.WARNING, "Sloth - Signup",
						"This user is alreday used by other player. ", ButtonType.OK);
			} else {
				try {
					// add the user to the system
					Sysdata.getThPlayers().add(user);
					Sysdata.exportUsersToJSON();
					clearning();
					System.out.println(user);
					// Close the current stage
					Stage currentStage = (Stage) signupBt.getScene().getWindow();
					currentStage.close();
					// Starts a new stage
					Stage primaryStage = new Stage();
					Parent root = FXMLLoader.load(getClass().getResource("/View/Login.fxml"));
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

				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			clearning();
		}

	}
/*
 * this method used to make the username unique for all users.
 */
	public boolean checkUser(User user) {
		for (User user2 : Sysdata.getThPlayers()) {
			if (user.getUsername().equals(user2.getUsername())) {
				clearning();
				return false;
			}
		}
		return true;

	}

	@FXML
	public void actionOnBack(ActionEvent event) throws IOException {
		back.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent e) -> {

		});
		{
			// Close the current stage
			Stage currentStage = (Stage) back.getScene().getWindow();
			currentStage.close();
			// Starts a new stage
			Stage primaryStage = new Stage();
			Parent root = FXMLLoader.load(getClass().getResource("/View/Login.fxml"));
			Scene scene = new Scene(root);
			primaryStage.setScene(scene);
			primaryStage.setTitle("Questions Managment");
			primaryStage.show();
			primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
				@Override
				public void handle(WindowEvent t) {
					Platform.exit();
					System.exit(0);
				}
			});
		}
	}
/*
 * this function used to clear all fields after adding / not adding the user
 */
	public void clearning() {

		if (username != null) {
			username.setText("");
		}
		if (password != null) {
			password.setText("");
		}
	}
}
