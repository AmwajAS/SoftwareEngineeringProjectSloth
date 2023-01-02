package Model;

import javax.swing.ImageIcon;

import java.util.ArrayList;

public class Knight extends Piece {
	public Knight(String color, int posX, int posY) {
		super(color, posX, posY);
		this.setType("Knight");
		setImage();
	}

	@Override
	public void getAllPossibleMoves(int lvl) {
		int x = this.getPosX();
		int y = this.getPosY();
		ArrayList<String> moves = new ArrayList<>();
		this.setPossibleMoves(new ArrayList<>());

		// this.possibleMoves = new ArrayList<>();
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

		for (String move : moves) {
			if (getSquareByName(move) != null) {
				if (getSquareByName(move).isOccupied() && !(getSquareByName(move) instanceof BlockCell)
						&& getPieceByName(move).getColor().equals(Game.currentPlayer))
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