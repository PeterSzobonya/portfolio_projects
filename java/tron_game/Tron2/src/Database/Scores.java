/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Database;

/**
 * Class to tidy the scores
 * @author peter
 */
public class Scores {
    String name;
    int wins;
    int id;
    
    public Scores(int id,String name, int wins){
        this.id = id;
        this.name = name;
        this.wins = wins;
    }
    
    public int getWins(){
        return wins;
    }
    
    public String getName(){
        return name;
    }
    
    public int getId(){
        return id;
    }
}
