package bead_egy;

/**
 * This class is a child of the Plant class
 * 
 * Alfa radiation +2 soil
 * Delta radiation -2 soil
 * No radiation -1 soil
 * 
 * It affects the next days radiation by 10 for alfa radiation
 * 
 * This plant dies if the soil level is above 10
 * 
 * @author peter
 */
public class Puffancs extends Plant{
    
    //constructor
    /**
     * Calls the parent constructor
     * @param name
     * @param soil 
     */
    public Puffancs(String name, int soil){
        super(name, soil);
    }
    
    //methods
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

            if(soil > 10 || soil <= 0){
                setAlive(false);
            }

            if(isAlive()){
                radiation.increaseABy(10);
            }
        }
    }
    
    /**
     * If there is alfa radiation this method is called
     * 
     * +2 soil
     */
    @Override
    protected void alfaRad(){
        soil += 2;
    }
    
    /**
     * If there is delta radiation this method is called
     * 
     * -2 soil
     */
    @Override
    protected void deltaRad(){
        soil -= 2;
    }
    
    /**
     * If there is no radiation this method is called
     * 
     * -3 soil
     */
    @Override
    protected void noRad(){
        soil -= 1;
    }
}
