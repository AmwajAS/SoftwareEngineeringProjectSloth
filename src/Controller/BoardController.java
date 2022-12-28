package Controller;

import java.awt.Label;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;
import javax.swing.Timer;

import Alerts.Alerts;
import Model.Board;
import Model.Game;
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



	// var to the doTime() method
	private final Integer startTime = 60;
	private Integer seconds = startTime;
	private int level=1;
	public Timeline scores;
	public Timeline timer;
	//var to save the higher score
	public int highScore=0;


	@Override
	public void initialize(URL location, ResourceBundle resources) {
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

	//Level up function initialize a new game by the new level 
	//It get the level and also the score in order to save the higher score 
	public void levelUp(int currentlevel, int currentScore){
		int totalScore=0;
		if(currentScore>=totalScore) {
			totalScore=totalScore+currentScore;
		}

		if(currentlevel==2) {
			second.setStyle("-fx-background-color: #89efa5; ");
		}else if(currentlevel==3) {
			third.setStyle("-fx-background-color: #78e495; ");
		}else if(currentlevel==4) {
			forth.setStyle("-fx-background-color: #4da865; ");
		}else if(currentlevel==5) {
		}

		scores.stop();
		timer.stop();
		timer = new Timeline();
		seconds=60;
		Game game = new Game(chessBoard, MainMenuController.getThemeSelected(),currentlevel);	
		game.setScore(1);
		game.stopTimer();
		doTime(game);
		doScore(game);

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
					if(g.getScore()>=4) {  // must 15 !! 4 for tests
						Alerts.showAlert(AlertType.INFORMATION, "Level Up!", "Congrats Level UP!!", ButtonType.OK);
						timer.stop();
						level++;
						levelUp(level, g.getScore());
					}else {
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