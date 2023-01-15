package Controller;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import Model.Game;
import Model.GameHistory;
import Model.User;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Duration;

public class BoardController implements Initializable {
	@FXML
	private Text time;
	@FXML
	private Text score;
	@FXML
	private Button first;
	@FXML
	private Button exitGame;
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
	public final static Integer startTime = 60;
	public static Integer seconds = startTime;
	private int level = 1;
	public static Timeline scores;
	public static Timeline timer;
	public int flag = 0;
	// static var to save the totalPoints in game
	public static int totalScore = 0;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// importing the games history
		if (LoginController.getUser() == null) {
			System.out.println("no user!!error in import users");
			playerName.setText("Player Name: Error! ");
		} else {
			playerName.setText("Player: " + LoginController.getUser().getUsername()); // set the user name on the board

		}
		scores = new Timeline();
		timer = new Timeline();
		// TODO Auto-generated method stub
		Game game = new Game(chessBoard, MainMenuController.getThemeSelected(), level);
		game.setController(this);
		doTime(game);
		doScore(game);
		first.setStyle("-fx-background-color: #b9f6ca; ");

//		forth.setOnAction(new EventHandler<ActionEvent>() {
//			@Override
//			public void handle(ActionEvent arg0) {
//				// TODO Auto-generated method stub
//				scores.stop();
//				timer.stop();
//				seconds = 30;
//				game.setScore(10);
//				level = 4;
//				initialize(location, resources);
//			}
//
//		});
		/*
		 * second.setOnAction(new EventHandler<ActionEvent>() {
		 * 
		 * @Override public void handle(ActionEvent arg0) { // TODO Auto-generated
		 * method stub scores.stop(); timer.stop(); seconds=60; game.setScore(0); level
		 * = 2; initialize(location,resources); }
		 * 
		 * }); third.setOnAction(new EventHandler<ActionEvent>() {
		 * 
		 * @Override public void handle(ActionEvent arg0) { // TODO Auto-generated
		 * method stub scores.stop(); timer.stop(); seconds=60; game.setScore(0); level
		 * = 3; game.stopTimer(); initialize(location,resources); }
		 * 
		 * }); forth.setOnAction(new EventHandler<ActionEvent>() {
		 * 
		 * @Override public void handle(ActionEvent arg0) { // TODO Auto-generated
		 * method stub scores.stop(); timer.stop(); seconds=60; game.setScore(0); level
		 * = 4; game.stopTimer(); initialize(location,resources); }
		 * 
		 * });
		 */
	}

	// Level up function initialize a new game by the new level
	// It get the level and also the score in order to save the higher score
	public void levelUp(int currentlevel) throws IOException {
		if (currentlevel == 2) {
			second.setStyle("-fx-background-color: #89efa5; ");
			scores.stop();
			timer.stop();
			timer = new Timeline();
			seconds = 60;
			Game game = new Game(chessBoard, MainMenuController.getThemeSelected(), currentlevel);
			game.setController(this);
			game.setScore(1);
			game.stopTimer();
			doTime(game);
			doScore(game);
		} else if (currentlevel == 3) {
			third.setStyle("-fx-background-color: #78e495; ");
			scores.stop();
			timer.stop();
			timer = new Timeline();
			seconds = 60;
			Game game = new Game(chessBoard, MainMenuController.getThemeSelected(), currentlevel);
			game.setController(this);
			game.setScore(1);
			game.stopTimer();
			doTime(game);
			doScore(game);
		} else if (currentlevel == 4) {
			forth.setStyle("-fx-background-color: #4da865; ");
			scores.stop();
			timer.stop();
			timer = new Timeline();
			seconds = 60;
			Game game = new Game(chessBoard, MainMenuController.getThemeSelected(), currentlevel);
			game.setController(this);
			game.setScore(1);
			game.stopTimer();
			doTime(game);
			doScore(game);
		} else if (currentlevel == 5) { // if he ends the last level correctly
			User newU = new User(LoginController.getUser().getUsername(), LoginController.getUser().getPassword());
			scores.stop();
			timer.stop();
			// updating the game history
			GameHistory historyGame = new GameHistory(level, LoginController.getUser(), totalScore,
					LocalDate.now().toString());
			System.out.println(historyGame.toString());
			try {
				if (Sysdata.getGamesHistoryList().isEmpty()) {
				//	Sysdata.importGameHistorysFromJSON();
				}
					Sysdata.getGamesHistoryList().add(historyGame);
					Sysdata.exportGamesHistoryToJSON();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			// updating the high Score for the player
			try {
				Sysdata.getThPlayers().remove(LoginController.getUser());
				newU.setHighScore(totalScore);
				Sysdata.getThPlayers().add(newU);
				Sysdata.exportUsersToJSON();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}

			// win pop-up
			Stage primaryStage = new Stage();
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/View/YouWinWindow.fxml"));
			Parent root;
			try {
				root = fxmlLoader.load();
				YouWinCtr winController = fxmlLoader.getController();
				Button button = winController.getBackBt();
				button.setOnAction(new EventHandler<ActionEvent>() {
				    @Override
				    public void handle(ActionEvent event) {
				    	Stage tempStage = (Stage) button.getScene().getWindow();
				    	tempStage.close();
				        exitGame.fire();
				    }
				});
	
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
		flag = 0;
		timer.setCycleCount(Timeline.INDEFINITE);
		if (timer != null) {
			timer.stop();
		}
		KeyFrame frame = new KeyFrame(Duration.seconds(1), new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				if(flag == 1) {
					seconds--;
				}
				time.setText("RemaingTime: 00:" + seconds.toString());
				if (seconds <= 0) {
					if (level < 4 && g.getScore() >= 15) { // for level 1-3 -->must reach min 15 !! 4 for tests
						timer.stop();
						level++;
						totalScore = totalScore + g.getScore();
						System.out.println(totalScore);
						ButtonType levelUpButton = new ButtonType("Next Level");
						Alert alert = new Alert(AlertType.INFORMATION, "", levelUpButton);
						alert.setTitle("Well Done !");
						alert.setHeaderText("Congratulations !");
						alert.setContentText("You completed level "+(level-1)+" !");
						Image image = new Image("/images/bravo.png");
						ImageView view = new ImageView(image);
						view.setFitHeight(80);
					    view.setFitWidth(80);
						alert.setGraphic(view);
						alert.show();
						try {
							levelUp(level);
						} catch (IOException e) {
							e.printStackTrace();
						}
					} else if (level == 4 && g.getScore() >= 15) { // for the last level
						timer.stop();
						level++;
						totalScore = totalScore + g.getScore();
						System.out.println(totalScore);
						try {
							levelUp(level);
						} catch (IOException e) {
							e.printStackTrace();
						}
					} else { // if the player didn't reach the min points and the time is over
						Alert alert = new Alert(Alert.AlertType.INFORMATION);
				        alert.setTitle("Game Over !");
				        alert.setHeaderText("Time's UP !");
				        alert.setContentText("Better Luck Next Time.");

				        // Add a custom button to the alert
				        ButtonType buttonTypeOk = new ButtonType("Exit to Main Menu");
				        alert.getButtonTypes().setAll(buttonTypeOk);
				        Button buttonOk = (Button) alert.getDialogPane().lookupButton(buttonTypeOk);

				        // Set an event handler for the custom button
				        buttonOk.setOnAction(new EventHandler<ActionEvent>() {
				          @Override
				          public void handle(ActionEvent event) {
				            exitGame.fire();
				          }
				        });
				        Image image = new Image("/images/timesup.png");
						ImageView view = new ImageView(image);
						view.setFitHeight(80);
					    view.setFitWidth(80);
						alert.setGraphic(view);
				        // Show the alert
				        alert.show();	
						timer.stop();
						totalScore = totalScore + g.getScore();
						GameHistory historyGame = new GameHistory(level, LoginController.getUser(), totalScore, LocalDate.now().toString());
						System.out.println(historyGame.toString());
						System.out.println(LocalDate.now().toString());
						try {
							if (Sysdata.getGamesHistoryList().isEmpty()) {
								Sysdata.importGameHistorysFromJSON();
							}
								Sysdata.getGamesHistoryList().add(historyGame);
								Sysdata.exportGamesHistoryToJSON();
							
						} catch (FileNotFoundException e) {
							e.printStackTrace();
						}

					}
				}
			}
		});
		timer.getKeyFrames().add(frame);
		timer.playFromStart();
	}

	// Score calculator
	private void doScore(Game g) {
		scores = new Timeline();
		scores.setCycleCount(Timeline.INDEFINITE);
		if (scores != null) {
			scores.stop();
		}
		KeyFrame frame = new KeyFrame(Duration.seconds(0.1), new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				score.setText("\n\n Score : " + g.getScore());
				if (seconds <= 0) {
					scores.stop();
				}
			}
		});
		scores.getKeyFrames().add(frame);
		scores.playFromStart();
	}

	@FXML
	public void exitGame(ActionEvent event) throws IOException {
		// Close the current stage
		timer.stop();
		Stage currentStage = (Stage) exitGame.getScene().getWindow();
		currentStage.close();
		timer = new Timeline();
		seconds = 60;

		// TODO: handle exception
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
	
	public void restartTrigger() {
		first.setStyle("-fx-background-color: #b9f6ca; ");
		second.setStyle("-fx-background-color: transparent; -fx-border-radius: 5; -fx-background-radius: 5;");
		third.setStyle("-fx-background-color: transparent; -fx-border-radius: 5; -fx-background-radius: 5;");
		forth.setStyle("-fx-background-color: transparent; -fx-border-radius: 5; -fx-background-radius: 5;");
		first.fire();
	}
	
	public void exitTrigger() {
		exitGame.fire();
	}
	public void levelUpTrigger(int lvl) throws IOException {
		levelUp(lvl);
	}
	public static int getSeconds() {
		return seconds;
	}

	public int getFlag() {
		return flag;
	}

	public void setFlag(int flag) {
		this.flag = flag;
	}
	
	

}
