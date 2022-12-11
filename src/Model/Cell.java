package Model;

import javafx.scene.layout.StackPane;

public class Cell extends StackPane{
    int x,y;
    boolean occupied;  //מבוקרת
    String name;

    public Cell(int x, int y){
        this.x = x;
        this.y = y;
        this.occupied = false;
    }

    @Override
    public String toString() {
        String status;
        if(this.occupied) status = "Occupied";
        else status = "Not occupied";
//        return "Square" + this.x + this.y + " - " + status;
        return "Cell";
    }

    public void setName(String name){
        this.name = name;
    }
}
