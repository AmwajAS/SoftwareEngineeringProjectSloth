package Model;

import java.util.Random;

import javafx.scene.effect.Effect;
import javafx.scene.effect.Glow;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;

public class JumpCell extends Cell {

	public JumpCell(int x, int y) {
		super(x, y);
	}

	@Override
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
	 * This function returns a random cell and it gets called when the knight steps on JumpCell cell.
	 */
	public Cell Jump(Board board) {
		Cell temp = null;
		do {
			Random randX = new Random();
			Random randY = new Random();
			int xRand = randX.nextInt(7);
			int yRand = randY.nextInt(7);
			for (Cell c : board.getCells()) {
				if ((c.getX() == xRand && c.getY() == yRand) && !(c.isOccupied()) && !(c instanceof JumpCell)) {
					temp = c;
				}
			}
		} while (temp == null);

		return temp;
	}
	/*
	 * After the knight steps on the JumpCell and jumps to a random spot, this function gets called in the Game.
	 * This function creates a new random JumpCell instead of the one the knight jumped on.
	 */
	public Cell createNewJumpCell(Board board, Cell cell) {
		Cell temp = null;
		int i = cell.getX();
		int j = cell.getY();
		int tempCounter = cell.getCounter();
		board.getCells().remove(cell);
		System.out.println("****This Is the new jumpcell****");
		Cell newCell = new Cell(i, j);
		newCell.setName("Square" + i + j);
		newCell.setCounter(tempCounter);
		newCell.setPrefHeight(100);
		newCell.setPrefWidth(100);
		newCell.setBorder(new Border(
				new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
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
					if (!(c instanceof BlockCell) && !(c instanceof QuesCell) && !(c instanceof JumpCell) && !(c instanceof UndoCell)) {
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
				temp = new JumpCell(iRand, jRand);
				temp.setBorder(new Border(
						new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
				temp.setName("Square" + iRand + jRand);
				temp.setPrefHeight(100);
				temp.setPrefWidth(100);
				board.getChessBoard().add(temp, iRand, jRand, 1, 1);
				board.getCells().add(temp);
			}
		} while (temp == null);

		return temp;
	}

}