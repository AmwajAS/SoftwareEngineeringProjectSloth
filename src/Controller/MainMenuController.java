package Controller;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class MainMenuController implements Initializable {

	@FXML
	private Button startBt;
	@FXML
	private Button questionBt;

	@FXML AnchorPane mainpage;
	@FXML Label textLabel;
	@FXML Button clickButton;
	@FXML ImageView homeimg;
	@FXML PasswordField pass;
	@FXML TextField userid;
	@FXML ImageView inimg;
	@FXML Tooltip passtool;
	@FXML Tooltip usertool;
	@FXML Label joinus;
	@FXML Button signup;
	@FXML Button vol;
	@FXML Button loginBt;


	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub

	}

	/*@FXML
	public void startGame(ActionEvent event) throws IOException {
		String n = userid.getText().toString();
		String p = pass.getText().toString();
		if(n.equals("arwad") && p.equals("1234") ){ //Check if the manager logging in
			Stage primaryStage = new Stage();
			try {

				Parent root = FXMLLoader.load(getClass().getResource("/View/Board.fxml"));
				Scene scene = new Scene(root);
				primaryStage.setScene(scene);
				primaryStage.setTitle("Chess");
				primaryStage.show();
				primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
					@Override
					public void handle(WindowEvent t) {
						Platform.exit();
						System.exit(0);
					}
				});  
			}catch (IOException e) {
				e.printStackTrace();
			}
		}
	}*/


	@FXML
	public void startGame(ActionEvent event) throws IOException {
		Stage primaryStage = new Stage();
		Parent root = FXMLLoader.load(getClass().getResource("/View/ChessBoard.fxml"));
		Scene scene = new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.setTitle("Chess");
		primaryStage.setMinHeight(700);
		primaryStage.setMinWidth(700);
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
		Stage primaryStage = new Stage();
		Parent root = FXMLLoader.load(getClass().getResource("/View/QuestionsManagement.fxml"));
		Scene scene = new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.setTitle("Chess");
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
