package lawnlayer;
import processing.core.PImage;
//import javafx.scene.input.KeyCode;
import processing.core.PApplet;
import java.util.ArrayList;

import com.jogamp.nativewindow.awt.DirectDataBufferInt;

import java.lang.Math;
public class lawnmover {
    public int x;
    public int y;
    public tile presentTile;
    public tile NextTile;
    public tile[][] Tiles;
    public tile[][] DuplicateTiles;
    public ArrayList<tile> Trail;
    public boolean emenmy;
    public ArrayList<worm> Enemies;
    public boolean WrongArea;
    public int Life;
    public powerup AvaiablePowerup;
    public boolean freeze;
    public boolean boost;
    public int UseTimer;
    public int VacantTimer; 

    private PImage sprite;
    public int xDirection;
    public int yDirection;
    public boolean xChange;
    public boolean yChange;
    public boolean KeyUp;
    public boolean KeyDown;
    public boolean KeyLeft;
    public boolean KeyRight;
    public int TilesFilled;
    public int  DirtTiles;
    





    public lawnmover(int x,int y, PImage sprite){
        this.x = x;
        this.y = y;
        this.sprite = sprite;
        this.xDirection = 0;
        this.yDirection = 0;
        this.xChange = true;
        this.yChange = true;
        this.KeyUp = false;
        this.KeyDown = false;
        this.KeyRight = false;
        this.KeyLeft = false;
        this.Tiles = new tile[64][32];
        this.Trail = new ArrayList<tile>();
        this.Enemies = new ArrayList<worm>();
        this.boost = false;
        this.freeze = false;
        //this.DirtTiles = 2048;
       // greenTile G = new greenTile(this.x,this.y);
        //this.Trail.add(G);   
        //this.Tiles[1][1] = G; 
                 


       
        
        


        

    }

    public void tick(){
        /*
        if (this.NextTile != null){
            System.out.println(this.NextTile.x);
            System.out.println(this.NextTile.x);
            System.out.println("///////////////////////////");
            
        }  
        */  
        this.keypress();
        this.move();
        this.EnemycollideTile();
        this.powerupCollide();
        if (this.freeze == false){
           this.enemiesMove();
        }
        else{
            this.UseTimer = this.UseTimer +1;
        }   
        if (this.AvaiablePowerup != null){
            this.VacantTimer = this.VacantTimer +0;
        }
        this.UpdateCurrent();
        this.UpdateTrail();
        this.powerUpExpire();
        
        this.ememiesLawnmoverCollide();
        if( this.ememiesLawnmoverCollide() ==false){
        if (this.x%20 == 0 && this.y%20 == 0 && this.Trail.size()>0){
           this.FloodFill(this.x/20, this.y/20-4);
        }   
        }
        
        
    }

    
    public void draw(PApplet app){
        
        //Handles graphics
        app.image(this.sprite, this.x, this.y);
        
        
    }
    /*
    public void updateTiles(tile[][] Tiles){
        for (int i =0 ; i <64;i++){
            for(int j = 0;j<32;j++){
                this.Tiles[i][j] = Tiles[i][j];
            }
        }

    }
    */

    public void keypress(){


        

        if (this.KeyUp == true&& this.x%20 == 0){
            this.xDirection = 0;
            this.yDirection = -1;
            this.yChange = false; 
            this.xChange = true;            

        }
        else if(this.KeyDown == true&& this.x%20 == 0){
            this.xDirection = 0;
            this.yDirection = 1;
            this.yChange = false; 
            this.xChange = true;            

        }
        else if(this.KeyLeft == true && this.y%20 == 0){
            this.xDirection = -1;
            this.yDirection = 0;    
            this.yChange = true;
            this.xChange = false;             

        }
        else if(this.KeyRight == true&& this.y%20 == 0){
            this.xDirection = 1;
            this.yDirection = 0;  
            this.yChange = true; 
            this.xChange = false;            

        }

    }
    /*
    public void UpdateCurrent(){
        double xTile = this.x/20;
        double yTile = (this.y/20)-4;
        int tempCurrX = (int) (Math.floor(xTile));
        
        int tempCurrY = (int)(Math.floor(yTile));
        System.out.println(yTile);
        int tempNextX = (int)(Math.ceil(xTile));
        int tempNextY = (int)(Math.ceil(yTile));
        if(tempCurrX == tempNextX && tempCurrY == tempNextY){
            this.presentTile = this.Tiles[tempCurrX][tempCurrY];
            this.NextTile = null;
        }
        else {
            this.presentTile = this.Tiles[tempCurrX][tempCurrY];
            this.NextTile = this.Tiles[tempNextX][tempNextY];
        }
        
        
         

    }
    */
    public void UpdateCurrent(){
        if (this.NextTile == null){
            int i = this.presentTile.x/20;
            int j = this.presentTile.y/20-4;

            if (this.xDirection == -1){
                this.NextTile = this.Tiles[i-1][j];
                
                
            }

            else if (this.xDirection == 1){
                this.NextTile = this.Tiles[i+1][j];
                //System.out.println(this.NextTile.x);
                //System.out.println(this.NextTile.y);
                //System.out.println("/////");
            } 
            else if (this.yDirection == -1){
                this.NextTile = this.Tiles[i][j-1];
                //System.out.println(this.NextTile.x);
                //System.out.println(this.NextTile.y);
                //System.out.println("/////");                
            }
            else if (this.yDirection == 1){
                this.NextTile = this.Tiles[i][j+1];
                //System.out.println(this.NextTile.x);
                //System.out.println(this.NextTile.y);                
                //System.out.println("/////");
            }                            
            
            
        }
        else if(this.x%20==0 && this.y%20==0){
            this.presentTile = this.NextTile;
            this.NextTile = null;

        }
    }
    

