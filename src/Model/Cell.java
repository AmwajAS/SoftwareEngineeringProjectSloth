package Model;
import javax.swing.JButton;

import javafx.scene.layout.StackPane;

//Cell equals Square
public class Cell extends StackPane{
<<<<<<< HEAD
    private int x,y;
    private boolean isOccupied;  //מבוקרת
    private String name;
=======
    int x,y;
    boolean occupied;
    String name;
>>>>>>> parent of f970382 (little changes.)

    public Cell(int x, int y){
        this.x = x;
        this.y = y;
        this.isOccupied = false;
    }
    
    public int getX() {
		return x;
	}

<<<<<<< HEAD
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
=======
    @Override
    public String toString() {
        String status;
        if(this.occupied) status = "Occupied";
        else status = "Not occupied";
//        return "Square" + this.x + this.y + " - " + status;
        return "Square";
    }
>>>>>>> parent of f970382 (little changes.)

    public void setName(String name){
        this.name = name;
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
