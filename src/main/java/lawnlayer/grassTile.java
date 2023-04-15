package lawnlayer;
import processing.core.PImage;
import processing.core.PApplet;
import java.util.ArrayList;

public class grassTile extends tile {
    public int x;
    private int y;
    private PImage sprite;


    public grassTile(int x, int y){
        super("grass", true, x, y);
        //this.sprite = sprite;
        this.x = x;
        this.y = y;
        


    }

    public void draw(PApplet app){
        app.fill(0, 100, 0);
        app.rect(this.x,this.y,20,20);
        
        
        
    

    }

    
}
