package Model;
import javax.swing.JButton;

public class Cell  extends JButton{
	private int row;
	private int column;
	
	public Cell(int row,int column) {
		this.row=row;
		this.column=column;
	}
	
	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
	}

	public int getColumn() {
		return column;
	}

	public void setColumn(int column) {
		this.column = column;
	}	
}
