/**
 * An Inspection bay driver is to inspect holding vial continuously. 
 */

 public class InspectionBayDriver extends Thread {

     protected InspectionBay inspectionBay;
     
    /**
      * Create a new InspectionBay to inspect
      * @param inspectionBay
      */
    public InspectionBayDriver(InspectionBay inspectionBay){
         this.inspectionBay = inspectionBay;
     }

    /**
      * Inspect the vial as often as possible but only if there is one 
        and only one vial in the inspection bay
      */
      @Override
    public void run(){
        while (!isInterrupted()) {
            try {
                inspectionBay.inspect();
            } catch (OverloadException e) {
                this.interrupt();
            } catch (InterruptedException e) {
                this.interrupt();
            }
        }
    }
 }