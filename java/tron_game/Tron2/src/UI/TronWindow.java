/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UI;

import Components.Player;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *This class creates the frame and loads the panels when needed.
 * 
 * @author peter
 */
public class TronWindow extends javax.swing.JFrame{
    private JFrame tronWindow;
    private Menu menuPanel;
    private PlayerSelection playerSelectionPanel;
    private ScoreBoard scoreBoardPanel;
    private GameEngine game;
    private Celebration celebration;
    
    public TronWindow(){ 
        tronWindow = new JFrame("Tron");
        tronWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        tronWindow.setPreferredSize(new Dimension(800,450));
        tronWindow.setResizable(false);
        menuPanel = new Menu(this);
        tronWindow.getContentPane().add(menuPanel);
        tronWindow.pack();
        tronWindow.setVisible(true);
    }
 
    /**
     * The given p panel is removed and the player selection is loaded
     * @param p 
     */
    public void switchToPlayerSelection(JPanel p){
        tronWindow.remove(p);
        playerSelectionPanel = new PlayerSelection(this);
        tronWindow.getContentPane().add(playerSelectionPanel);
        tronWindow.pack();
        tronWindow.setVisible(true);
    }
    
    /**
     * The given p panel is removed and the statistics is loaded
     * @param p 
     */
    public void switchToStatistics(JPanel p){
        tronWindow.remove(p);
        scoreBoardPanel = new ScoreBoard(this);
        tronWindow.getContentPane().add(scoreBoardPanel);
        tronWindow.pack();
        tronWindow.setVisible(true);
    }
    
    /**
     * The given p panel is removed and the main menu is loaded
     * @param p 
     */
    public void switchToMenu(JPanel p){
        tronWindow.remove(p);
        menuPanel = new Menu(this);
        tronWindow.getContentPane().add(menuPanel);
        tronWindow.pack();
        tronWindow.setVisible(true);
    }
    
    /**
     * The given p panel is removed and the actual game is loaded
     * @param p 
     */
    public void switchToGame(JPanel p,Image pl, Image pp, Color c, Color cc,String n,String nn){
        tronWindow.remove(p);
        game = new GameEngine(pl,c,pp,cc,n,nn, this);
        tronWindow.getContentPane().add(game);
        tronWindow.pack();
        tronWindow.setVisible(true);
    }
    
    /**
     * The given p panel is removed and the celebration is loaded
     * @param p 
     */
    public void switchToCongrats(JPanel g,Player p, Player pp){
        tronWindow.remove(g);
        celebration = new Celebration(p, pp, this);
        tronWindow.getContentPane().add(celebration);
        tronWindow.pack();
        tronWindow.setVisible(true);
    }
}
