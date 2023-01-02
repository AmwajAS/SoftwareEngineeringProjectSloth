package Controller;

import java.awt.Label;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;
import javax.swing.Timer;
import Alerts.Alerts;
import Model.Board;
import Model.Game;
import Model.GameHistory;
import Model.User;
import javafx.scene.control.Alert;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Duration;
import java.awt.Color;

public class BoardController implements Initializable {
	@FXML
	private Text time;
	@FXML
	private Text score;
	@FXML
	private Button first;
	@FXML
	private Button second;
	@FXML
	private Button third;
	@FXML
	private Button forth;
	@FXML
	private Text playerName;
	@FXML
	private GridPane chessBoard;

	@FXML
	private Text totalScoreText;

	@FXML
	private Text name;

	@FXML
	private Text newScore;

	// var to the doTime() method
	private final Integer startTime = 60;
	private Integer seconds = startTime;
	private int level=1;
	public Timeline scores;
	public Timeline timer;

	//var 
	public static int totalScore=0;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		try {
			Sysdata.importGameHistorysFromJSON();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		if(LoginController.getUser()==null) {
			System.out.println("no user!!error in import users");
			playerName.setText("Player Name: Error! ");
		}else{ 
			playerName.setText("Player: " +LoginController.getUser().getUsername());   //set the user name on the board

		}
		scores=new Timeline();
		timer = new Timeline();
		// TODO Auto-generated method stub
		Game game = new Game(chessBoard, MainMenuController.getThemeSelected(),level);
		doTime(game);
		doScore(game);
		first.setStyle("-fx-background-color: #b9f6ca; ");

		first.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				// TODO Auto-generated method stub
				scores.stop();
				timer.stop();
				seconds=60;
				game.setScore(0);
				level = 1;
				initialize(location,resources);
			}

		});
		/*
		second.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				// TODO Auto-generated method stub
				scores.stop();
				timer.stop();
				seconds=60;
				game.setScore(0);
				level = 2;
				initialize(location,resources);
			}

		});
		third.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				// TODO Auto-generated method stub
				scores.stop();
				timer.stop();
				seconds=60;
				game.setScore(0);
				level = 3;
				game.stopTimer();
				initialize(location,resources);
			}

		});
		forth.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				// TODO Auto-generated method stub
				scores.stop();
				timer.stop();
				seconds=60;
				game.setScore(0);
				level = 4;
				game.stopTimer();
				initialize(location,resources);
			}

		}); */
	}

	/*if(LoginController.getUser().getHighScore()<totalScore) {
	editHighScore(LoginController.getUser(), totalScore);
	Alerts.showAlert(AlertType.INFORMATION, "HighScoreIsUpdated!", 
			"You'r new high score is"+LoginController.getUser().getHighScore(), ButtonType.OK);
}
System.out.println(LoginController.getUser().getHighScore());*/
	/*public void editHighScore(User u, int newHS){
		User newU= new User(u.getUsername(),u.getPassword());
		newU.setHighScore(newHS);
		try {
			// Sysdata.importQuestionsFromJSON();
			Sysdata.importUsersFromJSON().remove(u);
			Sysdata.getThPlayers().add(newU);
			Sysdata.exportUsersToJSON();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}*/

	//Level up function initialize a new game by the new level 
	//It get the level and also the score in order to save the higher score 
	public void levelUp(int currentlevel) throws IOException{

		if(currentlevel==2) {
			second.setStyle("-fx-background-color: #89efa5; ");
			scores.stop();
			timer.stop();
			timer = new Timeline();
			seconds=60;
			Game game = new Game(chessBoard, MainMenuController.getThemeSelected(),currentlevel);	
			game.setScore(1);
			game.stopTimer();
			doTime(game);
			doScore(game);
		}else if(currentlevel==3) {
			third.setStyle("-fx-background-color: #78e495; ");
			scores.stop();
			timer.stop();
			timer = new Timeline();
			seconds=60;
			Game game = new Game(chessBoard, MainMenuController.getThemeSelected(),currentlevel);	
			game.setScore(1);
			game.stopTimer();
			doTime(game);
			doScore(game);
		}else if(currentlevel==4) {
			forth.setStyle("-fx-background-color: #4da865; ");
			scores.stop();
			timer.stop();

			GameHistory historyGame= new GameHistory(currentlevel, LoginController.getUser(), totalScore);
			Sysdata.gamesHistoryList.add(historyGame);
			Sysdata.exportGamesHistoryToJSON();

			System.out.println(historyGame.toString());

			Stage primaryStage = new Stage();
			try {
				Parent root = FXMLLoader.load(getClass().getResource("/View/YouWinWindow.fxml"));
				Scene scene = new Scene(root);
				primaryStage.setScene(scene);
				primaryStage.setTitle("Sloth Chess Board");
				primaryStage.setMinHeight(400);
				primaryStage.setMinWidth(500);
				primaryStage.show();
				primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
					@Override
					public void handle(WindowEvent t) {
						Platform.exit();
						System.exit(0);
					}
				});
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	// Countdown Timer
	private void doTime(Game g) {
		timer.setCycleCount(Timeline.INDEFINITE);
		if (timer != null) {
			timer.stop();
		}
		KeyFrame frame = new KeyFrame(Duration.seconds(1), new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				seconds--;
				time.setText("RemaingTime: 00:" + seconds.toString());
				if (seconds <= 0) {
					if(level<5 && g.getScore()>=4){ // must 15 !! 4 for tests 
						Alerts.showAlert(AlertType.INFORMATION, "Level Up!", "Congrats Level UP!!", ButtonType.OK);
						timer.stop();
						level++;
						totalScore=totalScore+g.getScore();
						System.out.println(totalScore);
						try {
							levelUp(level);
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}else {
						
						totalScore=totalScore+g.getScore();
						GameHistory historyGame= new GameHistory(level, LoginController.getUser(), totalScore);
						System.out.println(historyGame.toString());

						try {
							Sysdata.gamesHistoryList.add(historyGame);
							Sysdata.exportGamesHistoryToJSON();
						} catch (FileNotFoundException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
						
						//System.out.println(historyGame.toString());
						Alerts.showAlert(AlertType.WARNING, "Game Over!", "Time is out please try again.", ButtonType.OK);
						timer.stop();
					}
				} 
			}
		});
		timer.getKeyFrames().add(frame);
		timer.playFromStart();
	}

	//Score calculator
	private void doScore(Game g) {
		scores = new Timeline();
		scores.setCycleCount(Timeline.INDEFINITE);
		if (scores != null) {
			scores.stop();
		}
		KeyFrame frame = new KeyFrame(Duration.seconds(0.1), new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				score.setText("\n\n Score : "+g.getScore());
				if (seconds <= 0) {
					scores.stop();
				}
			}
		});
		scores.getKeyFrames().add(frame);
		scores.playFromStart();
	}

}



/*	Alerts.showAlert(AlertType.INFORMATION, "Game Completed!", "congratulations, you finished the last level!!", ButtonType.OK);
	scores.stop();
	timer.stop();
	totalScoreText.setText("-->" +currentScore);

	Stage primaryStage = new Stage();
	try {
		Parent root = FXMLLoader.load(getClass().getResource("/View/TotalScore.fxml"));
		Scene scene = new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.setTitle("Total Score of the game!");
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
	}catch (IOException e) {
		e.printStackTrace();
	}
 */