    public void UpdateTrail(){
         boolean Stationery = false;
         if (this.xDirection == 0 || this.yDirection == 0){
             Stationery = true;
         }
         if (this.x%20 == 0 && this.y%20 == 0 ){
             if (this.Tiles[x/20][y/20-4].type == "green"){
                //System.out.println("Life lost"+this.x/20+" "+(this.y/20-4));
                 for (tile t:this.Trail){
                     dirtTile T = new dirtTile(t.x, t.y);
                     this.Tiles[t.x/20][t.y/20-4] = T;
                     
                     
                 }
                 this.Trail.clear();
                 this.Life = this.Life -1;
                 this.x = 20;
                 this.y = 100;
                 this.xChange = true;
                 this.yChange = true;
                 this.xDirection = 0;
                 this.yDirection = 0;
                 this.presentTile = this.Tiles[1][1];
                 this.NextTile = null;
                  return;


             }
             if (this.Tiles[x/20][y/20-4].type != "dirt"){
                 return;
             }
            

           int I = this.x/20;
           int J = this.y/20-4;
           
           greenTile G = new greenTile(this.x,this.y);
           
        
           if (this.xDirection != 0 || this.yDirection != 0){
            this.Trail.add(G);   
            this.Tiles[I][J] = G; 
            //this.DirtTiles = this.DirtTiles -1; 
            
           //System.out.println(this.Trail.size());
          }
          



        }  



    }

    public void move(){
        if (this.NextTile != null){
            if (this.NextTile.type == "cement"){
                this.yDirection = 0;
                this.xDirection = 0;
                this.NextTile = null;
                this.x = this.presentTile.x;
                this.y = this.presentTile.y;
                this.KeyUp = false;
                this.KeyLeft = false;
                this.KeyDown = false;
                this.KeyRight = false;

            }

        }
        if (this.boost == true){
            System.out.println("boost");
            if (this.x%4 == 2){
                this.x = this.x -2*this.xDirection;
            }
            if (this.y%4 == 2){
                this.y = this.y -2*this.yDirection;
            }
            this.x = this.x + 4*this.xDirection;
            this.y = this.y + this.yDirection*4;
            this.UseTimer = this.UseTimer +1;
            return;
        }
        this.x = this.x + this.xDirection*2;
        this.y = this.y + this.yDirection*2;

    }

