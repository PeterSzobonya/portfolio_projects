/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Components;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.util.ArrayList;

/**
 *This class represents each player.
 * It has name, the motor/arrow the player chose, velocity, rotation of the motor,
 * width, height, position and color
 * 
 * It can move. Check if it collided with something. And can turn.
 * 
 * @author peter
 */
public class Player extends Sprite{
    
    private int velx;
    private int vely;
    private int vel;
    private int rot;
    
    public Player(int x,int y,int width,int height,Image image,Color color,int vel,String name){
        super(x, y, width, height, image, color, name);
        this.velx = 0;
        this.vely = -1;
        this.vel = vel;
        rot = 0;
        
    }
    /**
     * Moves the player on the map and updates the trail.
     */
    public void move() {
        x += velx;
        y += vely;
        
        trailPoints.remove(trailPoints.size()-1);
        trailPoints.add(new Point(x+width/2,y+height/2));
    }
    
    /**
     * Checks if the player is inside the map, if it collided with the other 
     * players trail or if it crashed into a box.
     * 
     * @param p
     * @param walls
     * @return 
     */
    public boolean collides(Player p,ArrayList<Point> walls){
        boolean isCollideing = false;
        //checking if the player is inside the window
        if(x<0 || x>800-width || y<0 || y>390){
            isCollideing = true;
        }
        
        for(int i =0; i<p.trailPoints.size()-1; i++){
            if(pointInside(p.trailPoints.get(i))){
                isCollideing = true;
            } else if(p.trailPoints.get(i).x == p.trailPoints.get(i+1).x){
                int a = p.trailPoints.get(i).y < p.trailPoints.get(i+1).y ? p.trailPoints.get(i).y : p.trailPoints.get(i+1).y;
                int b = p.trailPoints.get(i).y > p.trailPoints.get(i+1).y ? p.trailPoints.get(i).y : p.trailPoints.get(i+1).y;
                
                while(a<b && !isCollideing){
                    isCollideing = pointInside(new Point(p.trailPoints.get(i).x,a));
                    a+=height;
                }
            }else if(p.trailPoints.get(i).y == p.trailPoints.get(i+1).y){
                int a = p.trailPoints.get(i).x < p.trailPoints.get(i+1).x ? p.trailPoints.get(i).x : p.trailPoints.get(i+1).x;
                int b = p.trailPoints.get(i).x > p.trailPoints.get(i+1).x ? p.trailPoints.get(i).x : p.trailPoints.get(i+1).x;
                
                while(a<b && !isCollideing){
                    isCollideing = pointInside(new Point(a,p.trailPoints.get(i).y));
                    a+=width;
                }
            }
        }
        
        for(int i=0; i<walls.size(); i++){
            if(pointInside(new Point(walls.get(i).x*50,walls.get(i).y*50)) || 
                    pointInside(new Point(walls.get(i).x*50+50,walls.get(i).y*50)) ||
                    pointInside(new Point(walls.get(i).x*50,walls.get(i).y*50 + 50))||
                    pointInside(new Point(walls.get(i).x*50+50,walls.get(i).y*50+50)) ||
                    
                    pointInside(new Point(walls.get(i).x*50+25,walls.get(i).y*50)) ||
                    pointInside(new Point(walls.get(i).x*50,walls.get(i).y*50+25)) ||
                    pointInside(new Point(walls.get(i).x*50+50,walls.get(i).y*50+25)) ||
                    pointInside(new Point(walls.get(i).x*50+25,walls.get(i).y*50+50))
                    ){
                isCollideing = true;
            }
        }
        
        return isCollideing;
    }
    
    /**
     * helper function for collider. Checks if a point is inside the motor.
     * @param p
     * @return 
     */
    private boolean pointInside(Point p){
        return (p.x>=x && p.x<=x+width &&p.y>=y&&p.y<=y+height);
    }
    
    /**
     * change direction
     */
    public void moveLeft() {
        velx = -1*vel;
        vely = 0;
        switch(rot){
            case 0:
                rotateImage(270);
                rot=3;
                break;
            case 1:
                rotateImage(180);
                rot=3;
                break;
            case 2:
                rotateImage(90);
                rot=3;
                break;
            case 3:
                rot=3;
                break;
        }
        trailPoints.add(new Point(x+width/2,y+height/2));
    }
    
    /**
     * change direction
     */
    public void moveRight(){
        velx = vel;
        vely = 0;
        switch(rot){
            case 0:
                rotateImage(90);
                rot=1;
                break;
            case 1:
                rot=1;
                break;
            case 2:
                rotateImage(270);
                rot=1;
                break;
            case 3:
                rotateImage(180);
                rot=1;
                break;
        }
        trailPoints.add(new Point(x+width/2,y+height/2));
    }
    
    /**
     * change direction
     */
    public void moveUp() {
        vely = -1*vel;
        velx = 0;
        switch(rot){
            case 0:
                rot=0;
                break;
            case 1:
                rotateImage(270);
                rot=0;
                break;
            case 2:
                rotateImage(180);
                rot=0;
                break;
            case 3:
                rotateImage(90);
                rot=0;
                break;
        }
        trailPoints.add(new Point(x+width/2,y+height/2));
    }
    
    /**
     * change direction
     */
    public void moveDown(){
        vely = vel;
        velx = 0;
        switch(rot){
            case 0:
                rotateImage(180);
                rot=2;
                break;
            case 1:
                rotateImage(90);
                rot=2;
                break;
            case 2:
                rot=2;
                break;
            case 3:
                rotateImage(270);
                rot=2;
                break;
        }
        trailPoints.add(new Point(x+width/2,y+height/2));
    }
    
    /**
     * change direction
     */
    public Image getImage(){
        switch(rot){
            case 0:
                rot=0;
                break;
            case 1:
                rotateImage(270);
                rot=0;
                break;
            case 2:
                rotateImage(180);
                rot=0;
                break;
            case 3:
                rotateImage(90);
                rot=0;
                break;
        }
        return image;
    }
}
