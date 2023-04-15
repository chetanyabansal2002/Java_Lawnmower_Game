package lawnlayer;
import processing.core.PImage;
//import javafx.scene.input.KeyCode;
import processing.core.PApplet;
import java.util.ArrayList;
import java.lang.Math;


public abstract class enemies {
    public int x;
    public int y;
    //public PImage sprite;
    public int xDirection;
    public int yDirection;
    public int speed;
    public int xTile;
    public int yTile;
    public abstract void draw(PApplet app);

    
}





