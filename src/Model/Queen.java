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
	        this.type = "Queen";
	        setImage();
	    }

	    @Override
	    public void getAllPossibleMoves() {
	        int x = this.posX;
	        int y = this.posY;
	        String name;

	        this.possibleMoves = new ArrayList<>();

	        for(int i=x-1; i>=0; i--){
	            name = "Square" + i + y;
	            if(getSquareByName(name).occupied && getPieceByName(name).getColor().equals(Game.currentPlayer)) break;

	            possibleMoves.add(name);

	            if(getSquareByName(name).occupied && !getPieceByName(name).getColor().equals(Game.currentPlayer)) break;
	        }

	        for(int i=x+1; i<8; i++){
	            name = "Square" + i + y;
	            if(getSquareByName(name).occupied && getPieceByName(name).getColor().equals(Game.currentPlayer)) break;

	            possibleMoves.add(name);

	            if(getSquareByName(name).occupied && !getPieceByName(name).getColor().equals(Game.currentPlayer)) break;
	        }

	        for(int j=y-1; j>=0; j--){
	            name = "Square" + x + j;
	            if(getSquareByName(name).occupied && getPieceByName(name).getColor().equals(Game.currentPlayer)) break;

	            possibleMoves.add(name);

	            if(getSquareByName(name).occupied && !getPieceByName(name).getColor().equals(Game.currentPlayer)) break;
	        }

	        for(int j=y+1; j<8; j++){
	            name = "Square" + x + j;
	            if(getSquareByName(name).occupied && getPieceByName(name).getColor().equals(Game.currentPlayer)) break;

	            possibleMoves.add(name);

	            if(getSquareByName(name).occupied && !getPieceByName(name).getColor().equals(Game.currentPlayer)) break;
	        }

	        for(int i=x-1, j=y+1; i>=0 && j<8; i--, j++){
	            name = "Square" + i + j;
	            if(getSquareByName(name).occupied && getPieceByName(name).getColor().equals(Game.currentPlayer)) break;

	            possibleMoves.add(name);

	            if(getSquareByName(name).occupied && !getPieceByName(name).getColor().equals(Game.currentPlayer)) break;
	        }

	        for(int i=x+1, j=y+1; i<8 && j<8; i++, j++){
	            name = "Square" + i + j;
	            if(getSquareByName(name).occupied && getPieceByName(name).getColor().equals(Game.currentPlayer)) break;

	            possibleMoves.add(name);

	            if(getSquareByName(name).occupied && !getPieceByName(name).getColor().equals(Game.currentPlayer)) break;
	        }

	        for(int i=x+1, j=y-1; i<8 && j>=0; i++, j--){
	            name = "Square" + i + j;
	            if(getSquareByName(name).occupied && getPieceByName(name).getColor().equals(Game.currentPlayer)) break;

	            possibleMoves.add(name);

	            if(getSquareByName(name).occupied && !getPieceByName(name).getColor().equals(Game.currentPlayer)) break;
	        }

	        for(int i=x-1, j=y-1; i>=0 && j>=0; i--, j--){
	            name = "Square" + i + j;
	            if(getSquareByName(name).occupied && getPieceByName(name).getColor().equals(Game.currentPlayer)) break;

	            possibleMoves.add(name);

	            if(getSquareByName(name).occupied && !getPieceByName(name).getColor().equals(Game.currentPlayer)) break;
	        }


	    }
	}
    
    

