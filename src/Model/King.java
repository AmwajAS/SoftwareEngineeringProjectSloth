package Model;

import java.util.ArrayList;

/*
 * King extends from Piece
 */
public class King extends Piece {
	public King(String color, int posX, int posY) {
		super(color, posX, posY);
		;
		this.setType("King");
		setImage();
	}
	
	/*
	 * This function gets all the possible moves of the king.
	 * the king can move to all the 8 cells surrounding him.
	 * including moving from one side to another.
	 * The math in this move always gives back the 8 cells around him including the other side.
	 */
	@Override
	public void getAllPossibleMoves(int lvl) {
		int x = this.getPosX();
		int y = this.getPosY();
		ArrayList<String> moves = new ArrayList<>();
		this.setPossibleMoves(new ArrayList<>());

		moves.add("Square" + (((x+1) % 8 + 8) % 8 ) + (((y+0) % 8 + 8) % 8 ));
		moves.add("Square" + (((x+0) % 8 + 8) % 8 ) + (((y+1) % 8 + 8) % 8 ));
		moves.add("Square" + (((x-1) % 8 + 8) % 8 ) + (((y+0) % 8 + 8) % 8 ));
		moves.add("Square" + (((x+0) % 8 + 8) % 8 ) + (((y-1) % 8 + 8) % 8 ));
		moves.add("Square" + (((x+1) % 8 + 8) % 8 ) + (((y+1) % 8 + 8) % 8 ));
		moves.add("Square" + (((x+1) % 8 + 8) % 8 ) + (((y-1) % 8 + 8) % 8 ));
		moves.add("Square" + (((x-1) % 8 + 8) % 8 ) + (((y+1) % 8 + 8) % 8 ));
		moves.add("Square" + (((x-1) % 8 + 8) % 8 ) + (((y-1) % 8 + 8) % 8 ));
		

		
		/*
		 * after adding the moves, they get stored in the getPossibleMoves
		 */
		for (String move : moves) {
			if (getSquareByName(move) != null) {
				if (!(getSquareByName(move) instanceof BlockCell)) {
					if (getSquareByName(move).isOccupied()
							&& getPieceByName(move).getColor().equals("white"))
						continue;

					this.getPossibleMoves().add(move);

				}
			}
		}
	}

	@Override
	public String toString() {
		return "King [getType()=" + getType() + ", getPosX()=" + getPosX() + ", getPosY()=" + getPosY() + "]";
	}

}