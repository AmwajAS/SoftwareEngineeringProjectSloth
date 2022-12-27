package Model;

public class BlockCell extends Cell{

	public BlockCell(int x, int y) {
		super(x, y);
		this.setStyle("-fx-background-color: black;");
	}

	@Override
	public String toString() {
		return "BlockCell [getX()=" + getX() + ", getY()=" + getY() + ", isOccupied()=" + isOccupied() + ", getName()="
				+ getName() + ", isVisited()=" + isVisited() + "]";
	}

}