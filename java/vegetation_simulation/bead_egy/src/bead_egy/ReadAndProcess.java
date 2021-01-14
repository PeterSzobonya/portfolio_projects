package bead_egy;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * This class reads the data and simulates with the given data
 * 
 * @author peter
 */
public class ReadAndProcess {
    public List<Plant> plants = new ArrayList<>();
    private int numOfPlants=0;
    private int daysToSim=0;
    private String filePath;
    
    /**
     * sets the path of the file
     * @param filePath 
     */
    public ReadAndProcess(String filePath){
        this.filePath = filePath;
    }
    
    /**
     * Reads the file first the number of plants next the plants line by line. 
     * The plants are stored in a list.
     * Where needed exception is thrown.
     * I throw exceptions in the catch only for testing purposes
     * 
     * @throws Exception 
     */
    public void readFile() throws Exception{
        try{
            File file = new File(filePath);
            Scanner reader = new Scanner(file);
            
            numOfPlants = Integer.parseInt(reader.nextLine());
            for(int i=0; i< numOfPlants; i++){
                String currentLine = reader.nextLine();
                
                if(currentLine.split(" ").length == 3){
                    String tempName = currentLine.split(" ")[0];
                    String tempType = currentLine.split(" ")[1];
                    int tempSoil = Integer.parseInt(currentLine.split(" ")[2]);
                    
                    switch(tempType) {
                        case "a" : plants.add(new Puffancs(tempName, tempSoil)); break;
                        case "d" : plants.add(new Deltafa(tempName, tempSoil)); break;
                        case "p" : plants.add(new Parabokor(tempName, tempSoil)); break;
                    }
                } else {
                    throw new IllegalArgumentException("hibas sor");
                }
                
            }
            daysToSim = reader.nextInt();
        } catch(FileNotFoundException e){
            throw e;
        } catch(NoSuchElementException e){
            throw e;
        } catch(IllegalArgumentException e){
            throw e;
        }
    }
    
    /**
     * This function runs the simulation and prints out the daily results and
     * the radiation of the day. 
     * 
     * First it prints the starting state.
     * After printing the starting day it updates the radiation.
     * With the new radiation a cycle starts to simulate the given days. For 
     * every day this function runs the simulation for every plant than updates
     * the radiation, prints the daily results and starts over.
     * 
     * When the simulation is done the dead plants are removed from the plants
     * list so it is easier to test the simulation.
     * 
     * This function was written mainly so I can test easier.
     */
    public void process(){
        //print starting state
        System.out.println("0. nap:\nsugarzas:" + plants.get(0).getRadiation().getType());
        plants.forEach((n) -> System.out.println(n.getName() + " " + n.soil));

        //update rad before simulating the day
        plants.get(0).getRadiation().updateRad();
        
        for(int i=0; i<daysToSim; i++){
            //simulate day
            plants.forEach((n) -> n.rad());
            plants.get(0).getRadiation().updateRad();
            //print results
            System.out.println("\n" + (i+1) + ". nap:\nsugarzas:" + plants.get(0).getRadiation().getType());
            plants.forEach((n) -> {if(n.isAlive()){System.out.println(n.getName() + " " + n.soil);}});
        }
        
        //clean up the list for testing purposes
        List<Plant> tempPlants = new ArrayList<>();
        for(int i=0; i<plants.size(); i++){
            if(plants.get(i).isAlive()){
                tempPlants.add(plants.get(i));
            }
        }
        plants = tempPlants;
    }
}
