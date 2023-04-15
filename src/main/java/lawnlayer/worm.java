package lawnlayer;

//import javafx.scene.input.KeyCode;
import processing.core.PApplet;
import java.util.ArrayList;
import java.lang.Math;

import processing.core.PImage;

public class worm extends enemies {
    public int x;
    public int y;
    public PImage sprite;
    public int xDirection;
    public int yDirection;
    public int speed;
    public int xTile;
    public int yTile;


    public worm(int x,int y, PImage sprite){
        this.x =x;
        this.y = y;
        this.sprite = sprite;
        this.xTile = x/20;
        this.yTile = y/20 -4 ;
        this.xDirection = 1;
        this.yDirection = 1;
    }

  
    
    public void draw(PApplet app){
      app.image(this.sprite, this.x, this.y);
      //app.fill(255, 0, 0);
      //app.rect(this.x,this.y,20,20);
    }
 
    /*
    public void collide(){
      if (this.x%20 == 0 && this.y%20 == 0){
      //Check if if collides with top
       if (this.xDirection ==1 && this.Tiles[x+1][y].safe = "True"  ){
         this.xDirection = -1;
         
       }


      //Check if it collides with bottom
      if (this.xDirection ==-1 && this.Tiles[x-1][y].safe = "True"  ){
        this.xDirection = 1;
        
      }       

      //Check if it collides with left 
      if (this.xDirection ==-1 && this.Tiles[x][y-1].safe = "True"  ){
        this.yDirection = 1;
        
      }      
      // Check if collides with right
      if (this.xDirection ==1 && this.Tiles[x][y+1].safe = "True"  ){
        this.yDirection = -1;
        
      }      
      }
      

    }
    */
  
}
