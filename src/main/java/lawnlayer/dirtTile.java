package lawnlayer;
import processing.core.PImage;
import processing.core.PApplet;
import java.util.ArrayList;

public class dirtTile extends tile {
    public int x;
    private int y;
    private PImage sprite;

    public dirtTile(int x, int y){
        super("dirt",false,x,y);
        //this.sprite = sprite;
        this.x = x;
        this.y = y;
        this.toured = false;


        


    }

    public void draw(PApplet app){
        //app.image(this.sprite, this.x, this.y);
        app.fill(139,69,19);
        app.rect(this.x,this.y,20,20);
        
        //app.stroke(139,69,19);




    }    
 
}
