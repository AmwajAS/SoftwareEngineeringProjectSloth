package Model;

import java.util.ArrayList;

import javax.swing.ImageIcon;

public class King extends Piece {
	public King(String color, int posX, int posY) {
		super(color, posX, posY);
		;
		this.setType("King");
		setImage();
	}

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