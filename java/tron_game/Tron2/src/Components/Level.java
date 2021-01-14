/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Components;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Random;

/**
 *This class generates the map for the game.
 * 
 * 
 * @author peter
 */
public class Level {
    private Image background;
    private int blockToPlace = 10;
    private ArrayList<Point> walls;
    private int map[][] = new int[11][18];
    private final int size = 50;
    private Point startX = new Point(5,6);
    private Point startY = new Point(10,6);
    
    /**
     * The level is built from 50X50 blocks. The frame is 800x450 so there will 
     * be 144 block every block could be a wall but the ones where the player 
     * starts and the ones right in front. There will be a path finding 
     * algorithm so i can check if they could interact;
     * 
     * Whole map is 16x9
     * 
     * A 50 block is enough to let a 30 block player through so 1 block is 
     * enough for path;
     * 
     * Player moves only vertically and horizontally;
     */
    public Level(Image img){
        this.background = img;
        
        initMap();
        
    }
    
    /**
     * Draws the boxes on  the frame and sets the background
     * @param g 
     */
    public void draw(Graphics g){
        g.drawImage(background, 0, 0, 800, 450, null);
        g.setColor(Color.white);
        for(int i=0; i<walls.size(); i++){
            g.fillRect(walls.get(i).x*50, walls.get(i).y*50, size, size);
        }
    }
    
    /**
     * Initializes the map. Generates the locations of the walls.
     * 
     * Checks if the players could reach each other. If they cant the map is
     * regenerated;
    */
    public void initMap(){
        
        walls = new ArrayList<>();
        int btp = blockToPlace;
        while(btp>0){
            int x = randInt(1, 16);
            int y = randInt(1, 9);
            if(!walls.contains(new Point(x,y))){
                if(!(x==5 && y==6) && !(x==5 && y==5) && !(x==5 && y==4) && !(x==10 && y==6) && !(x==10 && y==5) && !(x==10 && y==4))
                walls.add(new Point(x,y));
                btp -= 1;
            }
        }
        
        for(int i=0; i<11; i++){
            for(int j=0; j<18; j++){
                if(i==0 || j==0 || i==10 || j==17){
                    map[i][j] = 1;
                } else{
                    map[i][j] = 0;
                }
            }
        }
        for(Point p : walls){
            map[p.y][p.x] = 1;
        }
        
        map[startX.y][startX.x] = -1;
        map[startY.y][startY.x] = -1;
        
        floodFill(map, 5, 5, 2);
        
        //playerTwo 10 6
        if(!(map[5][10]==2 || map[7][10]==2 || map[6][9]==2 || map[6][11]==2)){
            initMap();
        }
    }
    
    /**
     * Helper function for the flood fill.
     * 
     * Recursively calls itself as long as it can and paints all the free tiles
     * that can be reached.
     * This way if I start in front of the first player and checks weather the
     * second player ha a painted tile connected there is a path.
     * 
     * @param screen
     * @param x
     * @param y
     * @param prevC
     * @param newC 
     */
    static void floodFillUtil(int screen[][], int x, int y, 
                                    int prevC, int newC)
    {
        // Base cases
        if (x < 0 || x >= 11 || y < 0 || y >= 18)
            return;
        if (screen[x][y] != prevC)
            return;

        // Replace the color at (x, y)
        screen[x][y] = newC;

        // Recur for north, east, south and west
        floodFillUtil(screen, x+1, y, prevC, newC);
        floodFillUtil(screen, x-1, y, prevC, newC);
        floodFillUtil(screen, x, y+1, prevC, newC);
        floodFillUtil(screen, x, y-1, prevC, newC);
    }

    /**
     * 
     * Recursively calls the util as long as it can and paints all the free tiles
     * that can be reached.
     * This way if I start in front of the first player and checks weather the
     * second player ha a painted tile connected there is a path.
     * 
     * @param screen
     * @param x
     * @param y
     * @param newC 
     */
    static void floodFill(int screen[][], int x, int y, int newC)
    {
        int prevC = screen[x][y];
        floodFillUtil(screen, x, y, prevC, newC);
    }
    
    
    /**
     * Random number generator in range
     * 
     * @param min
     * @param max
     * @return 
     */
    public int randInt(int min, int max) {
        Random rand = new Random();

        int randomNum = rand.nextInt((max - min) + 1) + min;

        return randomNum;
    }
    
    public ArrayList<Point> getWalls(){
        return walls;
    }
}
