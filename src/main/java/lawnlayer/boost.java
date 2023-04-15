package lawnlayer;
import processing.core.PImage;
import processing.core.PApplet;
import java.util.ArrayList;
public class boost extends powerup {
    public int x;
    public int y;
    public String type;
    public PImage sprite;

    public boost(int x,int y, String type, PImage sprite){
        super(x,y,type,sprite);
        this.x =x;
        this.y =y;
        this.sprite = sprite;
        this.type = "boost";
    }

    public void draw(PApplet app){
        app.image(this.sprite, this.x, this.y);

    }
    
    
}
