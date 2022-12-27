package Controller;

import java.awt.Label;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;
import javax.swing.Timer;
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
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.util.Duration;

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

	// var to the doTime() method
	private final Integer startTime = 60;
	private Integer seconds = startTime;
	private int level=1;
	public Timeline scores;
	public Timeline timer;
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
			
		});
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
					timer.stop();
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