package Model;

import java.util.Random;

import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import java.util.ArrayList;
import java.util.Collections;

public class UndoCell extends Cell {

	public UndoCell(int x, int y) {
		super(x, y);
		this.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
	}

	public String toString() {
		String status;
		if (this.isOccupied())
			status = "Occupied";
		else
			status = "Not occupied";
//        return "Square" + this.x + this.y + " - " + status;
		return "Square";
	}
	
	/*
	 * This function undo the last 3 moves of the knight when gets stepped on,
	 * The lastMoves list has 4 values and we check the first 3.
	 * if the counter of the cell is 1 it means that the knight stepped on the cell only 1 time,
	 * so, the score should be deducted, and the cell should be marked as unvisited.
	 * if the counter is greater than 1 it means that the knight stepped on the cell more than 1 time,
	 * so the score should be added because it was deducted before, and the cell should still be visited,
	 * in both ways the counter should be deducted by 1.
	 * the function returns the new score.
	 */
	public int undoMoves(Board board, ArrayList<Cell> lastMoves, int score) {
		int i ;
		for(i=0;i<lastMoves.size()-1;i++) {
			Cell c = lastMoves.get(i);
			for (Cell temp : board.getCells()) {
				
				if (c == temp) {
						if(c.getCounter()>1) {
							c.setCounter(c.getCounter()-1);
							score++;
							break;
						}
						else if(c.getCounter()==1) {
							c.setCounter(c.getCounter()-1);
							c.setVisited(false);
							board.setTheme(c,board.getTheme(),c.getX(),c.getY());
							score--;
							break;
						}
					}
					
				}
			}
		return score;
	}
	/*
	 *This function creates a new undoCell instead of the one that the knight stepped on,
	 *it takes a random x,y and creates the undoCell. 
	 *and returns it.
	 */
	public Cell createNewUndoCell(Board board, Cell cell) {
		Knight tempKnight = (Knight) cell.getChildren().get(0);
		cell.getChildren().remove(0);
		Cell temp = null;
		int tempCounter = cell.getCounter();
		int i = cell.getX();
		int j = cell.getY();
		board.getCells().remove(cell);
		Cell newCell = new Cell(i, j);
		newCell.setName("Square" + i + j);
		newCell.setCounter(tempCounter);
		newCell.setPrefHeight(100);
		newCell.setPrefWidth(100);
		newCell.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
		newCell.getChildren().add(tempKnight);
		board.getCells().add(newCell);
		board.getChessBoard().add(newCell, newCell.getX(), newCell.getY(), 1, 1);
		board.setCells(board.getCells());
		newCell.setOccupied(false);
		newCell.setVisited(true);
		do {
			Random randI = new Random();
			Random randJ = new Random();
			int iRand = randI.nextInt(7);
			int jRand = randJ.nextInt(7);
			for (Cell c : board.getCells()) {
				if (c.getX() == iRand && c.getY() == jRand) {
					if (!(c instanceof BlockCell) && !(c instanceof QuesCell) && !(c instanceof JumpCell)
							&& !(c instanceof UndoCell)) {
						if (c.getChildren().isEmpty()) {
							if (c.getX() != 0 && c.getY() != 0) {
								temp = c;
								System.out.println(temp.getName());
							}
						}
					}
				}
			}

			if (temp != null) {
				board.getCells().remove(temp);
				temp = new UndoCell(iRand, jRand);
				temp.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
				temp.setName("Square" + iRand + jRand);
				temp.setPrefHeight(100);
				temp.setPrefWidth(100);
				board.getChessBoard().add(temp, iRand, jRand, 1, 1);
				board.getCells().add(temp);
			}
		} while (temp == null);

		return newCell;
	}

}