package Controller;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import com.fasterxml.jackson.databind.ser.std.UUIDSerializer;

import Alerts.Alerts;
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
import javafx.scene.control.ButtonType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class LoginController implements Initializable {

	private static User user; // we save the current user/player

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
	
	public static final Admin admin = new Admin("admin", "123");


	

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub

	}

	@FXML
	void login(ActionEvent event) throws IOException {

		String name = username.getText();
		String pass = password.getText();

		if (name == null || name.isEmpty()) {
			Alerts.showAlert(AlertType.ERROR, "Sloth - LogIn", "Please enter you'r username.", ButtonType.OK);
		} else if (pass == null) {
			Alerts.showAlert(AlertType.ERROR, "Sloth - LogIn", "Please enter you'r password.", ButtonType.OK);
		} else {
			 if(isUser(name, pass)) {

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
				}else if (isAdmin(name, pass)) {
					Stage primaryStage = new Stage();
					Parent root = FXMLLoader.load(getClass().getResource("/View/MainMenu.fxml"));
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
			 else {
					Alerts.showAlert(AlertType.ERROR, "Sloth - LogIn", "No User Founds", ButtonType.OK);
				}
			}
		

	}

	@FXML
	public void signup(ActionEvent event) throws IOException {
		Stage primaryStage = new Stage();
		Parent root = FXMLLoader.load(getClass().getResource("/View/Signup.fxml"));
		Scene scene = new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.setTitle("Chess");
		primaryStage.setMinHeight(513);
		primaryStage.setMinWidth(548);
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

	private boolean isAdmin(String uname, String pass) {
		
		Admin isAdmin = new Admin(uname, pass);

		if(admin.equals(isAdmin)) {
			return true;
		}

		return false;
	}

	private boolean isUser(String name, String pass) {

		// if(!Sysdata.getThPlayers().isEmpty())
		try {
			Sysdata.importUsersFromJSON();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(Sysdata.getThPlayers());
		for (User u : Sysdata.getThPlayers()) {
			if (u.getUsername().equals(name) && u.getPassword().equals(pass)) {
				this.user = u;
				return true;
			}
		}

		return false;

	}

	public static User getUser() {
		return user;
	}

	public static void setUser(User user) {
		LoginController.user = user;
	}

	public static Admin getAdmin() {
		return admin;
	}

}