    public void FloodFill(int x,int y){
        boolean XPlus = false;
        boolean YMinus = false;
        boolean XMinus = false;
        boolean YPlus = false;
        this.TilesFilled = 0;
        this.WrongArea = false;
        //Create a duplicate cell list
        this.DuplicateTiles = new tile[64][32];
        for (int i = 0;i<64;i++){
            for(int j=0;j<32;j++){
                this.DuplicateTiles[i][j]= this.Tiles[i][j];
            }
        }
        if(this.DuplicateTiles[x+1][y].type != "dirt"&&this.DuplicateTiles[x-1][y].type != "dirt"&&this.DuplicateTiles[x][y+1].type != "dirt"&&this.DuplicateTiles[x][y-1].type != "dirt"){
            return;
        }
        else if (this.DuplicateTiles[x+1][y].safe == false &&this.DuplicateTiles[x-1][y].safe == false && this.DuplicateTiles[x][y+1].safe == false &&this.DuplicateTiles[x][y-1].safe == false){
            return;
            
        }
        else{
            if (this.DuplicateTiles[x+1][y].type == "dirt"){
                this.HelperFunction(x+1, y);
                XPlus = true;
            }
            else if (this.DuplicateTiles[x-1][y].type == "dirt"){
                this.HelperFunction(x-1, y);
                XMinus = true;
            }
            else if (this.DuplicateTiles[x][y+1].type == "dirt"){
                this.HelperFunction(x, y+1);
                YPlus = true;
            }
            else if (this.DuplicateTiles[x][y-1].type == "dirt"){
                this.HelperFunction(x, y-1);
                YMinus = true;
            }                        
        }
        //System.out.println(this.TilesFilled);
        //System.out.println("Trail Size"+this.Trail.size());
        //System.out.println(this.DirtTiles);
        this.DirtTiles = this.getDirtTiles();
        //System.out.println(this.DirtTiles+"Dirt tiles");

        if (this.TilesFilled >= this.DirtTiles ){
            System.out.println(this.TilesFilled);
            System.out.println(this.DirtTiles);
            System.out.println("////////////////");
            return;
            
        }
        if(this.WrongArea == true){
            this.TilesFilled = 0;
            //System.out.println("air con");
            for (int i = 0;i<64;i++){
                for(int j=0;j<32;j++){
                    this.DuplicateTiles[i][j]= this.Tiles[i][j];    
                }
            }    
            this.WrongArea =false;
            if (XPlus == true && this.Tiles[this.x/20-1][this.y/20-4].type =="dirt"){
                this.HelperFunction(this.x/20 -1, this.y/20-4);
                if (this.WrongArea == true){
                    //System.out.println(this.TilesFilled);
                }
                else {
                    /*
                    for (int i = 0;i<64;i++){
                        for(int j = 0;j<32;j++){
                            if (this.DuplicateTiles[i][j].type == "dirt" && this.DuplicateTiles[i][j].toured == true){
                                grassTile G = new grassTile(20*i, (j+4)*20);
                                this.DuplicateTiles[i][j]= G;
                            }    
                        }
                    }
                    */
                    for (int i = 0;i<64;i++){
                        for(int j = 0;j<32;j++){
                            this.Tiles[i][j] = this.DuplicateTiles[i][j];
                        }
                    }                    
                }
            }            
            else if (YPlus == true && this.Tiles[this.x/20][this.y/20-5].type =="dirt"){
                this.HelperFunction(this.x/20, this.y/20-5);
                if (this.WrongArea == true){
                    ;
                }
                
                else {
                    /*
                    for (int i = 0;i<64;i++){
                        for(int j = 0;j<32;j++){
                            if (this.DuplicateTiles[i][j].type == "dirt" && this.DuplicateTiles[i][j].toured == true){
                                grassTile G = new grassTile(20*i, (j+4)*20);
                                this.DuplicateTiles[i][j]= G;
                            }    
                        }
                    }
                    */
                    for (int i = 0;i<64;i++){
                        for(int j = 0;j<32;j++){
                            this.Tiles[i][j] = this.DuplicateTiles[i][j];
                        }
                    }                    
                }                
            }
        }
        else{
            /*
            for (int i = 0;i<64;i++){
                for (int j = 0;i<64;i++){
                    if (this.DuplicateTiles[i][j].type == "dirt" && this.DuplicateTiles[i][j].toured == true){
                      grassTile G = new grassTile(20*i, (j+4)*20);
                      this.DuplicateTiles[i][j]= G;
                      System.out.println("Grape juice");

                    }

                }
            }
            */
            
            for(int i = 0;i<64;i++){
                for(int j = 0;j<32;j++){
                    this.Tiles[i][j] = this.DuplicateTiles[i][j];
                }
            }
            

        }
        for (tile t: this.Trail){
            int A = t.x/20;
            int B = t.y/20 -4;
            grassTile G = new grassTile(t.x,t.y);
            this.Tiles[A][B]= G; 
            
        }
        this.Trail.clear();

    }

