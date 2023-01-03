package Model;

import java.util.ArrayList;

import javafx.event.EventHandler;
import javafx.scene.effect.Glow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;

public class Piece extends ImageView {
    private String type;
    private String color;
    private int posX, posY;
    private ArrayList<String> possibleMoves;  

    public Piece(String color, int posX, int posY){
        this.color = color;
        this.posX = posX;
        this.posY = posY;
        addEventHandler();
    }
    
    public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getPosX() {
		return posX;
	}

	public void setPosX(int posX) {
		this.posX = posX;
	}

	public int getPosY() {
		return posY;
	}

	public void setPosY(int posY) {
		this.posY = posY;
	}

	public ArrayList<String> getPossibleMoves() {
		return possibleMoves;
	}

	public void setPossibleMoves(ArrayList<String> possibleMoves) {
		this.possibleMoves = possibleMoves;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getColor(){
        return this.color;
    }

	
	
    public void setPiece(Image image){
        this.setImage(image);
    }

    public void setImage(){
            this.setPiece(new Image("/images/" + this .color + "" + this.type + ".png"));

    }

    
    private void addEventHandler(){
        this.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                getAllPossibleMoves(1);
            }
        });


    }
    public void getAllPossibleMoves(int lvl) {}
    /*
     * this function shows all the possible moves with a white boarder.
     * it takes all the moves form possibleMoves from the extended Piece type,
     * and shows them.
     */
    public void showAllPossibleMoves(boolean val){
        if(val){
            for(String move : possibleMoves) {
                Cell square = getSquareByName(move);
                if(!(square instanceof BlockCell)) {
                square.setBorder(new Border(new BorderStroke(Color.WHITE,
                        BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(10))));
                }
                Piece piece = getPieceByName(move);
                if(piece == null) continue;
                if(piece.type.equals("King")){
                    square.setBorder(new Border(new BorderStroke(Color.DARKRED,
                            BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(1.5))));
                }
            }
        }
        else{
            for(String move : possibleMoves){
                Cell square = getSquareByName(move);
                square.setEffect(null);
                square.setBorder(new Border(new BorderStroke(Color.BLACK,
                        BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
            }
        }
    }

    public Cell getSquareByName(String name){
        for(Cell square : Game.cb.getCells()){
            if(square.getName().equals(name)){
                return square;
            }
        }

        return null;
    }

    public Piece getPieceByName(String name){
        for(Cell cell : Game.cb.getCells()){
            if(cell.getChildren().size() == 0) continue;

            if(cell.getName().equals(name))
                return (Piece) cell.getChildren().get(0);

        }
        return null;
    }


}