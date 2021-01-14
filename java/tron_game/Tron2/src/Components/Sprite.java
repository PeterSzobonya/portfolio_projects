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
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 *
 * The origin of the Player class. Can draw itself and rotate the image of the player.
 * 
 * @author peter
 */
public class Sprite {
    /**
     * The coordinates of the top left corner of the sprite
     */
    protected int x;
    protected int y;
    protected int width;
    protected int height;
    protected Image image;
    protected Color color;
    protected String name;
    protected ArrayList<Point> trailPoints;
    
    public Sprite(int x, int y, int width, int height, Image image, Color color, String name){
        this.x=x;
        this.y=y;
        this.width=width;
        this.height=height;
        this.image = image;
        this.color = color;
        this.name = name;
        trailPoints = new ArrayList<>();
        trailPoints.add(new Point(x+width/2,y+height/2));
        
        
        trailPoints.add(new Point(x+width/2,y+height/2));
    }
    
    public void draw(Graphics g){
        g.drawImage(image, x, y, width, height, null);
        g.setColor(color);
        for(int i=0; i<trailPoints.size()-1; i++){
            g.drawLine(trailPoints.get(i).x, trailPoints.get(i).y,
                    trailPoints.get(i+1).x, trailPoints.get(i+1).y);
        }
    }
    
    public void rotateImage(int rotateBy){
        BufferedImage i = new BufferedImage(image.getWidth(null), image.getHeight(null), BufferedImage.TYPE_INT_RGB);
        Graphics bg = i.getGraphics();
        bg.drawImage(image, 0, 0, null);
        bg.dispose();
        
        int w = i.getWidth();    
        int h = i.getHeight();

        BufferedImage rotated = new BufferedImage(w, h, i.getType());  
        Graphics2D graphic = rotated.createGraphics();
        graphic.rotate(Math.toRadians(rotateBy), w/2, h/2);
        graphic.drawImage(i, null, 0, 0);
        graphic.dispose();
        image = rotated;
        
    }
    
    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }
    
    public String getName(){
        return name;
    }
}
