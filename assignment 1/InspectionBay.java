import java.util.ArrayList;

/**
 * An InspectionBay is to inspect the defective vaccine and tag them.
 */

 public class InspectionBay {

    // The vial held by the inspection bay.
    private ArrayList<Vial> holdingVial = new ArrayList<>(); 

    // Place a vial to the inspection bay
    public void setVial(Vial vial) {
        this.holdingVial.add(vial);
    }


    // Inspect the vial at the inspection bay
    public synchronized void inspect()
        throws OverloadException{

        // Inspection bay should wait until there is a vial in the inspection bay.
        while (holdingVial.isEmpty()){
            wait();
        }

        // Inspection cannot hold more than one vial.
        if (holdingVial.size() > 1) {
            throw OverloadException("An inspection bay cannot hold more than one vial");
        }

        if(!holdingVial.get(0).isTagged()){
            tagVial();
        }
    }

    // Pass the inspected vial to the shuttle
    public synchronized Vial giveVial()
        throws OverloadException{

            // do not give the holding vial to the shuttle until the vial has been tagged
            while (holdingVial.isEmpty() ||
                (!holdingVial.isEmpty() && !holdingVial.get(0).isTagged())){
                wait();
            }
    
            // Inspection cannot hold more than one vial.
            if (holdingVial.size() > 1) {
                throw OverloadException("An inspection bay cannot hold more than one vial");
            }
    
            return holdingVial.remove(0);
        }

    public boolean isFull(){
        return !holdingVial.isEmpty();
    }

    private void tagVial(){
        holdingVial.get(0).setInspected();
        holdingVial.get(0).setTagged();
    }

    
 }