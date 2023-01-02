package Model;
import java.util.ArrayList;

import javax.swing.ImageIcon;

/**
 * Data structure to control a queen's movement
 * on a chess board.
 */

public class Queen extends Piece {
	public Queen(String color, int posX, int posY) {
		super(color, posX, posY);
		this.setType("Queen");
		setImage();
	}

	@Override
	public void getAllPossibleMoves(int lvl) {
		int x = this.getPosX();
		int y = this.getPosY();
		String name;
		this.setPossibleMoves(new ArrayList<>());


		for(int i=x-1; i>=0; i--){
			name = "Square" + i + y;
			if(getSquareByName(name).isOccupied() && getPieceByName(name).getColor().equals(Game.currentPlayer)) break;

			getPossibleMoves().add(name);

			if(getSquareByName(name).isOccupied() && !getPieceByName(name).getColor().equals(Game.currentPlayer)) break;
		}

		for(int i=x+1; i<8; i++){
			name = "Square" + i + y;
			if(getSquareByName(name).isOccupied() && getPieceByName(name).getColor().equals(Game.currentPlayer)) break;

			getPossibleMoves().add(name);

			if(getSquareByName(name).isOccupied() && !getPieceByName(name).getColor().equals(Game.currentPlayer)) break;
		}

		for(int j=y-1; j>=0; j--){
			name = "Square" + x + j;
			if(getSquareByName(name).isOccupied() && getPieceByName(name).getColor().equals(Game.currentPlayer)) break;

			getPossibleMoves().add(name);

			if(getSquareByName(name).isOccupied() && !getPieceByName(name).getColor().equals(Game.currentPlayer)) break;
		}

		for(int j=y+1; j<8; j++){
			name = "Square" + x + j;
			if(getSquareByName(name).isOccupied() && getPieceByName(name).getColor().equals(Game.currentPlayer)) break;

			getPossibleMoves().add(name);

			if(getSquareByName(name).isOccupied() && !getPieceByName(name).getColor().equals(Game.currentPlayer)) break;
		}

		for(int i=x-1, j=y+1; i>=0 && j<8; i--, j++){
			name = "Square" + i + j;
			if(getSquareByName(name).isOccupied() && getPieceByName(name).getColor().equals(Game.currentPlayer)) break;

			getPossibleMoves().add(name);

			if(getSquareByName(name).isOccupied() && !getPieceByName(name).getColor().equals(Game.currentPlayer)) break;
		}

		for(int i=x+1, j=y+1; i<8 && j<8; i++, j++){
			name = "Square" + i + j;
			if(getSquareByName(name).isOccupied() && getPieceByName(name).getColor().equals(Game.currentPlayer)) break;

			getPossibleMoves().add(name);

			if(getSquareByName(name).isOccupied() && !getPieceByName(name).getColor().equals(Game.currentPlayer)) break;
		}

		for(int i=x+1, j=y-1; i<8 && j>=0; i++, j--){
			name = "Square" + i + j;
			if(getSquareByName(name).isOccupied() && getPieceByName(name).getColor().equals(Game.currentPlayer)) break;

			getPossibleMoves().add(name);

			if(getSquareByName(name).isOccupied() && !getPieceByName(name).getColor().equals(Game.currentPlayer)) break;
		}

		for(int i=x-1, j=y-1; i>=0 && j>=0; i--, j--){
			name = "Square" + i + j;
			if(getSquareByName(name).isOccupied() && getPieceByName(name).getColor().equals(Game.currentPlayer)) break;

			getPossibleMoves().add(name);

			if(getSquareByName(name).isOccupied() && !getPieceByName(name).getColor().equals(Game.currentPlayer)) break;
		}

	}

	@Override
	public String toString() {
		return "Queen [getType()=" + getType() + ", getPosX()=" + getPosX() + ", getPosY()=" + getPosY() + "]";
	}
	
}