/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UI;

import Components.Player;

/**
 *After the match this panel shows the winner and offers steps to take.
 * @author peter
 */
public class Celebration extends javax.swing.JPanel {

    
    Player playerOne;
    Player playerTwo;
    TronWindow window;
    /**
     * Creates new form Celebration
     */
    public Celebration(Player one, Player two, TronWindow window) {
        initComponents();
        this.playerOne = one;
        this.playerTwo = two;
        this.window = window;
        playerNameOne.setText(playerOne.getName());
        playerNameTwo.setText(playerTwo.getName());
        arrowOne.setIcon(new javax.swing.ImageIcon(playerOne.getImage()));
        arrowTwo.setIcon(new javax.swing.ImageIcon(playerTwo.getImage()));
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        playerNameOne = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        arrowOne = new javax.swing.JLabel();
        goStats = new javax.swing.JButton();
        mainMenu = new javax.swing.JButton();
        newGame = new javax.swing.JButton();
        exit = new javax.swing.JButton();
        playerNameTwo = new javax.swing.JLabel();
        arrowTwo = new javax.swing.JLabel();
        playerNameTwo1 = new javax.swing.JLabel();
        playerNameTwo2 = new javax.swing.JLabel();

        jLabel1.setText("jLabel1");

        setBackground(new java.awt.Color(51, 51, 51));

        playerNameOne.setFont(new java.awt.Font("Dialog", 1, 36)); // NOI18N
        playerNameOne.setForeground(new java.awt.Color(255, 255, 255));
        playerNameOne.setText("Player");

        jLabel3.setFont(new java.awt.Font("Dialog", 1, 36)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 51, 51));
        jLabel3.setText("Beat");

        arrowOne.setIcon(new javax.swing.ImageIcon("/home/peter/Documents/elte3/progtech/beadok/3/Tron/src/main/java/resources/blue:arrow2.png")); // NOI18N

        goStats.setText("Go to statistics");
        goStats.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                goStatsActionPerformed(evt);
            }
        });

        mainMenu.setText("Back to main menu");
        mainMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mainMenuActionPerformed(evt);
            }
        });

        newGame.setText("New Game");
        newGame.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                newGameActionPerformed(evt);
            }
        });

        exit.setText("Exit");
        exit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exitActionPerformed(evt);
            }
        });

        playerNameTwo.setFont(new java.awt.Font("Dialog", 1, 36)); // NOI18N
        playerNameTwo.setForeground(new java.awt.Color(255, 255, 255));
        playerNameTwo.setText("Player");

        arrowTwo.setIcon(new javax.swing.ImageIcon("/home/peter/Documents/elte3/progtech/beadok/3/Tron/src/main/java/resources/blue:arrow2.png")); // NOI18N

        playerNameTwo1.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        playerNameTwo1.setForeground(new java.awt.Color(255, 153, 153));
        playerNameTwo1.setText("Better luck nex time...");

        playerNameTwo2.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        playerNameTwo2.setForeground(new java.awt.Color(153, 255, 153));
        playerNameTwo2.setText("Good job Champ :)");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(117, 117, 117)
                .addComponent(arrowOne)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(arrowTwo)
                .addGap(210, 210, 210))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(playerNameTwo1)
                .addGap(134, 134, 134))
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(55, 55, 55)
                        .addComponent(goStats, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(34, 34, 34)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(playerNameTwo2)
                            .addComponent(playerNameOne, javax.swing.GroupLayout.PREFERRED_SIZE, 252, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(6, 6, 6)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addGap(46, 46, 46)
                        .addComponent(playerNameTwo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(111, 111, 111))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(newGame, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(mainMenu))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 114, Short.MAX_VALUE)
                        .addComponent(exit, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(68, 68, 68))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(playerNameOne)
                    .addComponent(jLabel3)
                    .addComponent(playerNameTwo))
                .addGap(40, 40, 40)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(arrowTwo)
                    .addComponent(arrowOne))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(playerNameTwo1)
                    .addComponent(playerNameTwo2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 76, Short.MAX_VALUE)
                .addComponent(newGame)
                .addGap(33, 33, 33)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(goStats)
                    .addComponent(mainMenu)
                    .addComponent(exit))
                .addGap(52, 52, 52))
        );
    }// </editor-fold>//GEN-END:initComponents

    /**
     * Go to statistics panel
     * @param evt 
     */
    private void goStatsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_goStatsActionPerformed
        // TODO add your handling code here:
        window.switchToStatistics(this);
    }//GEN-LAST:event_goStatsActionPerformed

    /**
     * Got to the main menu 
     * @param evt 
     */
    private void mainMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mainMenuActionPerformed
        // TODO add your handling code here:
        window.switchToMenu(this);
    }//GEN-LAST:event_mainMenuActionPerformed

    /**
     * Go to the player selection panel
     * @param evt 
     */
    private void newGameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_newGameActionPerformed
        // TODO add your handling code here:
        window.switchToPlayerSelection(this);
    }//GEN-LAST:event_newGameActionPerformed

    /**
     * Close the application
     * @param evt 
     */
    private void exitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exitActionPerformed
        // TODO add your handling code here:
        System.exit(0);
    }//GEN-LAST:event_exitActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel arrowOne;
    private javax.swing.JLabel arrowTwo;
    private javax.swing.JButton exit;
    private javax.swing.JButton goStats;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JButton mainMenu;
    private javax.swing.JButton newGame;
    private javax.swing.JLabel playerNameOne;
    private javax.swing.JLabel playerNameTwo;
    private javax.swing.JLabel playerNameTwo1;
    private javax.swing.JLabel playerNameTwo2;
    // End of variables declaration//GEN-END:variables
}
