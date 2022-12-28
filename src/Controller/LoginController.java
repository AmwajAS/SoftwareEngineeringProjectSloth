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
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class LoginController implements Initializable {

	private static User user;

	@FXML
	private BorderPane loginframe;
	@FXML
	private Button signupBt;
	@FXML
	private Button loginBt;
	@FXML
	private PasswordField password;
	@FXML
	private TextField username;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub

//	Image image = new Image(new File("./src/images/game.gif").toURI().toString());
//		ImageView imageView = new ImageView(image);

	}

	@FXML
	void login(ActionEvent event) throws IOException {
		String uname = username.getText().toString();
		String passw = password.getText().toString();
		if (uname == null || uname.isEmpty()) {
			Alerts.showAlert(AlertType.ERROR, "Login ", "The Username is Empty, Please Enter your username!",
					ButtonType.CANCEL);

		} else if (passw == null || passw.isEmpty()) {
			Alerts.showAlert(AlertType.ERROR, "Login Scene", "The Password is Empty, Please Enter your username!",
					ButtonType.CANCEL);

		} else if (isAdmin(uname, passw)) {
			Stage primaryStage = new Stage();
			Parent root = FXMLLoader.load(getClass().getResource("Admin.fxml"));
			Scene scene = new Scene(root);
			primaryStage.setScene(scene);
			primaryStage.setTitle("Resturant");
			primaryStage.show();
		}

		else if (isUser(uname, passw)) {
			Stage primaryStage = new Stage();
			Parent root = FXMLLoader.load(getClass().getResource("/View/MainMenu.fxml"));
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
		} else
			Alerts.showAlert(AlertType.ERROR, "Login ",
					"Invalid Values - No user account found, Please Enter valid username & password!",
					ButtonType.CANCEL);
	}

	private boolean isAdmin(String uname, String pass) {

		return false;
	}

	private boolean isUser(String uname, String pass) {

		if (uname.equals("Amwaj") && pass.equals("123")) {
			this.user = new User(uname, pass);
			
			return true;

		} else {
			return false;

		}
	}

	public static User getUser() {
		return user;
	}

	public static void setUser(User user) {
		LoginController.user = user;
	}

}
