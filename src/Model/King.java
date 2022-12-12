package Model;
import java.util.ArrayList;

import javax.swing.ImageIcon;

public class King extends Piece{
    public King(String color, int posX, int posY) {
        super(color, posX, posY);;
        this.setType("King");
        setImage();
    }

    @Override
    public void getAllPossibleMoves() {
        int x = this.getPosX();
        int y = this.getPosY();
        ArrayList<String> moves = new ArrayList<>();
        this.setPossibleMoves(new ArrayList<>());

        moves.add("Square" + (x) + (y-1));
        moves.add("Square" + (x+1) + (y-1));
        moves.add("Square" + (x+1) + (y));
        moves.add("Square" + (x+1) + (y+1));
        moves.add("Square" + (x) + (y+1));
        moves.add("Square" + (x-1) + (y+1));
        moves.add("Square" + (x-1) + (y));
        moves.add("Square" + (x-1) + (y-1));


        for(String move : moves){
            if(getSquareByName(move) != null){
                if(getSquareByName(move).isOccupied() && getPieceByName(move).getColor().equals(Game.currentPlayer)) continue;

                this.getPossibleMoves().add(move);

            }
        }
    }

	@Override
	public String toString() {
		return "King [getType()=" + getType() + ", getPosX()=" + getPosX() + ", getPosY()=" + getPosY()+ "]";
	}
    
}