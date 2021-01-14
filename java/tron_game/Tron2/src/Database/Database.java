/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Properties;

/**
 *Database handler class
 * 
 * @author peter
 */
public class Database {
    PreparedStatement insertStatement;
    PreparedStatement deleteStatement;
    PreparedStatement findStatement;
    PreparedStatement updateStatement;
    Connection connection;
    
    public Database() throws SQLException{
        Properties connectionProps = new Properties();
        // Add new user -> MySQL workbench (Menu: Server / Users and priviliges)
        //                             Tab: Administrative roles -> Check "DBA" option
        connectionProps.put("user", "peter");
        connectionProps.put("password", "Password*_12");
        connectionProps.put("serverTimezone", "UTC");
        String dbURL = "jdbc:mysql://localhost:3306/sqlTron";
        connection = DriverManager.getConnection(dbURL, connectionProps);
        
        String insertQuery = "INSERT INTO sqlTron.highScores (id, name, wins) VALUES (?, ?, ?)";
        insertStatement = connection.prepareStatement(insertQuery);
        String deleteQuery = "DELETE FROM sqlTron.highScores WHERE name=?";
        deleteStatement = connection.prepareStatement(deleteQuery);
        String findQuery = "SELECT * FROM sqlTron.highScores WHERE name = ?;";
        findStatement = connection.prepareStatement(findQuery);
        String updateQuery = "UPDATE sqlTron.highScores SET wins = ? WHERE id = ?";
        updateStatement = connection.prepareStatement(updateQuery);
    }
    
    /**
     * Returns an array of the whole database
     * @return
     * @throws SQLException 
     */
    public ArrayList<Scores> getHighScores() throws SQLException {
        String query = "SELECT * FROM highScores LIMIT 0, 10";
        ArrayList<Scores> highScores = new ArrayList<>();
        Statement stmt = connection.createStatement();
        ResultSet results = stmt.executeQuery(query);
        while (results.next()) {
            int id = results.getInt("id");
            String name = results.getString("name");
            int score = results.getInt("wins");
            Scores s = new Scores(id,name, score);
            highScores.add(s);
            
        }
        sortHighScores(highScores);
        return highScores;
    }
    
    /**
     * Sorts the high scores in descending orders according to the wins
     * @param highScores 
     */
    private void sortHighScores(ArrayList<Scores> highScores) {
        Collections.sort(highScores, new Comparator<Scores>() {
            @Override
            public int compare(Scores t, Scores t1) {
                return t1.getWins()- t.getWins();
            }
        });
    }
    
    /**
     * Add a new score or update an existing one
     * @param name
     * @throws SQLException 
     */
    public void putHighScore(String name) throws SQLException {
        findStatement.setString(1,name);
        ResultSet set = findStatement.executeQuery();
        if (set.next()) //change it to if not in table
        {
            int score = set.getInt("wins");
            int id = set.getInt("id");

            updateScore(id, score+1);
        } else {
            insertScore(name, 1);
        }
    }
    
    /**
     * Helper for putHighScore puts a new record to the database
     * @param name
     * @param win
     * @throws SQLException 
     */
    private void insertScore(String name, int win) throws SQLException {
        insertStatement.setInt(1,getHighScores().size()+1);
        insertStatement.setString(2, name);
        insertStatement.setInt(3, win);
        insertStatement.executeUpdate();
    }
    
    /**
     * Helper for putHighScore updates a record
     * @param id
     * @param wins
     * @throws SQLException 
     */
    private void updateScore(int id, int wins) throws SQLException{
        updateStatement.setInt(1, wins);
        updateStatement.setInt(2, id);
        updateStatement.executeUpdate();
    }
    
    /**
     * Deletes a record by the name
     * @param name
     * @throws SQLException 
     */
    public void deleteScores(String name) throws SQLException {
        deleteStatement.setString(1, name);
        deleteStatement.executeUpdate();
    }
}
