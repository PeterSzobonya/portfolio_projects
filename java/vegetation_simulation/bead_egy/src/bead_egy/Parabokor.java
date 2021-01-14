
package bead_egy;

/**
 * This class is a child of the Plant class
 * 
 * Alfa radiation +1 soil
 * Delta radiation +1 soil
 * No radiation -1 soil
 * 
 * This plant has no effect on radiation
 * 
 * @author peter
 */
public class Parabokor extends Plant{
    
    //constructor
    /**
    * Calls the parent constructor
    * @param name
    * @param soil 
    */
    public Parabokor(String name, int soil){
        super(name, soil);
    }
    
    /**
     * If the plant is alive checks which radiation is for the present day and
     * calls the corresponding radiation function. When complete checks if the 
     * plant survived the day and if yes tells its influence of radiation for
     * the next day.
     */
    @Override
    public void rad(){
        if(isAlive()){
            switch(radiation.getRadId()){
                case 0 : noRad(); break;
                case 1 : alfaRad(); break;
                case 2 : deltaRad(); break;
            }
            if(soil <= 0){
                setAlive(false);
            }
        }
    }
    
    /**
     * If there is alfa radiation this method is called
     * 
     * +1 soil
     */
    @Override
    protected void alfaRad(){
        soil += 1;
    }
    
    /**
     * If there is delta radiation this method is called
     * 
     * +1 soil
     */
    @Override
    protected void deltaRad(){
        soil += 1;
    }
    
    /**
     * If there is no radiation this method is called
     * 
     * -1 soil
     */
    @Override
    protected void noRad(){
        soil -= 1;
    }
}
