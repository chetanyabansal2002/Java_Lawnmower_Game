package lawnlayer;
import processing.core.PImage;
//import javafx.scene.input.KeyCode;
import processing.core.PApplet;
import java.util.ArrayList;

public abstract class tile {
    public String type;
    public boolean safe;
    public int x;
    public int y;
    public boolean toured;

    public tile(String type,boolean safe,int x,int y){
        this.type = type;
        this.safe = safe;
        this.x = x;
        this.y = y;
        this.toured = false;

    }

    public abstract void draw(PApplet app);

    



    
}
