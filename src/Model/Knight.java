package Model;

import javax.swing.ImageIcon;

import java.util.ArrayList;
/*
 * Knight extends from Piece
 */
public class Knight extends Piece {
	public Knight(String color, int posX, int posY) {
		super(color, posX, posY);
		this.setType("Knight");
		setImage();
	}

	/*
	 * This function calculates all the possible moves to the knight, 
	 * depends on the level.
	 * lvl 1 : the knight moves like a normal knight in chess.
	 * lvl 2-4 : the knight moves 2 steps forward and 1 diagonal or 2 diagonal and 1 forward.
	 * We took the moves that only go forward in case of lvl 2-4. which means he has 16 moves.
	 * in all the lvls the knight can go from one side to another
	 * the math makes sure that all the moves are added, including going form one side to another.
	 */
	@Override
	public void getAllPossibleMoves(int lvl) {
		int x = this.getPosX();
		int y = this.getPosY();
		ArrayList<String> moves = new ArrayList<>();
		this.setPossibleMoves(new ArrayList<>());

		if (lvl == 1) {
			moves.add("Square" + (((x + 2) % 8 + 8) % 8) + (((y + 1) % 8 + 8) % 8));
			moves.add("Square" + (((x + 2) % 8 + 8) % 8) + (((y - 1) % 8 + 8) % 8));
			moves.add("Square" + (((x + 1) % 8 + 8) % 8) + (((y + 2) % 8 + 8) % 8));
			moves.add("Square" + (((x - 1) % 8 + 8) % 8) + (((y + 2) % 8 + 8) % 8));
			moves.add("Square" + (((x - 2) % 8 + 8) % 8) + (((y + 1) % 8 + 8) % 8));
			moves.add("Square" + (((x - 2) % 8 + 8) % 8) + (((y - 1) % 8 + 8) % 8));
			moves.add("Square" + (((x + 1) % 8 + 8) % 8) + (((y - 2) % 8 + 8) % 8));
			moves.add("Square" + (((x - 1) % 8 + 8) % 8) + (((y - 2) % 8 + 8) % 8));
		} else {
			moves.add("Square" + (((x + 3) % 8 + 8) % 8) + (((y + 1) % 8 + 8) % 8));
			moves.add("Square" + (((x + 3) % 8 + 8) % 8) + (((y - 1) % 8 + 8) % 8));
			moves.add("Square" + (((x + 1) % 8 + 8) % 8) + (((y + 3) % 8 + 8) % 8));
			moves.add("Square" + (((x - 1) % 8 + 8) % 8) + (((y + 3) % 8 + 8) % 8));
			moves.add("Square" + (((x - 3) % 8 + 8) % 8) + (((y + 1) % 8 + 8) % 8));
			moves.add("Square" + (((x - 3) % 8 + 8) % 8) + (((y - 1) % 8 + 8) % 8));
			moves.add("Square" + (((x + 1) % 8 + 8) % 8) + (((y - 3) % 8 + 8) % 8));
			moves.add("Square" + (((x - 1) % 8 + 8) % 8) + (((y - 3) % 8 + 8) % 8));
			
			moves.add("Square" + (((x + 3) % 8 + 8) % 8) + (((y + 2) % 8 + 8) % 8));
			moves.add("Square" + (((x + 3) % 8 + 8) % 8) + (((y - 2) % 8 + 8) % 8));
			moves.add("Square" + (((x + 2) % 8 + 8) % 8) + (((y + 3) % 8 + 8) % 8));
			moves.add("Square" + (((x - 2) % 8 + 8) % 8) + (((y + 3) % 8 + 8) % 8));
			moves.add("Square" + (((x - 3) % 8 + 8) % 8) + (((y + 2) % 8 + 8) % 8));
			moves.add("Square" + (((x - 3) % 8 + 8) % 8) + (((y - 2) % 8 + 8) % 8));
			moves.add("Square" + (((x + 2) % 8 + 8) % 8) + (((y - 3) % 8 + 8) % 8));
			moves.add("Square" + (((x - 2) % 8 + 8) % 8) + (((y - 3) % 8 + 8) % 8));
		}
		
		/*
		 * after adding the moves they get stored in getPossibleMoves.
		 */
		for (String move : moves) {
			if (getSquareByName(move) != null) {
				if (getSquareByName(move).isOccupied() && !(getSquareByName(move) instanceof BlockCell))
					continue;

				this.getPossibleMoves().add(move);

			}
		}

	}

	@Override
	public String toString() {
		return "Knight [getType()=" + getType() + ", getPosX()=" + getPosX() + ", getPosY()=" + getPosY() + "]";
	}

}