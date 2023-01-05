package Controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import Alerts.Alerts;
import Model.GameHistory;
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
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class ResetController implements Initializable {

	@FXML
	private Button back;
	@FXML
	private Button saveBt;
	@FXML
	private TextField username;
	@FXML
	private PasswordField password;
	@FXML
	private PasswordField renter;
	static String reuser;
	static String repass;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub

	}

	@FXML
	public void savePassword() {
		// reading the fields
		reuser = username.getText();
		String pass = password.getText();
		repass = renter.getText();
		
		// checking if all fields not empty
		if (reuser.equals(null) || reuser.isEmpty()) {
			Alerts.showAlert(AlertType.ERROR, "Sloth - Reset", "Please enter you'r username.", ButtonType.OK);
		} else if (pass.isEmpty()) {
			Alerts.showAlert(AlertType.ERROR, "Sloth - Reset", "Please enter you'r new password.", ButtonType.OK);
		} else if (repass.isEmpty()) {
			Alerts.showAlert(AlertType.ERROR, "Sloth - Reset", "Please Renter you'r new password.", ButtonType.OK);

		} else {
			// checks if the 2 fields of passwords are the same
			if (!pass.equals(repass)) { 
				Alerts.showAlert(AlertType.WARNING, "Sloth - Reset", "Password does'nt match, try again.",
						ButtonType.OK);
			} else {

				User oldUser = new User(reuser, pass);
				User newUser = userContains(oldUser);
				//update the user new password
				if (newUser != null) { 
					// newUser.setPassword(repass);
					Sysdata.getThPlayers().remove(oldUser);
					Sysdata.getThPlayers().add(newUser);
					Alerts.showAlert(AlertType.CONFIRMATION, "Sloth - Reset", "Password Reseted Successfuly",
							ButtonType.OK);
					clearning();

					try {
						Sysdata.exportUsersToJSON();
					} catch (FileNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					updateHistory(newUser);

				} else {
					Alerts.showAlert(AlertType.WARNING, "Sloth - Reset", "username doesnt found, please Sign-up",
							ButtonType.OK);
					clearning();
				}

				// checks if the user name already contains

			}
		}

	}
/*
 * updates the user object in the games history Json file
 */
	private void updateHistory(User newUser) {
		// TODO Auto-generated method stub
		if (Sysdata.getGamesHistoryList().isEmpty()) {
			try {
				Sysdata.importGameHistorysFromJSON();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			for (GameHistory g : Sysdata.getGamesHistoryList()) {
				if (g.getUser().getUsername().equals(newUser.getUsername())) {
					g.getUser().setPassword(repass);
					try {
						Sysdata.exportGamesHistoryToJSON();
					} catch (FileNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}

		}

	}
/*
 * this method checks if the user contains in the players list and update his new password
 */
	private User userContains(User u) {
		// TODO Auto-generated method stub
		// checks if the users list is empty
		if (Sysdata.getThPlayers().isEmpty()) {
			try {
				Sysdata.importUsersFromJSON();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		for (User user : Sysdata.getThPlayers()) {
			if (u.getUsername().equals(user.getUsername())) {
				System.out.println(user.getPassword());
				user.setPassword(repass);
				return user;

			}
		}

		return null;

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
			primaryStage.setTitle("Sloth - Login");
	        primaryStage.setResizable(false);
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
 * clearing all the fields after setting / un setting the password for the user.
 */
	public void clearning() {

		if (username != null) {
			username.setText("");
		}
		if (password != null) {
			password.setText("");
		}
		if (renter != null) {
			password.setText("");
		}

	}
}
