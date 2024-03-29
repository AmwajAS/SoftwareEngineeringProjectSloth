package Model;

import java.util.Random;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;


public class QuesCell extends Cell{
	private Question question;
	final String image = "/images/questionMark.png";
	public QuesCell(int x, int y, Question q, Game g) {
		super(x, y);
		this.question=q;
	}
	//The question cell needs to have a question mark showed on top of it.
	//the style takes the image and sets it.
	public QuesCell(int x, int y) {
		super(x, y);
		this.setStyle("-fx-background-image: url('"+image+"'); -fx-background-size: 100px 100px; -fx-background-position: center center; -fx-background-repeat:no-repeat;");
		this.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
	}

	public Question getQuestion() {
		return question;
	}

	public void setQuestion(Question question) {
		this.question = question;
	}
	
	/*
	 * This function creates a new Question cell instead of the one that the knight stepped on, 
	 * it takes a random x,y and creates a QuesCell .
	 */
	public Cell createNewQuesCell(Board board, Cell cell) {
		Knight tempKnight = (Knight) cell.getChildren().get(0);
		cell.getChildren().remove(0);
		Cell temp = null;
		int i = cell.getX();
		int j = cell.getY();
		board.getCells().remove(cell);
		Cell newCell = new Cell(i, j);
		newCell.setName("Square" + i + j);
		newCell.setPrefHeight(100);
		newCell.setPrefWidth(100);
		newCell.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
		newCell.getChildren().add(tempKnight);
		board.getCells().add(newCell);
		board.getChessBoard().add(newCell, newCell.getX(), newCell.getY(), 1, 1);
		board.setCells(board.getCells());
		newCell.setOccupied(true);
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

							}
						}
					}
				}
			}

			if (temp != null) {
				board.getCells().remove(temp);
				temp = new QuesCell(iRand, jRand);
				temp.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
				temp.setName("Square" + iRand + jRand);
				temp.setPrefHeight(100);
				temp.setPrefWidth(100);
				board.getChessBoard().add(temp, iRand, jRand, 1, 1);
				board.getCells().add(temp);
			}
		} while (temp == null);

		return temp;
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

}