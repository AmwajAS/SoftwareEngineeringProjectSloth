package Model;

import java.util.ArrayList;
import java.util.Random;

import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.event.EventTarget;
import javafx.scene.Node;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;

import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;

public class Game {

	private Board chessboard;
	private int level;
	private User user;

	public static Piece currentPiece;
	public static String currentPlayer;
	public static Board cb;
	private boolean game;
	private int score = 0;

	public Game(GridPane chessBoard, String theme) {
		cb = new Board(chessBoard, theme);
		currentPiece = null;
		currentPlayer = "black";
		this.game = true;
		addEventHandlers(cb.getChessBoard());
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
								if (currentPlayer.equals("white")) {
									for (Cell temp : cb.getCells()) {
										if (!temp.getChildren().isEmpty()) {
											if ((Piece) temp.getChildren().get(0) != null) {
												Piece tempPiece = (Piece) temp.getChildren().get(0);

												if (tempPiece.getColor().equals("white")
														&& currentPlayer.equals("white")) {
													findBestRoute(temp);
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
					System.out.println("Do not drag, just click!!");
					System.out.println(e.getMessage());
				}
			}
		});
	}

	public void findBestRoute(Cell tempCell) {
		Piece tempPiece = (Piece) tempCell.getChildren().get(0);
		currentPiece = tempPiece;
		tempPiece.getAllPossibleMoves();
		if (tempPiece.getPossibleMoves().isEmpty()) {
			System.out.println("bdestinationlist in findBestRoute function is empty!!!");
			return;
		} else {
			ArrayList<String> tempMoves = tempPiece.getPossibleMoves();
			Random rand = new Random();
			int len = tempMoves.size();
			String tempName = tempMoves.get(rand.nextInt(len));
			for (Cell temp : cb.getCells()) {
				if (temp.getName().equals(tempName)) {
					if (temp.isOccupied()) {
						killPiece(temp);
						dropPiece(temp);
					} else {
						dropPiece(temp);
					}

				}
			}
		}
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
		currentPiece.setEffect(null);
		currentPiece.showAllPossibleMoves(false);
		currentPiece = null;
		if (changePlayer)
			currentPlayer = currentPlayer.equals("white") ? "black" : "white";
	}

	private void dropPiece(Cell cell) {
		if (currentPiece != null && !currentPiece.getPossibleMoves().isEmpty()) {
			if (!currentPiece.getPossibleMoves().contains(cell.getName()))
				return;
		}
		if (currentPiece != null && !currentPiece.getPossibleMoves().isEmpty()) {
			Cell initialCell = (Cell) currentPiece.getParent();
			cell.getChildren().add(currentPiece);
			cell.setOccupied(true);
			initialCell.getChildren().removeAll();
			initialCell.setOccupied(false);
			currentPiece.setPosX(cell.getX());
			currentPiece.setPosY(cell.getY());
			if (currentPiece instanceof Knight) {
				if (!cell.isVisited()) {
					score++;
					setScore(score);
					cell.setVisited(true);
				}
			}
			deselectPiece(true);
		}
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
			this.game=false;
			System.out.println("Game Over!!!");
		}
		cell.setOccupied(true);
		initialSquare.getChildren().removeAll();
		initialSquare.setOccupied(false);
		currentPiece.setPosX(cell.getX());
		currentPiece.setPosY(cell.getY());
		deselectPiece(true);
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
	}

}