
/**
 * An InspectionBay is to inspect the defective vaccine and tag them.
 */

 public class InspectionBay
    implements Destination{

    // to help format output trace
    final private static String indentation = "                  ";

    // The vial held by the inspection bay.
    private Vial[] holdingVial;

    public InspectionBay(){
        holdingVial = new Vial[1];
        holdingVial[0] = null;
    }
    // Place a vial to the inspection bay
    @Override
    public synchronized void putVialByShuttle(Vial vial) 
        throws InterruptedException{
        // while there is another vial in the way, block this thread
        while (!isEmpty()) {
            wait();
        }
        
        // insert the element 
        holdingVial[0] = vial;
        
        // notify any waiting threads that the carousel state has changed
        notifyAll();
    }


    // Inspect the vial at the inspection bay
    public synchronized void inspect()
        throws InterruptedException{

        // Inspection bay should wait until there is a vial in the inspection bay.
        while (isEmpty()){
            wait();
        }

        if(!holdingVial[0].isTagged() &&
            holdingVial[0].isDefective()){
            tagVial();
        }

        notifyAll();
    }

    // Pass the inspected vial to the shuttle
    @Override
    public synchronized Vial getVialByShuttle()
        throws InterruptedException{

            Vial returnVial;

            // do not give the holding vial to the shuttle until the vial has been tagged
            while (isEmpty() ||
                (!isEmpty() && !holdingVial[0].isTagged())){
                wait();
            }

            returnVial = holdingVial[0];
            holdingVial[0] = null;

            // notify any waiting threads that the carousel has changed
            notifyAll();
            
    
            return returnVial;
        }

    private boolean isEmpty(){
        return holdingVial[0] == null;
    }

    private void tagVial(){
        holdingVial[0].setInspected();
        holdingVial[0].setTagged();
    }

    
 }