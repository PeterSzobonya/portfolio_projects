/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UI;

import Components.Constants;
import Components.Level;
import Components.Player;
import Database.Database;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.Timer;
import javax.swing.JPanel;
import javax.swing.KeyStroke;

/**
 * This class manages to repaint the panel so it makes the players move.
 * Also the button clicks are listened for in here;
 * 
 * @author peter
 */
public class GameEngine extends JPanel {
    private Constants gameConsts;
    
    private Image background;
    private Timer newFrameTimer;
    private Player playerOne;
    private Player playerTwo;
    private TronWindow window;
    private Level level;
    
    public GameEngine(Image pOne, Color pOneCol, Image pTwo, Color pTwoCol,String nOne, String nTwo, TronWindow w){
        super();
        gameConsts = new Constants();
        window = w;
        background = new ImageIcon("src/resources/background/background.jpeg").getImage();
        
        
        this.getInputMap(WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("typed a"), "pressed one left");
        this.getActionMap().put("pressed one left", new AbstractAction(){
            @Override
            public void actionPerformed(ActionEvent ae){
                playerOne.moveLeft();
            }
        });
        
        this.getInputMap(WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("typed d"), "pressed one right");
        this.getActionMap().put("pressed one right", new AbstractAction(){
            @Override
            public void actionPerformed(ActionEvent ae){
                playerOne.moveRight();
            }
        });
        
        this.getInputMap(WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("typed w"), "pressed one up");
        this.getActionMap().put("pressed one up", new AbstractAction(){
            @Override
            public void actionPerformed(ActionEvent ae){
                playerOne.moveUp();
            }
        });
        
        this.getInputMap(WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("typed s"), "pressed one down");
        this.getActionMap().put("pressed one down", new AbstractAction(){
            @Override
            public void actionPerformed(ActionEvent ae){
                playerOne.moveDown();
            }
        });
        
        //playerTwo
        this.getInputMap(WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("LEFT"), "pressed two left");
        this.getActionMap().put("pressed two left", new AbstractAction(){
            @Override
            public void actionPerformed(ActionEvent ae){
                playerTwo.moveLeft();
            }
        });
        
        this.getInputMap(WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("RIGHT"), "pressed two right");
        this.getActionMap().put("pressed two right", new AbstractAction(){
            @Override
            public void actionPerformed(ActionEvent ae){
                playerTwo.moveRight();
            }
        });
        
        this.getInputMap(WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("UP"), "pressed two up");
        this.getActionMap().put("pressed two up", new AbstractAction(){
            @Override
            public void actionPerformed(ActionEvent ae){
                playerTwo.moveUp();
            }
        });
        
        this.getInputMap(WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("DOWN"), "pressed two down");
        this.getActionMap().put("pressed two down", new AbstractAction(){
            @Override
            public void actionPerformed(ActionEvent ae){
                playerTwo.moveDown();
            }
        });
       
        restart(pOne, pOneCol, nOne, pTwo, pTwoCol, nTwo);
        newFrameTimer = new Timer(1000 / gameConsts.getFPS(), new NewFrameListener());
        newFrameTimer.start();
    }
    
    /**
     * This function recreates the players and the level. If the game is started
     * @param pOne
     * @param pOneCol
     * @param nOne
     * @param pTwo
     * @param pTwoCol
     * @param nTwo 
     */
    public void restart(Image pOne, Color pOneCol,String nOne,Image pTwo, Color pTwoCol,String nTwo){
        playerOne = new Player(
                gameConsts.getpOneStartX(),
                gameConsts.getpOneStartY(),
                gameConsts.getPlayerWidth(),
                gameConsts.getPlayerHeight(),
                pOne, pOneCol,
                gameConsts.getVel(),
                nOne
        );
        playerTwo = new Player(
                gameConsts.getpTwoStartX(),
                gameConsts.getpTwoStartY(),
                gameConsts.getPlayerWidth(),
                gameConsts.getPlayerHeight(),
                pTwo, pTwoCol,
                gameConsts.getVel(),
                nTwo
        );
        level = new Level(background);
    }
    
    /**
     * Paints the elements on the panel
     * @param grphcs 
     */
    @Override
    protected void paintComponent(Graphics grphcs){
        //super.paintComponent(grphcs);
        level.draw(grphcs);
        playerOne.draw(grphcs);
        playerTwo.draw(grphcs);
    }
    
    /**
     * This class repaints the panel every frame until the game is over.
     * If the game is over the champion is added to the database and the 
     * celebrate panel is loaded.
     */
    class NewFrameListener implements ActionListener{
        
        @Override
        public void actionPerformed(ActionEvent ae){
            if(!playerOne.collides(playerTwo,level.getWalls()) && !playerTwo.collides(playerOne,level.getWalls())){
                playerOne.move();
                playerTwo.move();
                repaint();
            } else if(playerOne.collides(playerTwo,level.getWalls())) {
                //open celebration menu for playerTwo
                try{
                    Database db = new Database();
                    db.putHighScore(playerTwo.getName());
                } catch(Exception e){
                    System.out.println(e.getMessage());
                }
                newFrameTimer.stop();
                window.switchToCongrats(GameEngine.this, playerTwo, playerOne);
            } else {
                //open celebration menu for playerOne
                try{
                    Database db = new Database();
                    db.putHighScore(playerOne.getName());
                } catch(Exception e){
                    System.out.println(e.getMessage());
                }
                newFrameTimer.stop();
                window.switchToCongrats(GameEngine.this, playerOne, playerTwo);
            }
        }
    }
}
