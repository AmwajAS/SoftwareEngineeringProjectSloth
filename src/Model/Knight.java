package Model;
import javax.swing.ImageIcon;

/**
 * Data structure to control a knight's movement
 * on a chess board.
 */
public class Knight {
    public static final int MAX_MOVE_NUM = 8;
    public static final int[] horizontal  = new int[]{ 2, 1, -1, -2, -2, -1, 1, 2 };
    public static final int[] vertical    = new int[]{ -1, -2, -2, -1, 1, 2, 2, 1 };
    private ImageIcon icon;
    private int currentRow;
    private int currentColumn;

    /**
     * Initialize a knight to be used on a {@link Board}.
     *
     * @param imageFile the filename of the knight's icon image
     * @param initialRow the row where the knight starts the tour
     * @param initialColumn the column where the knight starts the tour
     */
    public Knight(String imageFile, int initialRow, int initialColumn) {
        setIcon(imageFile);
        this.currentRow = initialRow;
        this.currentColumn = initialColumn;
    }

    /**
     * Get a knight's icon image.
     *
     * @return the knight's icon image
     */
    public ImageIcon getIcon() {
        return icon;
    }

    /**
     * Set the knight's icon image displayed on a {@link Board}.
     *
     * @param imageFile the filename of the knight's icon image
     */
    public void setIcon(String imageFile) {
        this.icon = new ImageIcon(imageFile);
    }

    /**
     * Check if the knight can reach the destination tile with its L-shaped movement.
     *
     * @param nextRow the index of the row to which the knight will be moved
     * @param nextColumn the index of the column to which the knight will be moved
     * @return {@code true} if the move has a valid shape. Otherwise, {@code false}
     */
    public boolean isValidMoveShape(int nextRow, int nextColumn) {
        int rowDiff = Math.abs(nextRow - currentRow);
        int columnDiff = Math.abs(nextColumn - currentColumn);

        return (rowDiff == 1 && columnDiff == 2) || (rowDiff == 2 && columnDiff == 1);
    }

    /**
     * Generate the next possible tiles to which the knight.
     *
     * The coordinates of them are calculated based on the knight's current position.
     * However, the returned tiles may be outside the chessboard or visited by the
     * knight already. Therefore, the {@link Board} needs to validate them before use.
     *
     * @return an array of possible reachable tiles
     */
    public int[][] nextDestinations() {
        int[][] nextTiles = new int[MAX_MOVE_NUM][2];

        for (int moveNumber = 0; moveNumber < MAX_MOVE_NUM; moveNumber++) {
            nextTiles[moveNumber][0] = currentRow + vertical[moveNumber];
            nextTiles[moveNumber][1] = currentColumn + horizontal[moveNumber];
        }

        return nextTiles;
    }

    /**
     * Generate the next possible tiles to which the knight.
     *
     * The coordinates of them are calculated based on specified row and column.
     * However, the returned tiles may be outside the chessboard or visited by the
     * knight already. Therefore, the {@link Board} needs to validate them before use.
     *
     * @param row the row from which the result is calculated
     * @param column the column from which the result is calculated
     * @return an array of possible reachable tiles
     */
    public int[][] nextDestinations(int row, int column) {
        int[][] nextTiles = new int[MAX_MOVE_NUM][2];

        for (int moveNumber = 0; moveNumber < MAX_MOVE_NUM; moveNumber++) {
            nextTiles[moveNumber][0] = row + vertical[moveNumber];
            nextTiles[moveNumber][1] = column + horizontal[moveNumber];
        }

        return nextTiles;
    }

    /**
     * Move the knight to the next tile based on a move number.
     *
     * @param moveNumber a number between 0 and 7
     */
    public boolean move(int moveNumber) {
        if (moveNumber < MAX_MOVE_NUM) {
            currentRow += vertical[moveNumber];
            currentColumn += horizontal[moveNumber];
            return true;
        }
        else
            return false;
    }

    /**
     * Move the knight to a specified tile.
     *
     * @param nextRow the row to which the knight move
     * @param nextColumn the column to which the knight
     * @return {@code true} if the move can be made. Otherwise, {@code false}.
     */
    public boolean move(int nextRow, int nextColumn) {
        if (isValidMoveShape(nextRow, nextColumn)) {
            currentRow = nextRow;
            currentColumn = nextColumn;
            return true;
        }

        return false;
    }

    /**
     * Get the row where the knight is currently located.
     */
    public int getCurrentRow() {
        return currentRow;
    }

    /**
     * Get the column where the knight is currently located.
     */
    public int getCurrentColumn() {
        return currentColumn;
    }

}
