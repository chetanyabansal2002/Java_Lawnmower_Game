package lawnlayer;
import processing.core.PImage;
import processing.core.PApplet;
import java.util.ArrayList;
public class cementTile extends tile {
    public int x;
    private int y;
    private PImage sprite;
    public boolean toured;


    public cementTile(int x, int y, PImage sprite){
        super("cement", true, x, y);
        this.sprite = sprite;
        this.x = x;
        this.y = y;
        this.toured = false;
        


    }

    public void draw(PApplet app){
        app.image(this.sprite, this.x, this.y);
        
    

    }

    
}
