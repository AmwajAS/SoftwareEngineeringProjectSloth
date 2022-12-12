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
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.util.Duration;

public class BoardController implements Initializable {

	@FXML
	private Text time;
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

	// var to the doTime() method
	private final Integer startTime = 60;
	private Integer seconds = startTime;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		doTime();
		Game game = new Game(chessBoard, "Coral");
		first.setStyle("-fx-background-color: #b9f6ca; ");


	}

		//Countdown Timer
		private void doTime() {
		Timeline timer=new Timeline();
		timer.setCycleCount(Timeline.INDEFINITE);
		if(timer!=null){
			timer.stop();
		}
		KeyFrame frame=new KeyFrame(Duration.seconds(1) ,new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				seconds--;

				time.setText("RemaingTime: 00:" + seconds.toString());
				if (seconds <= 0) {
					timer.stop();
				}

				time.setText("RemaingTime: 00:"+seconds.toString());
				if(seconds<=0) {
					timer.stop();
				} 
			}
		});
		timer.getKeyFrames().add(frame);
		timer.playFromStart();
	}
}
