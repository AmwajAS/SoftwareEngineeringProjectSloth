package Model;
public class UndoCell extends Cell{

	public UndoCell(int x, int y) {
		super(x, y);
	}

	@Override
	public String toString() {
		return "UndoCell [getX()=" + getX() + ", getY()=" + getY() + ", isOccupied()=" + isOccupied() + ", getName()="
				+ getName() + ", isVisited()=" + isVisited() + "]";
	}
	

}
