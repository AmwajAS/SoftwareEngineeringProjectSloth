package Controller;

import java.io.IOException;
import java.net.URL;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.ResourceBundle;
import Model.GameHistory;
import Model.User;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class GamesHistoryCtrl implements Initializable {

	@FXML
	private Button back;
	@FXML
	private Button myHistory;
	@FXML
	private Button allHistory;
	@FXML
	private ListView<GameHistory> gamesList;
	@FXML
	private ImageView first;
	@FXML
	private ImageView second;
	@FXML
	private ImageView third;
	public static List<GameHistory> sortedList;
	@FXML
	private Label hightScoreLabel;
	private User user = LoginController.getUser();

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
        //showing the specific player games history
		for (GameHistory g : Sysdata.getGamesHistoryList()) {
			if (g.getUser().getUsername().equals(user.getUsername())) {
				gamesList.getItems().add(g);
			}
		}
		// Invisible the 1st, 2nd, 3rd places.
		first.setVisible(false);  
		second.setVisible(false);
		third.setVisible(false);

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
	 * *this function show all players games history
	 */
	@FXML
	public void showAllHistory() {

		first.setVisible(true);
		second.setVisible(true); // set Visible the 1st, 2nd, 3rd places.
		third.setVisible(true);
		hightScoreLabel.setVisible(true);

		if (!gamesList.getItems().isEmpty()) {
			gamesList.getItems().clear();
            //sort the games based on the highest score for all players.
			Collections.sort(Sysdata.getGamesHistoryList(), new Comparator<GameHistory>() {
				@Override
				public int compare(GameHistory g1, GameHistory g2) {
					return Integer.compare(g2.getUser().getHighScore(), g1.getUser().getHighScore());
				}
			});

			gamesList.getItems().addAll(Sysdata.getGamesHistoryList());
		}
	}

	/*
	 * this function show the specific player games history
	 */
	@FXML
	public void showMyHistory() {
		first.setVisible(false); // Invisible the 1st, 2nd, 3rd places.
		second.setVisible(false);
		third.setVisible(false);

		if (!gamesList.getItems().isEmpty()) {
			gamesList.getItems().clear(); // clear the list from the old data and show a new data
			for (GameHistory g : Sysdata.getGamesHistoryList()) {
				if (g.getUser().getUsername().equals(user.getUsername())) {
					gamesList.getItems().add(g);
				}
			}
		}

	}

}
