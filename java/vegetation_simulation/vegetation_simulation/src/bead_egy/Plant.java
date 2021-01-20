package bead_egy;


/**
 * This is the parent class for every plant.
 * 
 * Every plant has a name, a counter for their soil and they store the fact
 * weather they are dead or alive
 * 
 * There is a static variable for radiation which is influenced by the need of 
 * the plants
 * 
 * Every plant dies if their soil is 0 or less
 * 
 * @author peter
 */

public abstract class Plant {
    private String name;
    protected int soil;
    private boolean alive;
    protected static Radiation radiation = new Radiation();
    
    //constructor
    /**
     * Constructor sets the name and soil to the given value and on creation
     * the plant is alive
     * 
     * @param name
     * @param soil 
     */
    public Plant(String name, int soil){
        this.name = name;
        this.soil = soil;
        alive = true;
    }
    
    //methods
    /**
     * Controlls the effects of radiation. Checks weather the plant is dead or 
     * alive. If the plant is alive it affects the radiation with its needs.
     */
    public abstract void rad();
    /**
     * Part of the function rad. Only used if the radiation value is alfa.
     */
    protected abstract void alfaRad();
    /**
     * Part of the function rad. Only used if the radiation value is delta.
     */
    protected abstract void deltaRad();
    /**
     * Part of the function rad. Only used if there is no radiation.
     */
    protected abstract void noRad();
    
    //getters setters
    /**
     * getter of the name of the plant
     * @return name(string)
     */
    public String getName() {
        return name;
    }

    /**
     * getter of the soil value.
     * @return soil(int)
     */
    public int getSoil() {
        return soil;
    }
    /**
     * getter for the alive value
     * @return alive(boolean)
     */
    public boolean isAlive() {
        return alive;
    }

    /**
     * the logical value whether the plant is alive or dead can be set here
     * @param isAlive 
     */
    public void setAlive(boolean isAlive) {
        this.alive = isAlive;
    }

    /**
     * getter for the radiation
     * @return the radiation
     */
    public static Radiation getRadiation() {
        return radiation;
    }
    
    /**
     * This function can reset the radiation to neutral. (for testing)
     */
    public static void resetRadiation() {
        radiation = new Radiation();
    }
    
    
}