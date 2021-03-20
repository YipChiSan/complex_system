/**
 * A shuttle is to pass a vial from one place to another
 */

 public class Shuttle {
    protected Destination[] destinations;

    /**
     * Create a new Shuttle to transfer vial to one place to another 
     * @param destinations
     * @throws IllegalArgumentException
     */
    public Shuttle(Destination[] destinations) 
        throws IllegalArgumentException{
        if (destinations.length != 2){ 
            throw new IllegalArgumentException();
        } else{
            this.destinations = destinations;
        }  
    }



 }