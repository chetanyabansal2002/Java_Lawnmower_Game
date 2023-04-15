package lawnlayer;


import processing.core.PApplet;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class lawnmoverTest {

    @Test
    public void Constructor() { // Test constructor
        lawnmover L = new lawnmover(500, 520, null);
        assert(L.x == 500);
        assert(L.y == 520);   
        assert(L.xChange = true);
        assert(L.xChange = true);
        assert(L.yChange = true);
        //assert(L.KeyUp = false);
        //assert(L.KeyDown = false);
        //assert(L.KeyRight = false);
        //assert(L.KeyLeft = false);        
    }

    @Test
    public void CheckMovement(){
        lawnmover L = new lawnmover(20, 100, null);
        for (int i =0 ; i <64;i++){
            for(int j = 4;j<36;j++){
                if (j==4 || j==35 || i == 0 || i== 63){
                   cementTile T = new cementTile(20*i,20*j,null); 
                   L.Tiles[i][j-4]  = T;
                   //L.DirtTiles = L.DirtTiles -1;
               }                              
            }
        }        
                    
        
        for (int n =0 ; n <64;n++){
            for(int m = 4;m<36;m++){
                if (L.Tiles[n][m-4] == null){
                    dirtTile D = new dirtTile(n*20, m*20);
                    L.Tiles[n][m-4] = D;
                }
               
            }
        } 
        L.xDirection = 1;
        L.presentTile = L.Tiles[1][1];
        L.UpdateCurrent();
        assert(L.NextTile == L.Tiles[2][1]);
        L.move();
        int M = L.x; 
        assert(M ==22);
        


        

    }


}