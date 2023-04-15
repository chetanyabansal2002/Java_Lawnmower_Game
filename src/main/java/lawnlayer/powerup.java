package lawnlayer;

import processing.core.PImage;
import processing.core.PApplet;
import java.util.ArrayList;

public abstract class powerup {
    public int x;
    public int y;
    public String type;
    public PImage sprite;

    public powerup(int x,int y, String type, PImage sprite){
        this.x = x;
        this.y = y;
        this.type = type;
        this.sprite = sprite;
    }

    public abstract void draw(PApplet app);
    
}

