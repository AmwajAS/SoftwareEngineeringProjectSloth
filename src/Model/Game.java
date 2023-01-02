package Model;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.event.EventTarget;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import Alerts.Alerts;
import Controller.BoardController;
import Controller.LoginController;
import Controller.QuestionPopupController;

public class Game {

	private Board chessboard;
	private int level;
	private User user;
	public static Piece currentPiece;
	public static String currentPlayer;
	public static Piece tempKing;
	public static Board cb;
	private boolean game;
	public int timerCounter =0;
	private int score = 1;
	private int flag = 0;
	private int flagging = 0;
	private ArrayList<Cell> lastMoves;
	private static Timer timer = new Timer();
	private static Timer counterTimer =  new Timer();
	private LocalDate gamedate;
	private int finalScore;
	


	public Game(GridPane chessBoard, String theme, int lvl) {
		this.level = lvl;
		cb = new Board(chessBoard, theme, lvl);
		currentPiece = null;
		currentPlayer = "black";
		this.game = true;
		lastMoves = new ArrayList<>();
		lastMoves.add(cb.getCells().get(0));
		addEventHandlers(cb.getChessBoard());
	}



	public Game(int level, User user, int finalscore) {
		super();
		this.level = level;
		this.user = user;
		this.finalScore = finalscore;
		this.gamedate = LocalDate.now();
	}



