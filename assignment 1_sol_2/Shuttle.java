
/**
 * A shuttle is to pass a vial from one place to another
 */

 public class Shuttle extends VaccineHandlingThread {

    // to help format output trace
    final private static String indentation = "                  ";
    Destination start;
    Destination destination;
   



    /**
     * Create a new Shuttle to transfer vial from start to destination. 
     * @param destinations
     * @param start
     * @throws IllegalArgumentException
     */
    public Shuttle(Destination start, Destination destination) {
        super();
        this.start = start;
     
        this.destination = destination;      
    }

    /**
     * Loop infinitely to take vial from start tp destination
     */
    public void run(){
        Vial holdingVial;
        while (!isInterrupted()){
            try{
                holdingVial = this.start.getVialByShuttle();
                sleep(Params.SHUTTLE_TIME);

                System.out.println(
                    indentation +
                    holdingVial +
                    " [ S"  + " -> " + this.destination.getNameByShuttle() + " ]");

                this.destination.putVial(holdingVial);
                
                sleep(Params.SHUTTLE_TIME);

                
            } catch (InterruptedException e){
                this.interrupt();
            }
        }
        System.out.println("Shuttle terminated");
    }



 }