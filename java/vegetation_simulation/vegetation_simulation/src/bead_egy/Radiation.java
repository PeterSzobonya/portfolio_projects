package bead_egy;

/**
 *This class handles everything connected to radiation.
 * It stores a list of radiation which has a corresponding radiation id
 * that can tell us which is the present radiation. Also it stores a the
 * requested alfa and delta values so those can be compared to decide which
 * radiation will the simulation has for the next day.
 * 
 * @author peter
 */
public class Radiation {
    private String[] types = {"nincs", "alfa", "delta"};
    private int radId;
    private int alfaValue;
    private int deltaValue;
    
    
    //constructor
    /**
     * Sets the starting values to neutral
     */
    public Radiation(){
        radId = 0;
        alfaValue = 0;
        deltaValue = 0;
    }
    
    //methods
    /**
     * This method can alter the alfa radiation value
     * @param value 
     */
    protected void increaseABy(int value){
        alfaValue += value;
    }
    
    /**
     * This method can alter the delta radiation value
     * @param value 
     */
    protected void increaseDBy(int value){
        deltaValue += value;
    }
    
    /**
     * This method compares the alfa and delta values
     */
    public void updateRad(){
        if(alfaValue-deltaValue >= 3){
            radId = 1;
        } else if(deltaValue-alfaValue >= 3){
            radId = 2;
        } else {
            radId = 0;
        }
        alfaValue = 0;
        deltaValue = 0;
    }
    
    
    //getters setters
    /**
     * Returns the type of the present radiation
     * @return 
     */
    public String getType() {
        return types[radId];
    }

    /**
     * Returns the id that of the present radiation
     * @return 
     */
    public int getRadId() {
        return radId;
    }

    /**
     * The radiation id can be set here
     * @param radId 
     */
    public void setRadId(int radId) {
        this.radId = radId;
    }

}
