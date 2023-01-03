package Controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
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
import javafx.print.Collation;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import sun.awt.www.content.image.gif;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;

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

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub

		User user = LoginController.getUser();
		showData();

		for (GameHistory g : Sysdata.getGamesHistoryList()) {
			if (g.getUser().equals(user)) {
				gamesList.getItems().add(g);
			}
		}
		first.setVisible(false);
		second.setVisible(false);
		third.setVisible(false);

	}

	public void showData() {
		try {
			System.out.println("Bug Founded");

			Sysdata.importGameHistorysFromJSON();
			Sysdata.getThPlayers();
			System.out.println(Sysdata.getGamesHistoryList());
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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

	@FXML
	public void showAllHistory() {

		

		first.setVisible(true);
		second.setVisible(true);
		third.setVisible(true);

		if (!gamesList.getItems().isEmpty()) {
			gamesList.getItems().clear();
			//sortHigthScores();

				gamesList.getItems().addAll(Sysdata.getGamesHistoryList());
			}
		}

	

	@FXML
	public void showMyHistory() {
		first.setVisible(false);
		second.setVisible(false);
		third.setVisible(false);

		User user = LoginController.getUser();

		if (!gamesList.getItems().isEmpty()) {
			gamesList.getItems().clear();
			for (GameHistory g : Sysdata.getGamesHistoryList()) {
				if (g.getUser().equals(user)) {
					gamesList.getItems().add(g);
				}
			}
		}

	}

	public void sortHigthScores() {

		Sysdata.getThPlayers();
		sortedList.addAll(Sysdata.getGamesHistoryList());

//		Collections.sort(sortedList, new Comparator<GameHistory>() {
//			@Override
//			public int compare(GameHistory g1, GameHistory g2) {
//				return g2.getUser().getHighScore().compareTo(g1.getUser().getHighScore());
//			}
//		});
	}

}
