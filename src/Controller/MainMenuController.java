package Controller;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import Model.User;
import javafx.animation.RotateTransition;
import javafx.animation.TranslateTransition;
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
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Duration;

public class MainMenuController implements Initializable {

	static String themeSelected; // save the selected theme
	@FXML
	private Button startBt;
	@FXML
	private Button questionBt;
	@FXML
	private Button historyBt;
	@FXML
	private Button logoutBt;
	@FXML
	private ComboBox<String> theme;
	@FXML
	private ImageView prizeImg;
	@FXML
	private Text helloUser;
	@FXML
	private Button ask;
	@FXML
	private Button ask1;
	@FXML
	private Pane helpPane;
	@FXML
	private ImageView vector;
	@FXML
	private ImageView bg;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		theme.getItems().addAll("Coral", "Dusk", "Wheat", "Marine", "Emerald", "Sandcastle");

		FileInputStream input;

		// theme.getSelectionModel().select("Coral");
		try {
			input = new FileInputStream("./src/images/dvector.png");
			Image sand = new Image(input);
			vector.setImage(sand);
			RotateTransition rotateTransition = new RotateTransition();
			rotateTransition.setNode(vector);
			rotateTransition.setFromAngle(0);
			rotateTransition.setToAngle(360);
			rotateTransition.setDuration(Duration.millis(10000));
			rotateTransition.setCycleCount(TranslateTransition.INDEFINITE);
			rotateTransition.play();

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		FileInputStream input2;
		try {
			input2 = new FileInputStream("./src/images/dbgg.png");
			Image img = new Image(input2);
			bg.setImage(img);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// Invisible the manage question button for players
		
		if (!LoginController.getUser().isAdmin()) {
			questionBt.setVisible(false);
		} else {
			questionBt.setVisible(true);
		}

		// Invisible the help button
		helpPane.setVisible(false);
		ask1.setVisible(false);

		if (LoginController.getUser().getUsername() == null) {
			System.out.println("no user!!error in import users");
		} else {
			// Image image = new Image("/images/prize.png");
			int highScore = 0;
			for (User user : Sysdata.getThPlayers()) {
				if (user.getUsername().equals(LoginController.getUser().getUsername())) {
					highScore = user.getHighScore();
					if (highScore >= 200) {
						prizeImg.setVisible(true);
					} else if (highScore < 200) {
						prizeImg.setVisible(false);
					}
				}
			}
			helloUser.setText("Hey  " + LoginController.getUser().getUsername() + " ,Welcome \n Your High Score Is : "
					+ highScore); // set the user name on the board
		}

	}

	public static String getThemeSelected() {
		return themeSelected;
	}

	public static void setThemeSelected(String themeSelected) {
		MainMenuController.themeSelected = themeSelected;
	}

	@FXML
	public void logout(ActionEvent event) throws IOException {
		// Close the current stage
		Stage currentStage = (Stage) startBt.getScene().getWindow();
		currentStage.close();
		themeSelected = theme.getSelectionModel().getSelectedItem();
		// selecting a default theme
		if (themeSelected == null) {
			setThemeSelected("Coral");
		}

		// TODO: handle exception
		// Starts a new stage
		Stage primaryStage = new Stage();
		Parent root = FXMLLoader.load(getClass().getResource("/View/Login.fxml"));
		Scene scene = new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.setTitle("Sloth Chess Board");
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
	public void startGame(ActionEvent event) throws IOException {
		// Close the current stage
		Stage currentStage = (Stage) startBt.getScene().getWindow();
		currentStage.close();
		themeSelected = theme.getSelectionModel().getSelectedItem();

		if (themeSelected == null) {
			// theme.getSelectionModel().select("Coral");
			setThemeSelected("Coral");
		}

		// TODO: handle exception
		// Starts a new stage
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
		// Starts a new stage
		Stage primaryStage = new Stage();
		Parent root = FXMLLoader.load(getClass().getResource("/View/QuestionMangement.fxml"));
		Scene scene = new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.setTitle("Sloth - Question Mangement");
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
	public void history(ActionEvent event) throws IOException {
		// Close the current stage
		Stage currentStage = (Stage) startBt.getScene().getWindow();
		currentStage.close();
		// Starts a new stage
		Stage primaryStage = new Stage();
		Parent root = FXMLLoader.load(getClass().getResource("/View/HistoryGames.fxml"));
		Scene scene = new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.setTitle("Sloth - Games History");
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
	void vectorTheme() throws FileNotFoundException {
		themeSelected = theme.getSelectionModel().getSelectedItem();
		FileInputStream input;

		if (themeSelected == null) {
			// theme.getSelectionModel().select("Coral");
			input = new FileInputStream("./src/images/vector5.png");
			Image sand = new Image(input);
			vector.setImage(sand);
		} else if (themeSelected == "Sandcastle") {
			input = new FileInputStream("./src/images/vector.png");
			Image sand = new Image(input);
			vector.setImage(sand);
			FileInputStream input2;
			try {
				input2 = new FileInputStream("./src/images/bgg.png");
				Image img = new Image(input2);
				bg.setImage(img);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		} else if (themeSelected == "Emerald") {
			input = new FileInputStream("./src/images/vector1.png");
			Image sand = new Image(input);
			vector.setImage(sand);
			FileInputStream input2;
			try {
				input2 = new FileInputStream("./src/images/1bg.png");
				Image img = new Image(input2);
				bg.setImage(img);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else if (themeSelected == "Marine") {
			input = new FileInputStream("./src/images/vector2.png");
			Image sand = new Image(input);
			vector.setImage(sand);
			FileInputStream input2;
			try {
				input2 = new FileInputStream("./src/images/2bg.png");
				Image img = new Image(input2);
				bg.setImage(img);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else if (themeSelected == "Wheat") {
			input = new FileInputStream("./src/images/vector3.png");
			Image sand = new Image(input);
			vector.setImage(sand);
			FileInputStream input2;
			try {
				input2 = new FileInputStream("./src/images/3bg.png");
				Image img = new Image(input2);
				bg.setImage(img);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else if (themeSelected == "Dusk") {
			input = new FileInputStream("./src/images/vector4.png");
			Image sand = new Image(input);
			vector.setImage(sand);
			FileInputStream input2;
			try {
				input2 = new FileInputStream("./src/images/4bg.png");
				Image img = new Image(input2);
				bg.setImage(img);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} else {
			input = new FileInputStream("./src/images/vector5.png");
			Image sand = new Image(input);
			vector.setImage(sand);
			FileInputStream input2;
			try {
				input2 = new FileInputStream("./src/images/5bg.png");
				Image img = new Image(input2);
				bg.setImage(img);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	@FXML
	void onText(ActionEvent event) {

		helpPane.setVisible(true);
		ask1.setVisible(true);
		ask.setVisible(true);

	}

	@FXML
	void onText2(ActionEvent event) {
		helpPane.setVisible(false);
		ask.setVisible(true);
		ask1.setVisible(false);
	}

}
