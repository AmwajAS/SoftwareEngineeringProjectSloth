
package Model;

import javafx.event.EventHandler;
import javafx.event.EventTarget;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent; 
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;

/*constructor for the different levels
public Game(GridPane chessBoard, String theme, int level) {
	cb = new Board(chessBoard, theme);
	this.level = level;
	currentPiece = null;
	currentPlayer = "black";
	this.game = true;
	addEventHandlers(cb.getChessBoard());	
}*/


public class Game {

	private Board chessboard;
	private int level;
	private User user;

	public static Piece currentPiece;
	public static String currentPlayer;
	public static Board cb;
	private boolean game;

	public Game(GridPane chessBoard, String theme) {
		cb = new Board(chessBoard, theme);
		currentPiece = null;
		currentPlayer = "black";
		this.game = true;
		addEventHandlers(cb.getChessBoard());
	}

	private void addEventHandlers(GridPane chessBoard){
		chessBoard.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				EventTarget target = event.getTarget();

				// Clicked on square
				if(target.toString().equals("Square")){
					Cell cell = (Cell) target;
					if(cell.isOccupied()){
						Piece newPiece = (Piece) cell.getChildren().get(0);
						// Selecting a new piece
						if(currentPiece == null){
							currentPiece = newPiece;
							currentPiece.getAllPossibleMoves();
							if(!currentPiece.getColor().equals(currentPlayer)){
								currentPiece = null;
								return;
							}
							selectPiece(game);
						}
						// Selecting other piece of same color || Killing a piece
						else{
							if(currentPiece.getColor().equals(newPiece.getColor())){
								deselectPiece(false);
								currentPiece = newPiece;
								currentPiece.getAllPossibleMoves();
								selectPiece(game);
							}
							else{
								killPiece(cell);
							}
						}

					}
					// Dropping a piece on blank square
					else{
						dropPiece(cell);
					}
				}
				// Clicked on piece
				else{
					Piece newPiece = (Piece) target;
					Cell square = (Cell) newPiece.getParent();
					// Selecting a new piece
					if(currentPiece == null){
						currentPiece = newPiece;
						if(!currentPiece.getColor().equals(currentPlayer)){
							currentPiece = null;
							return;
						}
						selectPiece(game);
					}
					// Selecting other piece of same color || Killing a piece
					else{
						if(currentPiece.getColor().equals(newPiece.getColor())){
							deselectPiece(false);
							currentPiece = newPiece;
							selectPiece(game);
						}
						else{
							killPiece(square);
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
		currentPiece.setEffect(null);
		currentPiece.showAllPossibleMoves(false);
		currentPiece = null;
		if (changePlayer)
			currentPlayer = currentPlayer.equals("white") ? "black" : "white";
	}

	private void dropPiece(Cell cell) {
		if (!currentPiece.getPossibleMoves().contains(cell.getName()))
			return;

		Cell initialCell = (Cell) currentPiece.getParent();
		cell.getChildren().add(currentPiece);
		cell.setOccupied(true);
		initialCell.getChildren().removeAll();
		initialCell.setOccupied(false);
		currentPiece.setPosX(cell.getX());
		currentPiece.setPosY(cell.getY());
		deselectPiece(true);
	}

	private void killPiece(Cell cell) {
		if (!currentPiece.getPossibleMoves().contains(cell.getName()))
			return;

		Piece killedPiece = (Piece) cell.getChildren().get(0);
		if (killedPiece.getType().equals("King"))
			this.game = false;

		Cell initialSquare = (Cell) currentPiece.getParent();
		cell.getChildren().remove(0);
		cell.getChildren().add(currentPiece);
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
}