/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Components;

import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;

/**
 *This class stores variables that does not change anywhere in the program.
 * @author peter
 */
public class Constants {
    
    private ArrayList<ColorPair> colors;
    private String pathnames[];
    private String pathToArrows;
    private final int FPS = 60;
    private final int pOneStartX = 260;
    private final int pOneStartY = 310;
    private final int pTwoStartX = 510;
    private final int pTwoStartY = 310;
    private final int playerHeight = 30;
    private final int playerWidth = 30;
    private final int vel = 1;

    public Constants() {
        colors = new ArrayList<>();
        colors.add(new ColorPair("red", hex2Rgb("#fe4164")));    //Neon Fuchsia
        colors.add(new ColorPair("pink",hex2Rgb("#ff11ff")));    //Light Neon Pink
        colors.add(new ColorPair("blue", hex2Rgb("#00fdff")));   //Fluorescent Turquise
        colors.add(new ColorPair("green", hex2Rgb("#08ff08")));  //Flurescent Green
        colors.add(new ColorPair("orange",hex2Rgb("#ffcf09")));  //Bright Saffron
        colors.add(new ColorPair("purple",hex2Rgb("#bf00ff"))); //Electric Purple
        colors.add(new ColorPair("yellow",hex2Rgb("#ccff00"))); //Electric Lime
        
        pathToArrows = Paths.get("").toAbsolutePath().toString() + "/src/resources";
        File f = new File(pathToArrows);
        
        pathnames = f.list();
    }
    //create Color obj from hex code
    public static Color hex2Rgb(String colorStr) {
        return new Color(
            Integer.valueOf( colorStr.substring( 1, 3 ), 16 ),
            Integer.valueOf( colorStr.substring( 3, 5 ), 16 ),
            Integer.valueOf( colorStr.substring( 5, 7 ), 16 ) );
    }
    
    public ArrayList<ColorPair> getColors(){
        return colors;
    }
    
    public ArrayList<String> getArrowPaths(String name){
        ArrayList<String> paths = new ArrayList<>();
        
        for(String pathName : pathnames){
            
            if(pathName.split(":")[0].equals(name)){
                paths.add( pathToArrows + "/" + pathName);
            }
        }
        Collections.sort(paths);
        return paths;
    }

    public String[] getPathnames() {
        return pathnames;
    }

    public String getPathToArrows() {
        return pathToArrows;
    }

    public int getFPS() {
        return FPS;
    }

    public int getpOneStartX() {
        return pOneStartX;
    }

    public int getpOneStartY() {
        return pOneStartY;
    }

    public int getpTwoStartX() {
        return pTwoStartX;
    }

    public int getpTwoStartY() {
        return pTwoStartY;
    }

    public int getPlayerHeight() {
        return playerHeight;
    }

    public int getPlayerWidth() {
        return playerWidth;
    }

    public int getVel() {
        return vel;
    }
    
    //Just a class so I can refer to colors by name
    public class ColorPair{
        private String name;
        private Color color;

        public ColorPair(String name, Color color){
            this.name = name;
            this.color = color;
        }

        public String getName(){
            return name;
        }

        public Color getColor(){
            return color;
        }
    }
} 
