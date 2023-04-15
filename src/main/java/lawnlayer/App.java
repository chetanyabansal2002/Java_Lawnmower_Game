package lawnlayer;

import java.util.ArrayList;
import java.util.LinkedList;
import java.io.*;
import java.util.Scanner;

import org.checkerframework.checker.units.qual.A;
import processing.core.PApplet;
import processing.core.PImage;
import processing.data.JSONObject;
import processing.data.JSONArray;
//import processing.core.PFont;
import processing.event.KeyEvent;
import java.lang.Math;

public class App extends PApplet {

    public static final int WIDTH = 1280;
    public static final int HEIGHT = 720;
    public static final int SPRITESIZE = 20;
    public static final int TOPBAR = 80;
    public JSONArray levels;
    public JSONObject jsonObject;
    public int totalEnem;
    public double goal;
    public int tilesNeed;



    public static final int FPS = 60;
    public int currentLevel = 0;
    public String configPath;
    public int Counter;
	
	public PImage grass;
    public PImage concrete;
    public PImage worm;
    public PImage heart;
    public PImage boost;
    public PImage freeze;
    public PImage beetle;
    private lawnmover Lawnmover;
    public int lives;
   // public tile[][] Tiles;




    public App() {
        this.configPath = "config.json";



        
        
             
    }

    /**
     * Initialise the setting of the window size.
    */
    public void settings() {
        size(WIDTH, HEIGHT);
        
        
    }

    /**
     * Load all resources such as images. Initialise the elements such as the player, enemies and map elements.
    */
    public void setup() {
        frameRate(FPS);
        this.Lawnmover = new lawnmover(20, 100, loadImage(this.getClass().getResource("ball.png").getPath()));
        this.jsonObject = loadJSONObject(this.configPath);
        this.levels = (JSONArray)jsonObject.get("levels");
        this.Lawnmover.Life = (int)(jsonObject.get("lives"));

        // Load images during setup
		this.grass = loadImage(this.getClass().getResource("grass.png").getPath());
        this.concrete = loadImage(this.getClass().getResource("concrete_tile.png").getPath());
        this.worm = loadImage(this.getClass().getResource("worm.png").getPath());
        this.beetle = loadImage(this.getClass().getResource("beetle.png").getPath());
        this.freeze = loadImage(this.getClass().getResource("freeze.png").getPath());
        this.boost = loadImage(this.getClass().getResource("boost.png").getPath());
        this.heart = loadImage(this.getClass().getResource("heart.png").getPath());
        

        this.LevelChange();
        this.Counter = 0;
        /*
        //this.Tiles = new tile[64][32];

        
        worm w1 = new worm(800, 400, this.worm);
        worm w2 = new worm(600,400,this.worm);
        
         
        this.Lawnmover.Enemies.add(w1);
        this.Lawnmover.Enemies.add(w2);
        for (int i =0 ; i <64;i++){
            for(int j = 4;j<36;j++){
                if (j==4 || j==35 || i == 0 || i== 63){
                   cementTile T = new cementTile(20*i,20*j,this.concrete); 
                   this.Lawnmover.Tiles[i][j-4]  = T;
                   //this.Lawnmover.DirtTiles = this.Lawnmover.DirtTiles -1;
               }                              
            }
        }        
                    
        
        for (int n =0 ; n <64;n++){
            for(int m = 4;m<36;m++){
                if (this.Lawnmover.Tiles[n][m-4] == null){
                    dirtTile D = new dirtTile(n*20, m*20);
                    this.Lawnmover.Tiles[n][m-4] = D;
                }
               
            }
        } 
        this.Lawnmover.draw(this);
        this.Lawnmover.presentTile = this.Lawnmover.Tiles[1][1];
        this.Lawnmover.NextTile = null;      
    */              

               
    }
    
	
    /**
     * Draw all elements in the game by current frame. 
    */
    public void draw() {
        if (this.CheckDead() == true){
            this.fill(255,255,255);
            this.rect(-1,-1,WIDTH+2,HEIGHT+2);
            fill(0, 408, 612); 
            this.fill(139,69,19);
            textSize(80);
            text("All Lives Lost,You Lose",300,300);
            return;
        }
        this.CheckDead();
        int b = this.LevelCheck();
        

        int goal = (int) (this.goal*100);
        if (b>goal){
            boolean n = this.LevelChange();
            if (n == true){
                this.fill(255,255,255);
                this.rect(-1,-1,WIDTH+2,HEIGHT+2);
                fill(0, 408, 612); 
                this.fill(139,69,19);
                textSize(80);
                text("You win",500,300);
                return;
            }
        }
        this.fill(139,69,19);
        this.rect(-1,-1,WIDTH+2,HEIGHT+2);
        fill(0, 408, 612); 
        textSize(32);
        text("Lives: "+this.Lawnmover.Life,60,60);
        text("Progress: "+ b +"%/"+goal+"%",500,60);
        text("Level: "+this.currentLevel, 1000, 60);
        if (this.Lawnmover.freeze == true || this.Lawnmover.boost == true){
            int num = 5-(int) Math.round(this.Lawnmover.UseTimer/60);
            text("Powerup Time: "+num, 200, 60);
            
        }
 
        double L = Math.random();
        int RandomNumber = (int)(200 + L*400);
        
        if (this.Counter>RandomNumber && this.Lawnmover.AvaiablePowerup == null&&this.Lawnmover.boost == false && this.Lawnmover.freeze == false){
            double randonChance = Math.random();
            this.Counter = 0;
            int X1 = (int)(25+30*Math.random());
            int Y1 = (int)(12+15*Math.random());
            this.Lawnmover.VacantTimer = 0;
            this.Lawnmover.UseTimer = 0;
            

            if (randonChance<0.33){
                freeze F = new freeze(X1*20,Y1*20-4,"freeze", this.freeze);
                this.Lawnmover.AvaiablePowerup = F;

            }
            else if (randonChance<0.66){
                heart F = new heart(X1*20, Y1*20-4, "heart", this.heart);
                this.Lawnmover.AvaiablePowerup = F;
                
            }
            else if (randonChance<1.5){
                boost F = new boost(X1*20, Y1*20-4, "boost", this.boost);
                this.Lawnmover.AvaiablePowerup = F;
                
            }

        }

         if(this.Lawnmover.freeze == false && this.Lawnmover.boost ==false&&this.Lawnmover.AvaiablePowerup == null){
            this.Counter = this.Counter +1;
        }
        this.Lawnmover.tick();                     
        for (int i = 0;i<64;i++){
            for (int j=0;j<32;j++){
                this.Lawnmover.Tiles[i][j].draw(this);
                /*
                if (this.Lawnmover.Tiles[i][j].type == "green"){
                    //System.out.println(i );
                    //System.out.println(j);
                    //System.out.print("///////////////////////");
                }
                */
            }
        }
        if (this.Lawnmover.AvaiablePowerup != null){
            this.Lawnmover.AvaiablePowerup.draw(this);
            this.Lawnmover.VacantTimer = this.Lawnmover.VacantTimer +1;
        }
        for (worm e: this.Lawnmover.Enemies){
            e.draw(this);
        }
        this.Lawnmover.draw(this);
        //System.out.println(this.Counter);
        
        

        

    }
    public boolean LevelChange(){
        this.Counter = 0;
        this.tilesNeed = 0;
        this.Lawnmover.xDirection = 0;
        this.Lawnmover.yDirection = 0;
        this.Lawnmover.xChange = true;
        this.Lawnmover.yChange = true;
        this.currentLevel = this.currentLevel +1;
        this.Lawnmover.Enemies.clear();
        if (this.currentLevel> this.levels.size()){
            return true;
        }
        JSONObject LocalLevel = this.levels.getJSONObject(currentLevel-1);
        JSONArray Enem = (JSONArray)LocalLevel.get("enemies");
        this.goal = (double)(LocalLevel.get("goal"));
        String Filename = (String)LocalLevel.get("outlay");
        
        for (int i = 0;i<Enem.size();i++){
            int x =(int) Math.round(5+52*Math.random());
            
            int y =(int) Math.round(5+25*Math.random());
            //System.out.println(x + "    "+ y);
            worm W = new worm(x*20, y*20, this.worm);
            this.Lawnmover.Enemies.add(W);
        }

        try{
            //System.out.println(Filename);
            File f = new File(Filename); 
            Scanner reader = new Scanner(f);
            int j = 0;
            while (reader.hasNextLine()){
                String data = reader.nextLine();
                char[] chars = data.toCharArray();
                int i= 0;
                //System.out.println(chars);
                for (char c:chars){
                    
                    if (c == 'X'){
                        cementTile C = new cementTile(i*20, (j+4)*20, this.concrete);
                        this.Lawnmover.Tiles[i][j] = C;
                    }
                    else{
                        dirtTile D = new dirtTile(i*20, (j+4)*20);
                        this.Lawnmover.Tiles[i][j] = D;
                        this.tilesNeed = this.tilesNeed +1;
                    }
                    i = i +1;

                }
                j = j+1;
                
                


            }




        }
        catch(FileNotFoundException e){
            ;
        }

        //this.Lawnmover.draw(this);
        this.Lawnmover.x = 20;
        this.Lawnmover.y = 100;
        this.Lawnmover.presentTile = this.Lawnmover.Tiles[1][1];
        this.Lawnmover.NextTile = null;
        return false;








    }