	private void addEventHandlers(GridPane chessBoard) {
		chessBoard.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				try {
					EventTarget target = event.getTarget();
					// Clicked on cell
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
								currentPiece.getAllPossibleMoves();
								if (!currentPiece.getColor().equals("black")) {
									currentPiece = null;
									return;
								}
								selectPiece(game);
							}
							// Selecting other piece of same color || Killing a piece
							else {
								if (currentPiece.getColor().equals(newPiece.getColor())) {
									deselectPiece(false);
									currentPiece = newPiece;
									currentPiece.getAllPossibleMoves();
									selectPiece(game);
								} else {
									return;
								}
							}
						}
						// Dropping a piece on blank square
						else {
							if (currentPiece != null) {
								dropPiece(cell);
								// The king will start moving as soon as the knight makes the first move;
								// the flag is originally 0, then the first time it goes inside the if it
								// changes to 1;
								// which means we call this function only once;
								if (flag == 0 && (level == 3 || level == 4)) {
									startTimer();
								} else if (level == 1 || level == 2) {
									if (currentPlayer.equals("white")) {
										for (Cell temp : cb.getCells()) {
											if (!temp.getChildren().isEmpty()) {
												if ((Piece) temp.getChildren().get(0) != null) {
													Piece tempPiece = (Piece) temp.getChildren().get(0);

													if (tempPiece.getColor().equals("white")
															&& tempPiece instanceof Queen
															&& currentPlayer.equals("white")) {
														findBestRoute(temp, cell);
													}
												}

											}
										}
									}
								}
							}
						}

					}
					// Clicked on piece
					else {
						Piece newPiece = (Piece) target;
						Cell square = (Cell) newPiece.getParent();

						// Selecting a new piece
						if (currentPiece == null) {
							currentPiece = newPiece;
							if (!currentPiece.getColor().equals("black")) {
								currentPiece = null;
								return;
							}

							selectPiece(game);
						}
						// Selecting other piece of same color || Killing a piece
						else {
							if (currentPiece.getColor().equals(newPiece.getColor())) {
								deselectPiece(false);
								currentPiece = newPiece;
								selectPiece(game);
							} else {
								return;
							}
						}
					}
				} catch (Exception e) {

					System.out.println(e.getMessage());
				}

			}
		});
	}

	// This function is for the queen's move it works like this:
	/*
	 * if the queen can kill the knight it kills him. if not, the queen will block
	 * one of the future possible destination for the knight(To make it harder). if
	 * none of the above is available it moves randomly.
	 */
	/*
	 * tempCell is the queen, cell is the knight
	 */
	public void findBestRoute(Cell tempCell, Cell cell) {
		for (Cell temp : cb.getCells()) {
			if (!temp.getChildren().isEmpty()) {
				if ((Piece) temp.getChildren().get(0) != null) {
					Piece tempPiece = (Piece) temp.getChildren().get(0);
					if (tempPiece instanceof Knight) {
						cell = temp;
						break;
					}
				}
			}
		}
		Piece tempKnight = (Piece) cell.getChildren().get(0);
		tempKnight.getAllPossibleMoves();
		Piece tempPiece = (Piece) tempCell.getChildren().get(0);
		currentPiece = tempPiece;
		tempPiece.getAllPossibleMoves();
		if (tempPiece.getPossibleMoves().isEmpty()) {
			System.out.println("bdestinationlist in findBestRoute function is empty!!!");
			return;
		} else {
			ArrayList<String> tempMoves = tempPiece.getPossibleMoves();
			for (String move : tempMoves) {
				if (move.equals(cell.getName())) {

					killPiece(cell);
					dropPiece(cell);
				}
			}
			for (String move : tempMoves) {
				for (String knightMove : tempKnight.getPossibleMoves()) {
					if (move.equals(knightMove)) {
						for (Cell temp : cb.getCells()) {
							if (temp.getName().equals(move)) {
								dropPiece(temp);
							}
						}
					}
				}
			}
			Random rand = new Random();
			int len = tempMoves.size();

			String tempName = tempMoves.get(rand.nextInt(len));
			for (Cell temp : cb.getCells()) {
				if (temp.getName().equals(tempName)) {
					dropPiece(temp);
				}

			}
		}
	}

	// this function returns the best cell for the king to jump on.

	public void findBestRouteKing(Cell cTemp) {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				tempKing.getAllPossibleMoves();
				ArrayList<String> kingTempMoves = tempKing.getPossibleMoves();
				Cell help = cTemp;
				for (Cell temp : cb.getCells()) {
					if (!temp.getChildren().isEmpty()) {
						if ((Piece) temp.getChildren().get(0) != null) {
							Piece tempPiece = (Piece) temp.getChildren().get(0);
							if (tempPiece instanceof Knight) {
								help = temp;
								break;
							}
						}
					}
				}
				// If the king can kill the knight he does it.
				for (String move : kingTempMoves) {
					if (move.equals(help.getName())) {
						kingKillPiece(help);
						dropKingPiece(help);
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
				if (closestCell != null)
					dropKingPiece(closestCell);
				// just in case something went wrong and we don't want the game to collapse, we
				// choose a random cell for the king to jump on;
				else {
					Random rand = new Random();
					int len = kingTempMoves.size();
					String kingTempCellName = kingTempMoves.get(rand.nextInt(len));
					for (Cell tempCellCheck : cb.getCells()) {
						if (tempCellCheck.getName().equals(kingTempCellName)) {
							dropKingPiece(tempCellCheck);
						}
					}
				}
			}
		});
	}

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
		currentPiece.getAllPossibleMoves();
		currentPiece.showAllPossibleMoves(true);
	}

	private void deselectPiece(boolean changePlayer) {
		if (currentPiece != null) {
			currentPiece.setEffect(null);
			currentPiece.showAllPossibleMoves(false);
			currentPiece = null;
			if (changePlayer)
				currentPlayer = currentPlayer.equals("white") ? "black" : "white";
		}
	}

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

	private void dropPiece(Cell cell) {
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
		if (currentPiece instanceof Knight) {
			cell.setCounter(cell.getCounter()+1);
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
		// if the cell instance of Question we will call the fmxl Question Controller pop-up
				if (cell instanceof QuesCell && currentPiece instanceof Knight) {
					  FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/View/QuestionPopup.fxml"));
					    Parent root;
						try {
							root = fxmlLoader.load();
							 QuestionPopupController controller = fxmlLoader.getController();
							    controller.setGame(this);
							    controller.getGame().setScore(score);
							    Scene scene = new Scene(root);
							    Stage stage = new Stage();
							    stage.setScene(scene);
							    stage.show();
							    QuesCell help = (QuesCell) cell;
								help.createNewQuesCell(cb, cell);
								controller.checkAnswer();

						} catch (IOException e) {
							// TODO 
							e.printStackTrace();
						}
				}
		if (cell instanceof UndoCell && currentPiece instanceof Knight) {
			setScore(((UndoCell) cell).undoMoves(cb, lastMoves,score));
			UndoCell help = (UndoCell) cell;
			Cell cl;
			cl=help.createNewUndoCell(cb, cell);
			lastMoves.removeAll(lastMoves);
			lastMoves.add(cl);
		}
		if (cell instanceof JumpCell && currentPiece instanceof Knight) {
			flagging = 1;
			Cell temp = ((JumpCell) cell).Jump(cb);
			dropPiece(temp);
			flagging = 0;
			JumpCell help = (JumpCell) cell;
			help.createNewJumpCell(cb, cell);
		}
		deselectPiece(true);
	}

	// function when the king kills the knight.
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
					System.out.println("Game Over!!!");
					killFlag = 1;
					Alerts.showAlert(AlertType.WARNING, "Game Over!", "Please try again.", ButtonType.OK);
				}
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

	private void killPiece(Cell cell) {
		if (!currentPiece.getPossibleMoves().contains(cell.getName()))
			return;

		Piece killedPiece = (Piece) cell.getChildren().get(0);
		if (killedPiece.getType().equals("King"))
			this.game = false;

		Cell initialSquare = (Cell) currentPiece.getParent();
		Piece temp = (Piece) cell.getChildren().get(0);
		cell.getChildren().remove(0);
		cell.getChildren().add(currentPiece);
		if (temp instanceof Knight) {
			this.game = false;
			System.out.println("Game Over!!!");
			BoardController.totalScore= BoardController.totalScore+getScore();
			GameHistory historyGame= new GameHistory(level, LoginController.getUser(), BoardController.totalScore);
			System.out.println(historyGame.toString());
			Alerts.showAlert(AlertType.WARNING, "Game Over!", "Please try again.", ButtonType.OK);
		}
		cell.setOccupied(true);
		initialSquare.getChildren().removeAll();
		initialSquare.setOccupied(false);
		currentPiece.setPosX(cell.getX());
		currentPiece.setPosY(cell.getY());
		deselectPiece(true);
	}

	public void stopTimer() {
		timer.cancel();
		counterTimer.cancel();
	}

	// implementing a thread to make a move very second for the king.
	// just in case we're at level 3/4.
	public void startTimer() {

		flag = 1;
		counterTimer = new Timer(); 
		timer = new Timer();
		timer.schedule(new TimerTask() {
			int speed = 2000;

			@Override
			public void run() {
				try {
					Thread.sleep(speed);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				Cell helper = null;
				for (Cell kingTempCell : cb.getCells()) {
					if (!kingTempCell.getChildren().isEmpty()) {
						if ((Piece) kingTempCell.getChildren().get(0) != null) {
							Piece tempKingCheck = (Piece) kingTempCell.getChildren().get(0);
							if (tempKingCheck.getColor().equals("white") && tempKingCheck instanceof King) {
								helper = kingTempCell;
								tempKing = tempKingCheck;
								findBestRouteKing(helper);
							}
						}
					}
				}
				
					if (timerCounter % 10 == 0) {
						System.out.println(timerCounter);
						speed -= 500;
						System.out.println("this is the speed : "+speed);
						if (speed < 0) {
							speed = 0;
						}
					}
				
			}
		}, 1000, 1000);
		
		counterTimer.schedule(new TimerTask() {

			@Override
			public void run() {
				timerCounter++;
				System.out.println("this is the counter :"+timerCounter);
			}
			
		}, 1000, 1000);
		
	}

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
		if(this.score <= 0) {
			this.score=0;
		}
	}

	public LocalDate getGamedate() {
		return gamedate;
	}

	public void setGamedate(LocalDate gamedate) {
		this.gamedate = gamedate;
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


}