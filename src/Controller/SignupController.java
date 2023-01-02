package Controller;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
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
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

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
		 	// Close the current stage
		    Stage currentStage = (Stage) signupBt.getScene().getWindow();
		    currentStage.close();
		    //Starts a new stage
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
