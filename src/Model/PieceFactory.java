package Model;
//Design Pattern for the inherited cell's classes.
public class PieceFactory {
	
	public static Piece newPieceByFactory(String type,String color, int posX, int posY) {
		if(type == null){
			return null;
		}

		if(type.equalsIgnoreCase("Knight")) {
			return new Knight(color, posX, posY);

		}else if (type.equalsIgnoreCase("Queen")) {
			return new Queen(color, posX, posY);

		}else if (type.equalsIgnoreCase("King")) {
			return new King(color, posX, posY);
		}
		return null;
	}

}
