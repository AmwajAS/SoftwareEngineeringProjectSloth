package Model;

import java.util.ArrayList;
import java.util.Random;
import Controller.MainMenuController;
import javafx.geometry.Insets;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.effect.Glow;


/**
 * Data structure for managing interaction with a board.*
 */
public class Board {
	Color color = Color.web("#F0E68C");
	private int level;
	private GridPane chessBoard;
	private String theme;
	private ArrayList<Cell> cells = new ArrayList<>();

	public Board(GridPane chessBoard, String theme, int lvl) {
		this.level = lvl;
		this.chessBoard = chessBoard;
		this.theme = MainMenuController.getThemeSelected();
		makeBoard(this.chessBoard, theme);
	}

	public GridPane getChessBoard() {
		return chessBoard;
	}

	public void setChessBoard(GridPane chessBoard) {
		this.chessBoard = chessBoard;
	}

	public String getTheme() {
		return theme;
	}

	public void setTheme(String theme) {
		this.theme = theme;
	}

	public ArrayList<Cell> getCells() {
		return cells;
	}

	public void setCells(ArrayList<Cell> cells) {
		this.cells = cells;
	}

	private void makeBoard(GridPane chessBoard, String theme) {
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				Cell cell = new Cell(i, j);
				cell.setName("Square" + i + j);
				cell.setPrefHeight(100);
				cell.setPrefWidth(100);
				cell.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY,
						BorderWidths.DEFAULT)));
				setTheme(cell, theme, i, j);
				chessBoard.add(cell, i, j, 1, 1);
				cells.add(cell);
			}
		}
		/*
		 * creating 8 blocked cells in level 4 making sure that there is 8 different
		 * blocked cells blocked cells are not under each of the pieces
		 */
		if (level == 1) {
			createJumpCell(3);
			createQuesCell(3);
		}
		if(level == 2) {
			createUndoCell(3);
			createQuesCell(3);

		}
		if (level == 3) {
			createJumpCell(2);
			createUndoCell(2);
			createQuesCell(3);

		}
		
		
		if (level == 4) {
			int eightTimes = 0;
			while (eightTimes < 8) {
				Random randI = new Random();
				Random randJ = new Random();
				int iRand = randI.nextInt(7);
				int jRand = randJ.nextInt(7);
				Cell temp = null;
				for (Cell c : cells) {
					if (c.getX() == iRand && c.getY() == jRand) {
						if (!(c instanceof BlockCell) && !(c instanceof QuesCell)) {
							if (c.getChildren().isEmpty()) {
								if (c.getX() != 0 && c.getY() != 0) {
									temp = c;
									eightTimes++;
								}
							}
						}
					}
				}
				if (temp != null) {
					cells.remove(temp);
					temp = new BlockCell(iRand, jRand);
					temp.setName("Square" + iRand + jRand);
					temp.setPrefHeight(100);
					temp.setPrefWidth(100);
					chessBoard.add(temp, iRand, jRand, 1, 1);
					cells.add(temp);
				}
			}
		}
		addPieces();
	}
	
	// Creating JumpCells
	private void createJumpCell(int times) {
		while (times > 0) {
			Random randI = new Random();
			Random randJ = new Random();
			int iRand = randI.nextInt(7);
			int jRand = randJ.nextInt(7);
			Cell temp = null;
			for (Cell c : cells) {
				if (c.getX() == iRand && c.getY() == jRand) {
					if (!(c instanceof BlockCell) && !(c instanceof QuesCell) && !(c instanceof JumpCell) && !(c instanceof UndoCell)) {
						if (c.getChildren().isEmpty()) {
							if (c.getX() != 0 && c.getY() != 0) {
								temp = c;
								times--;
								System.out.println(temp.getName());
							}
						}
					}
				}
			}
			if (temp != null) {
				cells.remove(temp);
				temp = new JumpCell(iRand, jRand);
				temp.setName("Square" + iRand + jRand);
				temp.setPrefHeight(100);
				temp.setPrefWidth(100);
				chessBoard.add(temp, iRand, jRand, 1, 1);
				cells.add(temp);
			}

		}
	}
	// Creating UndoCells
	private void createUndoCell(int times) {
		System.out.println(" * These are the UndoCells *");
		while (times > 0) {
			Random randI = new Random();
			Random randJ = new Random();
			int iRand = randI.nextInt(7);
			int jRand = randJ.nextInt(7);
			Cell temp = null;
			for (Cell c : cells) {
				if (c.getX() == iRand && c.getY() == jRand) {
					if (!(c instanceof BlockCell) && !(c instanceof QuesCell) && !(c instanceof JumpCell) && !(c instanceof UndoCell)) {
						if (c.getChildren().isEmpty()) {
							if (c.getX() != 0 && c.getY() != 0) {
								temp = c;
								times--;
								System.out.println(temp.getName());
							}
						}
					}
				}
			}
			if (temp != null) {
				cells.remove(temp);
				temp = new UndoCell(iRand, jRand);
				temp.setName("Square" + iRand + jRand);
				temp.setPrefHeight(100);
				temp.setPrefWidth(100);
				chessBoard.add(temp, iRand, jRand, 1, 1);
				cells.add(temp);
			}

		}
	}
	
	// Creating Question Cells
	private void createQuesCell(int times) {
		while (times > 0) {
			Random randI = new Random();
			Random randJ = new Random();
			int iRand = randI.nextInt(7);
			int jRand = randJ.nextInt(7);
			Cell temp = null;
			for (Cell c : cells) {
				if (c.getX() == iRand && c.getY() == jRand) {
					if (!(c instanceof BlockCell) && !(c instanceof QuesCell) && !(c instanceof JumpCell) && !(c instanceof UndoCell)) {
						if (c.getChildren().isEmpty()) {
							if (c.getX() != 0 && c.getY() != 0) {
								temp = c;
								times--;
								System.out.println(temp.getName());
							}
						}
					}
				}
			}
			if (temp != null) {
				cells.remove(temp);
				temp = new QuesCell(iRand, jRand);
				temp.setName("Square" + iRand + jRand);
				temp.setPrefHeight(100);
				temp.setPrefWidth(100);
				chessBoard.add(temp, iRand, jRand, 1, 1);
				cells.add(temp);
			}

		}
	}

	public void setTheme(Cell cell, String theme, int i, int j) {
		Color color1 = Color.web("#ffffff00");
		Color color2 = Color.web("#ffffff00");

		switch (theme) {
		case "Coral":
			color1 = Color.web("#b1e4b9");
			color2 = Color.web("#70a2a3");
			break;

		case "Dusk": {
			color1 = Color.web("#cbb7ae");
			color2 = Color.web("#716677");
			break;
		}
		case "Wheat": {
			color1 = Color.web("#eaefce");
			color2 = Color.web("#bbbe65");
			break;
		}
		case "Marine": {
			color1 = Color.web("#9dacff");
			color2 = Color.web("#6f74d2");
			break;
		}
		case "Emerald": {
			color1 = Color.web("#adbd90");
			color2 = Color.web("#6e8f72");
			break;
		}
		case "Sandcastle": {
			color1 = Color.web("#e4c16f");
			color2 = Color.web("#b88b4a");
			break;
		}
		}

		if ((i + j) % 2 == 0) {
			cell.setBackground(new Background(new BackgroundFill(color1, CornerRadii.EMPTY, Insets.EMPTY)));
		} else {
			cell.setBackground(new Background(new BackgroundFill(color2, CornerRadii.EMPTY, Insets.EMPTY)));
		}

	}

	private void addPiece(Cell square, Piece piece) {
		square.getChildren().add(piece);
		square.setOccupied(true);
	}

	private void addPieces() {
		for (Cell cell : cells) {
			if (cell.isOccupied())
				continue;

			else if (cell.getY() == 0) {
				if (cell.getX() == 7) {
					if (level == 1 || level == 2)
						addPiece(cell, PieceFactory.newPieceByFactory("Queen", "white", cell.getX(), cell.getY())); // new
																													// Queen("white",
																													// cell.getX(),
																													// cell.getY()));
					else
						addPiece(cell, PieceFactory.newPieceByFactory("King", "white", cell.getX(), cell.getY()));
				}
				if (cell.getX() == 0) {
					addPiece(cell, PieceFactory.newPieceByFactory("Knight", "black", cell.getX(), cell.getY())); // new
					cell.setVisited(true);		
					cell.setCounter(1);
				}
			}
		}
	}

}