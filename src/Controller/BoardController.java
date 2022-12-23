package Controller;


import java.net.URL;
import java.util.ResourceBundle;
import Model.Game;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
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

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		Game game = new Game(chessBoard, "Coral");
		doTime(game);
		doScore(game);
		first.setStyle("-fx-background-color: #b9f6ca; ");
	}


	// Countdown Timer
	private void doTime(Game g) {
		Timeline timer = new Timeline();
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
		Timeline scores = new Timeline();
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