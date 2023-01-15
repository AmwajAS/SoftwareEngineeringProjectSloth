package Controller;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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
import javafx.scene.control.ListView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;

public class GamesHistoryCtrl implements Initializable {

	@FXML
	private Button back;
	@FXML
	private Button myHistory;
	@FXML
	private Button allHistory;
	@FXML
	private ListView<String> gamesList;
	@FXML
	private ListView<GameHistory> melist;
	@FXML
	private ImageView first;
	@FXML
	private ImageView second;
	@FXML
	private ImageView third;
	public  ArrayList<GameHistory> sortedList = new ArrayList<>();
	@FXML
	private Label hightScoreLabel;
	@FXML
	private Pane meall;
	@FXML
	private Pane me;


	private User user = LoginController.getUser();

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		// showing the specific player games history
		meall.setVisible(false);
		me.setVisible(true);
		for (GameHistory g : Sysdata.getGamesHistoryList()) {
			if (g.getUser().getUsername().equals(user.getUsername())) {
				melist.getItems().add(g);

			}

		}
		// if the user have no games history yet
		if (melist.getItems().isEmpty()) {
			Alerts.showAlert(AlertType.INFORMATION, "Games History",
					"You have no games history to show! Go AND PLAY NOW ", ButtonType.OK);
		}



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
		}
	}

	/*
	 * *this function show all players games history
	 */
	@FXML
	public void showAllHistory() {
		
		meall.setVisible(true);
		me.setVisible(false);
		first.setVisible(true);
		second.setVisible(true); // set Visible the 1st, 2nd, 3rd places.
		third.setVisible(true);
		//hightScoreLabel.setVisible(true);

		if (!gamesList.getItems().isEmpty()) {
			gamesList.getItems().clear();
			}
		
			// sort the games based on the highest score for all players.
			Collections.sort(Sysdata.getThPlayers(), new Comparator<User>() {
				@Override
				public int compare(User g1, User g2) {
					return Integer.compare(g2.getHighScore(), g1.getHighScore());
				}
			});
			
			
			for (User g : Sysdata.getThPlayers()) {
			      	gamesList.getItems().add(g.getUsername() + "                                                                     " + g.getHighScore());
				
			}
			gamesList.getSelectionModel().select(user.getUsername() + "                                                                     " + user.getHighScore());
	}

	/*
	 * this function show the specific player games history
	 */
	@FXML
	public void showMyHistory() {
		meall.setVisible(false);
		me.setVisible(true);
		first.setVisible(false); // Invisible the 1st, 2nd, 3rd places.
		second.setVisible(false);
		third.setVisible(false);

		if (!melist.getItems().isEmpty()) {
			melist.getItems().clear(); // clear the list from the old data and show a new data
			for (GameHistory g : Sysdata.getGamesHistoryList()) {
				if (g.getUser().getUsername().equals(user.getUsername())) {
					melist.getItems().add(g);
				}
			}
		}

	}

}
