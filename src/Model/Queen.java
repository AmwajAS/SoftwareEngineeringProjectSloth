package Model;
import javax.swing.ImageIcon;

/**
 * Data structure to control a queen's movement
 * on a chess board.
 */
public class Queen {
	
    public static final int MAX_MOVE_NUM = 8;
   //public static final int[] horizontal  = new int[]{ 1, 1, 1, 0, 0, -1, -1, -1 };
   //public static final int[] vertical    = new int[]{ 1, 0, -1, 1, -1, 1, 0, -1 };
    private ImageIcon icon;
    private int currentRow;
    private int currentColumn;

    public Queen(String imageFile, int initialRow, int initialColumn) {
        setIcon(imageFile);
        this.currentRow = initialRow;
        this.currentColumn = initialColumn;
    }

	public ImageIcon getIcon() {
		return icon;
	}

    public void setIcon(String imageFile) {
        this.icon = new ImageIcon(imageFile);
    }

	public int getCurrentRow() {
		return currentRow;
	}

	public void setCurrentRow(int currentRow) {
		this.currentRow = currentRow;
	}

	public int getCurrentColumn() {
		return currentColumn;
	}

	public void setCurrentColumn(int currentColumn) {
		this.currentColumn = currentColumn;
	}

	public static int getMaxMoveNum() {
		return MAX_MOVE_NUM;
	}
/*
	public static int[] getHorizontal() {
		return horizontal;
	}

	public static int[] getVertical() {
		return vertical;
	}
	*/
    
    
}
