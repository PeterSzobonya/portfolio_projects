package bead_egy;

/**
 * This class is a child of the Plant class
 * 
 * Alfa radiation -3 soil
 * Delta radiation +4 soil
 * No radiation -1 soil
 * 
 * If soil is less than 5: +4 delta radiation
 * If soil is between 5 and 10: +1 delta radiation
 * If soil is over 10: no influence on radiation
 * 
 * @author peter
 */
public class Deltafa extends Plant{
    
    //constructor
    /**
     * Calls the parent constructor
     * @param name
     * @param soil 
     */
    public Deltafa(String name, int soil){
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

            if(isAlive()){
                if(soil < 5) {
                    radiation.increaseDBy(4);
                } else if(soil >= 5 && soil <= 10) {
                    radiation.increaseDBy(1);
                }
            }
        }
    }
    
    /**
     * If there is alfa radiation this method is called
     * 
     * -3 soil
     */
    @Override
    protected void alfaRad(){
        soil -= 3;
    }
    
    /**
     * If there is delta radiation this method is called
     * 
     * +4 soil
     */
    @Override
    protected void deltaRad(){
        soil += 4;
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
