package Model;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.event.EventTarget;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import Alerts.Alerts;
import Controller.BoardController;
import Controller.LoginController;
import Controller.QuestionPopupController;
import Controller.Sysdata;

public class Game {

	private Board chessboard;
	private int level;
	private User user;
	public static Piece currentPiece;
	public static String currentPlayer;
	public static Piece tempKing;
	public static Board cb;
	private boolean game;
	public int speed = 2000;
	private int score = 1;
	private int flag = 0;
	private int flagging = 0;
	int[] array = new int[6];
	private ArrayList<Cell> lastMoves;
	private static Timer timer = new Timer();
	private int finalScore;
	BoardController controller;

	public Game(GridPane chessBoard, String theme, int lvl) {
		this.level = lvl;
		cb = new Board(chessBoard, theme, lvl);
		currentPiece = null;
		currentPlayer = "black";
		this.game = true;
		lastMoves = new ArrayList<>();
		lastMoves.add(cb.getCells().get(0));
		addEventHandlers(cb.getChessBoard());
		Arrays.fill(array, 0);
	}

	public Game(int level, User user, int finalscore) {
		super();
		this.level = level;
		this.user = user;
		this.finalScore = finalscore;
	}

	private void addEventHandlers(GridPane chessBoard) {
		chessBoard.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				try {
					if(controller.getFlag()==0) {
						controller.setFlag(1);
					}
					EventTarget target = event.getTarget();
					  //clicked on a cell is occupied takes the piece on the cell if not drops the already selected piece.
					if (target.toString().equals("Square")) {
						Cell cell = (Cell) target;
						if (cell instanceof JumpCell) {
							flagging = 0;
						}
						if (cell.isOccupied()) {
							Piece newPiece = (Piece) cell.getChildren().get(0);
							// Selecting a new piece
							if (currentPiece == null) {
								currentPiece = newPiece;
								currentPiece.getAllPossibleMoves(level);
								if (!currentPiece.getColor().equals("black")) {
									currentPiece = null;
									return;
								}
								selectPiece(game);
							}
						}
						// Dropping a piece on blank square
						else {
							if (currentPiece != null) {
								dropPiece(cell);
								//startTimer() calls the king to move.
								// The king will start moving as soon as the knight makes the first move;
								// the flag is originally 0, then the first time it goes inside the if it
								// changes to 1;
								// which means we call this function only once;
								if (flag == 0 && (level == 3 || level == 4)) {
									startTimer();
								} else if (level == 1 || level == 2) {
									if (currentPlayer.equals("white")) {
										Cell temp = findPiece("Queen");
										findBestRoute(temp, cell);
									}
								}
							}
						}
					}
					/*
					 * Clicking on a piece instead of the cell
					 */
					else {
						Piece newPiece = (Piece) target;

						// Selecting a new piece
						if (currentPiece == null) {
							currentPiece = newPiece;
							if (!currentPiece.getColor().equals("black")) {
								currentPiece = null;
								return;
							}
							selectPiece(game);
						}
					}
				}catch(Exception e) {
					System.out.println(e.getMessage());
				}
				
			}
		});
	}

	// This function is for the queen's move it works like this:
	/*
	 * if the queen can kill the knight it kills him. if not, the queen will block
	 * one of the future possible destination for the knight(To make it harder). if
	 * not, the queen will move as close to the knight as she can. if none of the
	 * above is available it moves randomly.
	 */
	/*
	 * tempCell is the queen, cell is the knight
	 */
	public void findBestRoute(Cell tempCell, Cell cell) {
		cell = findPiece("Knight");
		Piece tempKnight = (Piece) cell.getChildren().get(0);
		tempKnight.getAllPossibleMoves(level);
		Piece tempPiece = (Piece) tempCell.getChildren().get(0);
		currentPiece = tempPiece;
		tempPiece.getAllPossibleMoves(level);
		if (tempPiece.getPossibleMoves().isEmpty()) {
			return;
		} else {
			ArrayList<String> tempMoves = tempPiece.getPossibleMoves();
			//killing the Knight
			for (String move : tempMoves) {
				if (move.equals(cell.getName())) {
					killPiece(cell);
					dropPiece(cell);
					return;
				}
			}
			//blocking one future cell for the knight
			for (String move : tempMoves) {
				for (String knightMove : tempKnight.getPossibleMoves()) {
					if (move.equals(knightMove)) {
						for (Cell temp : cb.getCells()) {
							if (temp.getName().equals(move)) {
								dropPiece(temp);
								return;
							}
						}
					}
				}
			}
			//getting as close as she can to the knight
			Cell closestCell = null;
			int minDistance = Math.max(Math.abs(cell.getX() - tempPiece.getPosX()),
					Math.abs(cell.getY() - tempPiece.getPosY()));
			for (String move : tempPiece.getPossibleMoves()) {
				int distance = Math.max(Math.abs(cell.getX() - tempPiece.getSquareByName(move).getX()),
						Math.abs(cell.getY() - tempPiece.getSquareByName(move).getY()));
				if (distance < minDistance) {
					closestCell = tempPiece.getSquareByName(move);
					minDistance = distance;
				}
			}
			if (closestCell != null) {
				dropPiece(closestCell);
				return;
			} 
			//random cell
			else {
				Random rand = new Random();
				int len = tempMoves.size();

				String tempName = tempMoves.get(rand.nextInt(len));
				for (Cell temp : cb.getCells()) {
					if (temp.getName().equals(tempName)) {
						dropPiece(temp);
						return;
					}
				}
			}
		}
	}

	// this function returns the best cell for the king to jump on.

	public void findBestRouteKing(Cell cTemp) {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				tempKing.getAllPossibleMoves(level);
				ArrayList<String> kingTempMoves = tempKing.getPossibleMoves();
				Cell help = cTemp;
				help = findPiece("Knight");
				// If the king can kill the knight he does it.
				for (String move : kingTempMoves) {
					if (move.equals(help.getName())) {
						kingKillPiece(help);
						dropKingPiece(help);
						return;
					}
				}
				/*
				 * if the king can't kill the knight he will go to the closest cell to the
				 * knight
				 */
				Cell closestCell = null;
				int minDistance = Math.max(Math.abs(help.getX() - tempKing.getPosX()),
						Math.abs(help.getY() - tempKing.getPosY()));
				for (String move : kingTempMoves) {
					int distance = Math.max(Math.abs(help.getX() - tempKing.getSquareByName(move).getX()),
							Math.abs(help.getY() - tempKing.getSquareByName(move).getY()));
					if (distance < minDistance) {
						closestCell = tempKing.getSquareByName(move);
						minDistance = distance;
					}
				}
				if (closestCell != null) {
					dropKingPiece(closestCell);
					return;
				}
				// just in case something went wrong and we don't want the game to collapse, we
				// choose a random cell for the king to jump on;
				else {
					Random rand = new Random();
					int len = kingTempMoves.size();
					String kingTempCellName = kingTempMoves.get(rand.nextInt(len));
					for (Cell tempCellCheck : cb.getCells()) {
						if (tempCellCheck.getName().equals(kingTempCellName)) {
							dropKingPiece(tempCellCheck);
							return;
						}
					}
				}
			}
		});
	}
	//selecting the piece and showing all possible moves
	private void selectPiece(boolean game) {
		if (!game) {
			currentPiece = null;
			return;
		}
		DropShadow borderGlow = new DropShadow();
		borderGlow.setColor(Color.BLACK);
		borderGlow.setOffsetX((int) 0f);
		borderGlow.setOffsetY((int) 0f);
		currentPiece.setEffect(borderGlow);
		currentPiece.getAllPossibleMoves(level);
		currentPiece.showAllPossibleMoves(true);
	}
	//changing turns, removing the possible moves
	private void deselectPiece(boolean changePlayer) {
		if (currentPiece != null) {
			currentPiece.setEffect(null);
			currentPiece.showAllPossibleMoves(false);
			currentPiece = null;
			if (changePlayer)
				currentPlayer = currentPlayer.equals("white") ? "black" : "white";
		}
	}
	//dropping the king's piece 
	private void dropKingPiece(Cell c) {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				if (tempKing != null && !tempKing.getPossibleMoves().isEmpty()) {
					if (!tempKing.getPossibleMoves().contains(c.getName()))
						return;
				}
				if (tempKing != null && !tempKing.getPossibleMoves().isEmpty() && !c.getChildren().contains(tempKing)) {
					Cell initialCellKing = (Cell) tempKing.getParent();
					if (c.getChildren().isEmpty()) {
						c.getChildren().add(tempKing);
						c.setOccupied(true);
						initialCellKing.getChildren().removeAll();
						initialCellKing.setOccupied(false);
						tempKing.setPosX(c.getX());
						tempKing.setPosY(c.getY());
					}
				}
			}
		});
	}
	//dropping the piece on the cell
	private void dropPiece(Cell cell) {
		//in flagging is 0 it means that the cell is jumpCell, so we  dont want to check if the cell is in 
		//the possibleMoves because it is a random one on the board and might not be of the possibleMoves.
		if (flagging == 0) {
			if (currentPiece != null && !currentPiece.getPossibleMoves().isEmpty()) {
				if (!currentPiece.getPossibleMoves().contains(cell.getName())) {
					return;
				}
			}
		}
		if (currentPiece != null && !currentPiece.getPossibleMoves().isEmpty()) {
			Cell initialCell = (Cell) currentPiece.getParent();
			cell.getChildren().add(currentPiece);
			cell.setOccupied(true);
			initialCell.getChildren().removeAll();
			initialCell.setOccupied(false);
			currentPiece.setPosX(cell.getX());
			currentPiece.setPosY(cell.getY());
		}
		//changing the score and visited if the piece is the Knight
		if (currentPiece instanceof Knight) {
			cell.setCounter(cell.getCounter() + 1);
			addCellsToArraylist(cell);
			if (!cell.isVisited()) {
				score++;
				setScore(score);
				cell.setVisited(true);
			} else {
				if (cell.isVisited()) {
					if (score >= 1) {
						score--;
						setScore(score);
					}
				}
			}
		}
		// if the cell instance of Question we will call the fmxl Question Controller
		// pop-up
		if (cell instanceof QuesCell && currentPiece instanceof Knight) {
			// Stop the timer
			Controller.BoardController.timer.stop();
			stopTimer();
			// Load the FXML file for the pop-up window
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/View/QuestionPopup.fxml"));
			Parent root;
			try {
				root = fxmlLoader.load();
				QuestionPopupController controller = fxmlLoader.getController();
				controller.setGame(this);
				controller.getGame().setScore(score);
				// Create a new scene for the pop-up window
				Scene scene = new Scene(root);
				// Create a new stage for the pop-up window
				Stage stage = new Stage();
				// Set the scene for the pop-up stage
				stage.setScene(scene);
				stage.initStyle(StageStyle.UNDECORATED);
				// Show the pop-up stage and block the parent window until the pop-up stage is
				// closed
				stage.showAndWait();
				// Create a new QuesCell instance
				QuesCell help = (QuesCell) cell;
				help.createNewQuesCell(cb, cell);
				// Check the answer for the question
			} catch (IOException e) {
				// TODO
				e.printStackTrace();
			}
		}
		//UndoCell changes
		if (cell instanceof UndoCell && currentPiece instanceof Knight) {
			makeAlert("Undo");
			setScore(((UndoCell) cell).undoMoves(cb, lastMoves, score));
			UndoCell help = (UndoCell) cell;
			Cell cl;
			cl = help.createNewUndoCell(cb, cell);
			lastMoves.removeAll(lastMoves);
			lastMoves.add(cl);
		}
		//JumpCell changes
		if (cell instanceof JumpCell && currentPiece instanceof Knight) {
			flagging = 1;
			makeAlert("Jump");
			Cell temp = ((JumpCell) cell).Jump(cb);
			dropPiece(temp);
			flagging = 0;
			JumpCell help = (JumpCell) cell;
			help.createNewJumpCell(cb, cell);
		}
		if (cell instanceof QuesCell && (level == 3 || level == 4)) {
			startTimer();
		}
		deselectPiece(true);
	}

	/*
	 * function when the king kills the knight.
	 * showing an alert to the player, that he lost.
	 * stopping the game and saving the history.
	 */
	private void kingKillPiece(Cell cell) {
		Platform.runLater(new Runnable() {
			int killFlag = 0;

			@Override
			public void run() {
				if (!tempKing.getPossibleMoves().contains(cell.getName()))
					return;
				Cell initialSquare = (Cell) tempKing.getParent();
				Piece temp = (Piece) cell.getChildren().get(0);
				cell.getChildren().remove(0);
				cell.getChildren().add(tempKing);
				if (temp instanceof Knight) {
					killFlag = 1;
					Controller.BoardController.timer.stop();
					Controller.BoardController.scores.stop();
					stopTimer();
					GameHistory historyGame = new GameHistory(level, LoginController.getUser(),
							BoardController.totalScore, LocalDate.now().toString());
					try {
						// Sysdata.importGameHistorysFromJSON();
						Sysdata.getGamesHistoryList().add(historyGame);
						Sysdata.exportGamesHistoryToJSON();
					} catch (FileNotFoundException e) {
						e.printStackTrace();
					}
				}
				makeAlert("loseAlert");
				cell.setOccupied(true);
				initialSquare.getChildren().removeAll();
				initialSquare.setOccupied(false);
				dropKingPiece(cell);
				deselectPiece(true);
				if (killFlag == 1) {
					if (timer != null) {
						timer.cancel();
					}
				}
			}
		});
	}
	
	/*
	 * function for the queen when she kills the Knight
	 * stopping the game, showing an alert and saving to history.
	 */
	private void killPiece(Cell cell) {
		if (!currentPiece.getPossibleMoves().contains(cell.getName()))
			return;

		Cell initialSquare = (Cell) currentPiece.getParent();
		Piece temp = (Piece) cell.getChildren().get(0);
		cell.getChildren().remove(0);
		cell.getChildren().add(currentPiece);
		if (temp instanceof Knight) {
			this.game = false;
			BoardController.totalScore = BoardController.totalScore + getScore();

			Controller.BoardController.timer.stop();
			Controller.BoardController.scores.stop();
			GameHistory historyGame = new GameHistory(level, LoginController.getUser(), BoardController.totalScore,
					LocalDate.now().toString());
			try {
				// Sysdata.importGameHistorysFromJSON();
				Sysdata.getGamesHistoryList().add(historyGame);
				Sysdata.exportGamesHistoryToJSON();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}
		cell.setOccupied(true);
		initialSquare.getChildren().removeAll();
		initialSquare.setOccupied(false);
		currentPiece.setPosX(cell.getX());
		currentPiece.setPosY(cell.getY());
		deselectPiece(true);
		makeAlert("loseAlert");
	}

	/*
	 * This function is for creating alerts,
	 * it takes the type of the alert as a string  and creates an alert accordingly
	 */
	public void makeAlert(String type) {
		if (type == "loseAlert") {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Game Over");
			alert.setHeaderText("Sorry, you lost the game!");
			alert.setContentText("Better luck next time!");
			Image image = new Image("/images/sadbavo.png");
			ImageView view = new ImageView(image);
			view.setFitHeight(80);
		    view.setFitWidth(80);
			alert.setGraphic(view);
			ButtonType buttonTypeTryAgain = new ButtonType("Try Again");
			ButtonType buttonTypeExit = new ButtonType("Exit");
			alert.getButtonTypes().setAll(buttonTypeTryAgain, buttonTypeExit);
			Optional<ButtonType> result = alert.showAndWait();
			if (result.isPresent()) {
				if (result.get() == buttonTypeTryAgain) {
					controller.restartTrigger();
				} else if (result.get() == buttonTypeExit) {
					controller.exitTrigger();
				}
			}
		} 
		/*
		 * checking if the type is cell Type and creating an alert when jumping on a specific cell type.
		 */
		else {
			Controller.BoardController.timer.stop();
			if (level == 3 || level == 4) {
				stopTimer();
			}
			// Create an alert with a progress indicator

			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Surprise!!");
			if (type == "Jump") {
				alert.setHeaderText("JumpCell Selected");
				alert.setContentText("Now you will jump to a random cell!");
				alert.getDialogPane().setStyle("-fx-background-color: #add8e6;" + " -fx-text-fill: white;");
			} else if (type == "Undo") {
				alert.setHeaderText("UndoCell Selected");
				alert.setContentText("Now your last 3 moves will be removed!.");
				alert.getDialogPane().setStyle("-fx-background-color: orange;" + " -fx-text-fill: white;");
			}
			PauseTransition delay = new PauseTransition(Duration.seconds(2)); // time that the notification disappears
																				// after
			delay.setOnFinished(event -> alert.hide());

			// Create a progress indicator
			ProgressIndicator progressIndicator = new ProgressIndicator();
			alert.getDialogPane().setMinSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);

			// Set the progress indicator as the graphic of the alert
			alert.setGraphic(progressIndicator);
			delay.play();
			alert.showAndWait();
			if (level == 3 || level == 4) {
				startTimer();
			}
			Controller.BoardController.timer.play();
		}
	}


	//stopping the timer of the king
	public void stopTimer() {
		timer.cancel();
	}

	// implementing a thread to make a move very second for the king.
	// just in case we're at level 3/4.
	/*
	 * the King starts with the speed of a move in 2 seconds
	 * after every 10 seconds he gets faster of -750milliseconds
	 * reaching sec 30 he starts moving the fastest of a moveevery 750millisecond
	 * the array is for helping us to do the speed every 10 seconds.
	 */
	public void startTimer() {
		flag = 1;
		timer = new Timer();
		timer.schedule(new TimerTask() {

			@Override
			public void run() {
				Cell helper = null;
				helper = findPiece("King");
				tempKing = (Piece) helper.getChildren().get(0);
				findBestRouteKing(helper);

				try {
					Thread.sleep(speed);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				int gameTimer = BoardController.seconds;
				if ((gameTimer % 10 == 0 && gameTimer != 60) || (gameTimer - 1) % 10 == 0) {
					gameTimer = (int) Math.floor(gameTimer / 10);
					if (array[gameTimer] == 0) {
						speed -= 750;
						if (speed < 0) {
							speed = 0;
						}
						array[gameTimer] = 1;
					}
				}
			}
		}, 750, 750);
	}
	
	/*
	 *this function is for finding a piece depending on the type 
	 *it returns the cell of the piece type it receives.
	 */
	public Cell findPiece(String pieceType) {
		Cell pieceCell = null;
		for (Cell temp : cb.getCells()) {
			if (!temp.getChildren().isEmpty()) {
				if ((Piece) temp.getChildren().get(0) != null) {
					Piece tempPiece = (Piece) temp.getChildren().get(0);
					if (pieceType == "Knight") {
						if (tempPiece instanceof Knight) {
							pieceCell = temp;
							break;
						}
					} else if (pieceType == "Queen") {
						if (tempPiece instanceof Queen) {
							pieceCell = temp;
							break;
						}
					} else if (pieceType == "King") {
						if (tempPiece instanceof King) {
							pieceCell = temp;
							break;
						}
					}
				}
			}
		}
		return pieceCell;
	}
	/*
	 * this is for adding the cells into the arraylist, keeping only 4 cells
	 */
	public void addCellsToArraylist(Cell c) {
		if (lastMoves.size() > 3) {
			lastMoves.remove(0);
		}
		lastMoves.add(c);
	}

	public Board getChessboard() {
		return chessboard;
	}

	public void setChessboard(Board chessboard) {
		this.chessboard = chessboard;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
		if (this.score <= 0) {
			this.score = 0;
		}
	}

	public int getFinalScore() {
		return finalScore;
	}

	public void setFinalScore(int finalScore) {
		this.finalScore = finalScore;
	}

	@Override
	public String toString() {
		return "Game [level=" + level + ", user=" + user + ", finalScore=" + finalScore + "]";
	}
	
	//setting the controller to the game from the ChessBoardController
	public void setController(BoardController BC) {
		this.controller = BC;
	}

}