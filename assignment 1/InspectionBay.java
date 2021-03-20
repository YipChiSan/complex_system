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


    // Take the vial from the inspection bay
    public Vial giveVial(){
       
        return holdingVial.remove(0);
    }

    public boolean isFull(){
        return !holdingVial.isEmpty();
    }

    public void tagVial(){
        holdingVial.get(0).setInspected();
        holdingVial.get(0).setTagged();
    }
 }