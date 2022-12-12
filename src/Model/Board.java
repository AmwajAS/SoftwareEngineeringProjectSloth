package Model;

import java.util.ArrayList;
import javafx.geometry.Insets;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;


/**
 * Data structure for managing interaction with a board.
 */
public class Board {

	private GridPane chessBoard;
	private String theme;
	private ArrayList<Cell> cells = new ArrayList<>();

	public Board(GridPane chessBoard, String theme){
		this.chessBoard = chessBoard;
		this.theme = theme;
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



	private void makeBoard(GridPane chessBoard, String theme){
		for(int i=0; i<8; i++){
			for(int j=0; j<8; j++){
				Cell cell = new Cell(i,j);
				cell.setName("Square" + i + j);
				cell.setPrefHeight(100);
				cell.setPrefWidth(100);
				cell.setBorder(new Border(new BorderStroke(Color.BLACK,
						BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
				setTheme(cell, theme, i, j);
				chessBoard.add(cell, i, j, 1, 1);
				cells.add(cell);
			}
		}
		addPieces();
	}

	private void setTheme(Cell cell, String theme, int i, int j){
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
		}
		case "Wheat": {
			color1 = Color.web("#eaefce");
			color2 = Color.web("#bbbe65");
		}
		case "Marine": {
			color1 = Color.web("#9dacff");
			color2 = Color.web("#6f74d2");
		}
		case "Emerald": {
			color1 = Color.web("#adbd90");
			color2 = Color.web("#6e8f72");
		}
		case "Sandcastle": {
			color1 = Color.web("#e4c16f");
			color2 = Color.web("#b88b4a");
		}
		}

		if((i+j)%2==0){
			cell.setBackground(new Background(new BackgroundFill(color1, CornerRadii.EMPTY, Insets.EMPTY)));
		}else{
			cell.setBackground(new Background(new BackgroundFill(color2, CornerRadii.EMPTY, Insets.EMPTY)));
		}

	}

	private void addPiece(Cell square, Piece piece){
		square.getChildren().add(piece);
		square.setOccupied(true);
	}

	private void addPieces(){
		for(Cell cell : cells){
			if(cell.isOccupied()) continue;

			else if(cell.getY()== 0){
				if(cell.getX() == 7){
					addPiece(cell, PieceFactory.newPieceByFactory("Queen","white",cell.getX(), cell.getY()));      //new Queen("white", cell.getX(), cell.getY()));
				}
				if(cell.getX()== 0){
					addPiece(cell, 	PieceFactory.newPieceByFactory("Knight","black", cell.getX(), cell.getY()));	//new Knight("black", cell.getX(), cell.getY()));
				}
			}
		}
	}

}