    public void keyPressed(){

        

         if (key == CODED)      {
            if (keyCode == UP &&  this.Lawnmover.yChange ==true ){
                
                this.Lawnmover.KeyUp = true;
                this.Lawnmover.KeyDown = false;
                this.Lawnmover.KeyRight = false;
                this.Lawnmover.KeyLeft = false;
            }

            if (keyCode == DOWN &&  this.Lawnmover.yChange ==true ){
                this.Lawnmover.KeyUp = false;
                this.Lawnmover.KeyDown = true;
                this.Lawnmover.KeyRight = false;
                this.Lawnmover.KeyLeft = false;                

                
                
            }
            
            if (keyCode == LEFT &&  this.Lawnmover.xChange ==true ){
                this.Lawnmover.KeyUp = false; 
                this.Lawnmover.KeyDown = false;
                this.Lawnmover.KeyRight = false;
                this.Lawnmover.KeyLeft = true;                
                             
                
            }
            
            if (keyCode == RIGHT &&  this.Lawnmover.xChange ==true ){
                this.Lawnmover.KeyUp = false;
                this.Lawnmover.KeyDown = false;
                this.Lawnmover.KeyRight = true;
                this.Lawnmover.KeyLeft = false;   
                
                                               
                
            }            

        }
        
    }
    public  boolean CheckDead(){
        if (this.Lawnmover.Life<1){
            return true;
            //System.exit(1);
        }
        return false;

    }

    public int LevelCheck(){
        int Moment  =0 ;
        for (int i = 0;i<64;i++){
            for(int j = 0;j<32;j++){
                if (this.Lawnmover.Tiles[i][j].type == "grass"){
                    Moment = Moment +1;
                }
            }
        }
        int percentage = (int) Math.round(Moment*100/this.tilesNeed);
        //System.out.println(percentage);
        if (percentage > this.goal*100){
            //this.LevelChange();
            return percentage;
        }
        else{
            return percentage;
        }
    }




    public static void main(String[] args) {
        PApplet.main("lawnlayer.App");
    }
}
