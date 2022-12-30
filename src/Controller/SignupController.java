package Controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

import Alerts.Alerts;
import Model.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;

public class SignupController implements Initializable {

	@FXML
	private Button signupBt;
	@FXML
	private PasswordField password;
	@FXML
	private TextField username;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub

		try {
			Sysdata.importUsersFromJSON();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    System.out.println(Sysdata.thPlayers);


	}

	@FXML
	void signup(ActionEvent event) throws IOException {

		String uname = username.getText().toString();
		String passw = password.getText().toString();

		if (uname == null || uname.isEmpty()) {
			Alerts.showAlert(AlertType.ERROR, "Sloth - Signup", "Please enter you'r username.",
					ButtonType.OK);
		} else if (passw == null) {
			Alerts.showAlert(AlertType.ERROR, "Sloth - Signup", "Please enter you'r password.",
					ButtonType.OK);
		} else {
			// add
			User user = new User(uname, passw);
			try {
			Sysdata.getThPlayers().add(user);
			Sysdata.exportUsersToJSON();
			clearning();
			System.out.println(user);

			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}

	public void clearning() {

		if (username != null) {
			username.setText("");
		}
		if (password != null) {
			password.setText("");
		}
	}
}