    public void HelperFunction(int x,int y){
        if(this.DuplicateTiles[x][y].type != "dirt" ){
            return;      
        } 
        else{
            this.TilesFilled = this.TilesFilled+1;
            //grassTile G = new grassTile(20*x, (y+4)*20);
            //this.DuplicateTiles[x][y]= G;
             
            /*
            if ( this.TilesFilled>1000){
                this.WrongArea = true;
            }     
            */
            for (worm e:this.Enemies){
                int I = (int) Math.round(e.x/20);
                int J = (int) (Math.round(e.y/20)-4);
                if(I == x && J == y){
                   this.WrongArea = true;
                   //System.out.println("wrong Area");
                   

                }
            }
            
            //this.DuplicateTiles[x][y].toured = true;
            grassTile G = new grassTile(20*x, (y+4)*20);
           
            this.DuplicateTiles[x][y]= G;
            this.HelperFunction(x+1,y);
            this.HelperFunction(x-1,y);
            this.HelperFunction(x,y+1);
            this.HelperFunction(x,y-1);
            
        }        



    }

    public void EnemycollideTile(){
    
      for(worm e: this.Enemies){  
        

        
        if (e.x%20 == 0 && e.y%20 == 0){
        //Check if if collides with top
        int x = e.x/20;
        int y = e.y/20 - 4;
           if (e.xDirection ==1 && this.Tiles[x+1][y].safe == true  ){
           e.xDirection = -1;
           
           }
  
  
        //Check if it collides with bottom
          if (e.xDirection ==-1 && this.Tiles[x-1][y].safe == true){
            //System.out.println("x:"+x+"y"+y);
          e.xDirection = 1;
          
          }       
  
        //Check if it collides with left 
          if (e.yDirection ==-1 && this.Tiles[x][y-1].safe == true){
           // System.out.println("x:"+x+"y"+y);
          e.yDirection = 1;
          
          }      
        // Check if collides with right
          if (e.yDirection ==1 && this.Tiles[x][y+1].safe == true){
          //System.out.println("x:"+x+"y"+y);    
          e.yDirection = -1;
          
          }      
        
      
        }
     } 
  
    }
    
    public void enemiesMove(){
        for(worm e: this.Enemies){
            e.x = e.x + 2*e.xDirection;
            e.y = e.y + 2*e.yDirection;
        }
    }

    public boolean ememiesLawnmoverCollide(){
        boolean check = false;
        int coll = 0;
        for (worm e: this.Enemies){
            for(tile t: this.Trail){
                check = this.CheckCollison(e.x, e.y, t.x, t.y);
               if (check == true){
                   coll = coll +1;
                   //System.out.println("e_x: "+e.x+" e_y: "+e.y+" t.x: "+t.x+" t.y: "+t.y);
               }
               

                         



            }
        }
        if (coll> 0){
            for (tile t:this.Trail){
                dirtTile T = new dirtTile(t.x, t.y);
                this.Tiles[t.x/20][t.y/20-4] = T;

            }
            this.Trail.clear();
            this.x = 20;
            this.y = 100;
            this.xChange = true;
            this.yChange = true;
            this.xDirection = 0;
            this.yDirection = 0;
            this.presentTile = this.Tiles[1][1];
            this.NextTile = null;
            this.Life = this.Life -1;
    
            return true;
        }
        return false;

    }

    public int getDirtTiles(){
        this.DirtTiles = 0;
        for(int i =0;i<64;i++){
            for(int j = 0;j<32;j++){
                
                if (this.Tiles[i][j].type== "dirt"){
                    this.DirtTiles = this.DirtTiles +1;

                }
            }
        }
        return this.DirtTiles;

    }

    public boolean CheckCollison(int x1,int y1,int x2,int y2){
        int obj2left = x2;
        int obj2right = x2+20;
        int obj2up = y2;
        int obj2down = y2+20;
        int obj1right = x1+20;
        int obj1down = y1+20;
        if(obj1right>obj2left && x1<obj2right && obj1down>obj2up && y1<obj2down){
            return true;
        }




        return false;


    }

    public boolean powerupCollide(){
        if (this.AvaiablePowerup ==null){
            return false;
        }

        if (this.CheckCollison(this.x, this.y, this.AvaiablePowerup.x, this.AvaiablePowerup.y)==true){
            if (this.AvaiablePowerup.type=="boost"){
                this.boost = true;
                this.UseTimer = 0;
                this.AvaiablePowerup = null;
                return true;
            

            }
            if (this.AvaiablePowerup.type=="freeze"){
                this.freeze = true;
                this.UseTimer = 0;
                this.AvaiablePowerup = null;
                return true;

            }
            if (this.AvaiablePowerup.type=="heart"){
                this.Life = this.Life +1;
    
                this.AvaiablePowerup = null;
                return true;
                
            }
        }
        return false;
    }

    public void powerUpExpire(){
       if (this.VacantTimer>900 || this.UseTimer>300){
            this.AvaiablePowerup = null;
            this.freeze = false;
            this.boost = false;

        }
    }

  
    
}
