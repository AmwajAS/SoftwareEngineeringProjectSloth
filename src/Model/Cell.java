package Model;


import javafx.geometry.Insets;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;

//Cell equals Square
public class Cell extends StackPane{
	Color color = Color.web("#F0E68C");
    private int x,y;
    private boolean isOccupied;  //has a piece on it
    private String name;
    private boolean isVisited;
    private int counter;
/*
 * Cell is an object in the chessBoard that extends StackPane
 * the counter variable counts how many times the knight steps on a specific cell.
 */
	public Cell(int x, int y){
        this.x = x;
        this.y = y;
        this.setOccupied(false);
        this.isVisited = false;
        this.counter=0;
       }
   
	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public boolean isOccupied() {
		return isOccupied;
	}

	public void setOccupied(boolean occupied) {
		this.isOccupied = occupied;
	}

	public String getName() {
		return name;
	}

    public void setName(String name){
        this.name = name;
    }    
    
    public boolean isVisited() {
		return isVisited;
	}
   
	public int getCounter() {
		return counter;
	}

	public void setCounter(int counter) {
		this.counter = counter;
	}

	public void setVisited(boolean isVisited) {
		this.isVisited = isVisited;
		if(isVisited) {
			setBackground(new Background(new BackgroundFill(color, CornerRadii.EMPTY, Insets.EMPTY)));
		}
	}


	@Override
    public String toString() {
        String status;
        if(this.isOccupied) status = "Occupied";
        else status = "Not occupied";
//        return "Square" + this.x + this.y + " - " + status;
        return "Square";
    }
}