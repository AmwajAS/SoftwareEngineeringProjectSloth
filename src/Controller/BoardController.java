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
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

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
	private javafx.scene.text.Text playerName;

	Timer countdownTimer;

	@FXML
	private GridPane chessBoard;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		Game game = new Game(chessBoard, "Coral");
		first.setStyle("-fx-background-color: #b9f6ca; ");

	}

}
