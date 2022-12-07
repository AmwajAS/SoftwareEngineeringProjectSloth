package Model;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.LineBorder;

/**
 * Data structure for managing interaction with a board.
 */
public class Board {
    public static final int BOARD_SIZE = 8;
    private int[][] nextTiles = new int[BOARD_SIZE][2];
    private Cell[][] tiles = new Cell[BOARD_SIZE][BOARD_SIZE];
    private int visitedTileCounter;
    private Knight knight;

    /**
     * Construct an empty board with white and black tiles.
     * check line 25 creating a new Cell();
     */
    public Board() {
        TileHandler tileHandler = new TileHandler();

        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int column = 0; column < BOARD_SIZE; column++) {
                tiles[row][column] = new Cell(row,column);
                if ((row + column) % 2 == 0) {
                    tiles[row][column].setBackground(Color.white);
                }
                else {
                    tiles[row][column].setBackground(Color.black);
                }
                // Display colors on MacOS
                tiles[row][column].setOpaque(true);
                tiles[row][column].setBorder(new LineBorder(Color.black));
                tiles[row][column].addActionListener(tileHandler);
            }
        }
    }

    /**
     * Reset the board to an empty state.
     *
     * Firstly, repaint all the tiles to their initial color (black and white).
     * Secondly, remove the knight's icon from the board. Thirdly, reset the
     * counter for visited tiles. Lastly, set the suggestion for next moves to
     * a safe empty state.
     */
    public void resetBoard() {
        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int column = 0; column < BOARD_SIZE; column++) {
                tiles[row][column].setText("");
                if ((row + column) % 2 == 0) {
                    tiles[row][column].setBackground(Color.white);
                }
                else {
                    tiles[row][column].setBackground(Color.black);
                }
            }
        }
        //CHECK knight's ICON!!!!!!!!
        tiles[knight.getCurrentRow()][knight.getCurrentColumn()].setIcon(knight.getIcon());

        visitedTileCounter = 0;
    }

    /**
     * Get the tile at a specified coordinate.
     *
     * @param row the row where the tile is located
     * @param column the column where the tile is located
     * @return a tile of the board
     * Checking if not within bound calculating to the other side!!!
     */
    public JButton getTile(int row, int column) {
        if (isWithinBound(row, column))
            return tiles[row][column];
        else
            return null;
    }

    /**
     * Check if a tile is inside the board.
     *
     * @param row the row where the tile is located
     * @param column the column where the tile is located
     * @return {@code true} if the tile is located inside
     *          the board. Otherwise, {@code false}.
     */
    public boolean isWithinBound(int row, int column) {
        return (row >= 0 && column >= 0) && (row < BOARD_SIZE && column < BOARD_SIZE);
    }
    //writing a function that calculates what happens if not within bound!!

    /**
     * Identify a tile as already visited.
     *
     * @param row the row where the tile is located
     * @param column the column where the tile is located
     */
    public void markAsVisited(int row, int column) {
        if (isWithinBound(row, column) && isNotVisited(row, column)) {
            visitedTileCounter++;
            tiles[row][column].setIcon(knight.getIcon());
            tiles[row][column].setBackground(Color.orange);
            tiles[row][column].setText("" + visitedTileCounter);
        }
    }

    /**
     * Check if a tile has been visited.
     *
     * @param row the row where the tile is located
     * @param column the column where the tile is located
     * @return {@code true} if the tile can be visited.
     *          Otherwise, {@code false}
     */
    public boolean isNotVisited(int row, int column) {
        return !(tiles[row][column].getBackground() == Color.orange);
    }

    /**
     * Paint reachable tiles to suggest the next moves for the knight.
     */
    private void displayMoveSuggestion() {
        int validMoveCounter = 0;

        nextTiles = knight.nextDestinations();
        for (int moveNumber = 0; moveNumber < Knight.MAX_MOVE_NUM; moveNumber++) {
            int nextRow = nextTiles[moveNumber][0];
            int nextColumn = nextTiles[moveNumber][1];
            if (isWithinBound(nextRow, nextColumn) && isNotVisited(nextRow, nextColumn)) {
                tiles[nextRow][nextColumn].setBackground(Color.green);
                validMoveCounter++;
            }
        }

        if (validMoveCounter == 0) {
            JOptionPane.showMessageDialog(null, "Out of valid moves!", "Tour Ended", JOptionPane.PLAIN_MESSAGE);
        }
    }

    /**
     * Repaint the tiles that were painted for move suggestion.
     */
    private void clearMoveSuggestion() {
        for (int moveNumber = 0; moveNumber < Knight.MAX_MOVE_NUM; moveNumber++) {
            int nextRow = nextTiles[moveNumber][0];
            int nextColumn = nextTiles[moveNumber][1];
            if (isWithinBound(nextRow, nextColumn) && tiles[nextRow][nextColumn].getBackground() == Color.green) {
                if ((nextRow + nextColumn) % 2 == 0) {
                    tiles[nextRow][nextColumn].setBackground(Color.white);
                }
                else {
                    tiles[nextRow][nextColumn].setBackground(Color.black);
                }
            }
        }
    }

    /**
     * Calculate the accessibility number of a tile.
     *
     * @param row the row where the tile is located
     * @param column the column where the tile is located
     * @return the number of tiles from which the knight can reach
     *         the specified one.
     */
    /*
    private int getAccessibilityScore(int row, int column) {
        int accessibilityScore = 0;

        for (int moveNumber = 0; moveNumber < Knight.MAX_MOVE_NUM; moveNumber++) {
            int neighborRow = row + Knight.vertical[moveNumber];
            int neighborColumn = column + Knight.horizontal[moveNumber];

            if (isWithinBound(neighborRow, neighborColumn) && isNotVisited(neighborRow, neighborColumn)) {
                accessibilityScore++;
            }
        }

        return accessibilityScore;
    }
    */

    /**
     * Get the minimum accessibility score in a collection of tiles suggested for the next moves.
     *
     * Firstly, the next possible destinations are validated.
     * Then, an accessibility score is found for each destination.
     *
     * @param nextTiles a collection of next possible destinations for the tour
     * @return the lowest accessibility score among the tiles
     */
    /*
    private int getMinAccessibilityScore(int[][] nextTiles) {
        int minScore = Knight.MAX_MOVE_NUM;

        for (int moveNumber = 0; moveNumber < Knight.MAX_MOVE_NUM; moveNumber++) {
            int row = nextTiles[moveNumber][0];
            int column = nextTiles[moveNumber][1];

            if (isWithinBound(row, column) && isNotVisited(row, column)) {
                minScore = Math.min(minScore, getAccessibilityScore(row, column));
            }
        }

        return minScore;
    }
    */

    /**
     * Get the optimal move number from the suggested moves.
     *
     * Firstly, the next possible destinations are validated.
     * Then, an accessibility score is found for each destination.
     * If optimization mode is on, look ahead to those squares
     * reachable from "tied" squares to decide on the best one based
     * on their next minimum accessibility score.
     *
     * @param nextTiles a collection of next possible destinations for the tour
     * @param optimizedTiedSquares turn on/off optimization mode when "tied" squares
     *                             are encountered
     * @return the optimal move number that a knight can make
     */
    /*
    private int getOptimalMoveNumber(int[][] nextTiles, boolean optimizedTiedSquares) {
        int minScore = Knight.MAX_MOVE_NUM, optimalMoveNumber = -1;

        for (int moveNumber = 0; moveNumber < Knight.MAX_MOVE_NUM; moveNumber++) {
            int nextRow = nextTiles[moveNumber][0];
            int nextColumn = nextTiles[moveNumber][1];

            if (isWithinBound(nextRow, nextColumn) && isNotVisited(nextRow, nextColumn)) {
                int score = getAccessibilityScore(nextRow, nextColumn);
                if (score < minScore) {
                    minScore = score;
                    optimalMoveNumber = moveNumber;
                }
                else if (optimizedTiedSquares && score == minScore) {
                    int[][] currOptimalNextTiles = knight.nextDestinations(nextTiles[optimalMoveNumber][0], nextTiles[optimalMoveNumber][1]);
                    int[][] tiedSquareNextTiles = knight.nextDestinations(nextTiles[moveNumber][0], nextTiles[moveNumber][1]);

                    if (getMinAccessibilityScore(tiedSquareNextTiles) < getMinAccessibilityScore(currOptimalNextTiles)) {
                        optimalMoveNumber = moveNumber;
                    }
                }
            }
        }

        return optimalMoveNumber;
    }
    */

    /**
     * Move the knight based on the heuristic approach.
     *
     * Firstly, the next possible destinations for the tour are generated.
     * Then, the optimal move is found. The knight moves to that tile.
     * If no optimal move is found, an error message is displayed.
     *
     * @param optimizedTiedSquares turn on/off optimization mode when "tied" squares
     *                             are encountered
     * @return {@code true} if the knight can make a valid move. Otherwise, {@code false}.
     */
    /*
    private boolean moveKnight(boolean optimizedTiedSquares) {
        nextTiles = knight.nextDestinations();

        int optimalMoveNumber = getOptimalMoveNumber(nextTiles, optimizedTiedSquares);

        if (optimalMoveNumber <= -1) {
            JOptionPane.showMessageDialog(null, "Out of valid moves!", "Tour Ended", JOptionPane.PLAIN_MESSAGE);
        }
        else {
            tiles[knight.getCurrentRow()][knight.getCurrentColumn()].setIcon(null);
            if (knight.move(optimalMoveNumber)) {
                markAsVisited(nextTiles[optimalMoveNumber][0], nextTiles[optimalMoveNumber][1]);
                return true;
            }
        }

        return false;
    }
    */

    /**
     * Make a tour of the board with a knight using the heuristic approach.
     *
     * @param intialRow the starting row of the tour
     * @param intialColumn the starting column of the tour
     * @param optimized turn on/off optimization mode
     */
    /*
    public void runTour(int intialRow, int intialColumn, boolean optimized) {
        knight = new Knight("knight.png", intialRow, intialColumn);
        markAsVisited(intialRow, intialColumn);

        while (visitedTileCounter < BOARD_SIZE * BOARD_SIZE && moveKnight(optimized)) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        if (visitedTileCounter >= BOARD_SIZE * BOARD_SIZE) {
            JOptionPane.showMessageDialog(null, "All tiles have been visited!", "Full Tour", JOptionPane.PLAIN_MESSAGE);
        }
    }
    */


    /**
     * A class for handling the user's interaction with the tiles on a board.
     */
    private class TileHandler implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            Object source = e.getSource();
            // Clear any move suggestion
            clearMoveSuggestion();

            for (int row = 0; row < BOARD_SIZE; row++) {
                for (int column = 0; column < BOARD_SIZE; column++) {
                    // Attempt to move the knight to the clicked and unvisited tile
                    if (source == tiles[row][column] && isNotVisited(row, column)) {
                        // Initialize the knight on its first move
                        if (visitedTileCounter == 0) {
                            knight = new Knight("src/images/knighticon.png", row, column);
                            // Mark the tile as visited
                            markAsVisited(row, column);
                        }
                        else {
                            // Remove the knight from the previous position
                            tiles[knight.getCurrentRow()][knight.getCurrentColumn()].setIcon(null);
                            // Move the knight to the new position
                            if (knight.move(row, column)) {
                                // Mark the tile as visited
                                markAsVisited(row, column);
                            }
                            // If fails, put the knight back
                            else {
                                tiles[knight.getCurrentRow()][knight.getCurrentColumn()].setIcon(knight.getIcon());
                            }
                        }
                    }
                }
            }

            // Check if all tiles have been visited
            if (visitedTileCounter >= BOARD_SIZE * BOARD_SIZE) {
                JOptionPane.showMessageDialog(null, "All tiles have been visited!", "Full Tour", JOptionPane.PLAIN_MESSAGE);
            }
            else {
                // Suggest the next available moves
                displayMoveSuggestion();
            }
        }
    }

}
