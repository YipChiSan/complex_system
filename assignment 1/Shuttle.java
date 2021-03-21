import javax.print.attribute.standard.Destination;
/**
 * A shuttle is to pass a vial from one place to another
 */

 public class Shuttle extends VaccineHandlingThread {
    protected Destination start;
    protected Destination destination;

    /**
     * Create a new Shuttle to transfer vial from start to destination. 
     * @param destinations
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
                this.destination.putVialByShuttle(holdingVial);
                holdingVial = this.destination.getVialByShuttle();
                sleep(Params.SHUTTLE_TIME);
                this.start.putVialByShuttle(holdingVial);
            } catch (InterruptedException e){
                this.interrupt();
            }
        }
        System.out.println("Shuttle terminated");
    }



 }