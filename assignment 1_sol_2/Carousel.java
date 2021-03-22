/**
 * The carousel holds vials of vaccine and rotates them from the compartment
 * at position 0, through to the scanner compartment, where they are 
 * scanned and potentially removed by a shuttle for further inspection, 
 * through to the final compartment where they are taken off the carousel.
 */
public class Carousel implements Destination {

    // the items in the carousel segments
    protected Vial[] compartment;

    // the id for this carousel
    private int id;

    // to help format output trace
    final private static String indentation = "                  ";

    /**
     * Create a new, empty carousel, initialised to be empty.
     */
    public Carousel(int size, int id) {
        this.id = id;
        compartment = new Vial[size];
        for (int i = 0; i < compartment.length; i++) {
            compartment[i] = null;
        }
    }

    /**
     * Insert a vial into the carousel.
     * 
     * @param vial
     *            the vial to insert into the carousel.
     * @throws InterruptedException
     *            if the thread executing is interrupted.
     */
    public synchronized void putVial(Vial vial)
            throws InterruptedException {

    	// while there is another vial in the way, block this thread
        while (compartment[0] != null) {
            wait();
        }

        // insert the element at the specified location
        compartment[0] = vial;

        // make a note of the event in output trace
        System.out.println(vial + " inserted");

        // notify any waiting threads that the carousel state has changed
        notifyAll();
    }

    /**
     * Remove a vial from the final compartment of the carousel
     * 
     * @return the removed vial
     * @throws InterruptedException
     *             if the thread executing is interrupted
     */
    public synchronized Vial getVial() throws InterruptedException {

    	// the vial to be removed
        Vial vial;

        // while there is no vial in the final compartment, block this thread
        while (compartment[compartment.length - 1] == null) {
            wait();
        }

        // get the vial
        vial = compartment[compartment.length-1];
        compartment[compartment.length - 1] = null;

        // notify any waiting threads that the carousel has changed
        notifyAll();
        return vial;
    }

    /**
     * Remove a vial from the middle compartment of the carousel
     * 
     * @return the removed vial
     * @throws InterruptedException
     *             if the thread executing is interrupted
     */
    @Override
    public synchronized Vial getVialByShuttle() throws InterruptedException {

    	// the vial to be removed
        Vial vial;

        // while there is no vial in the middle compartment or the vial is not defective, block this thread
        while (compartment[compartment.length / 2] == null || 
            (compartment[compartment.length / 2] != null &&
             (!compartment[compartment.length / 2].isDefective() || 
             compartment[compartment.length / 2].isTagged()))) {
            wait();
        }

        // get the vial
        vial = compartment[compartment.length / 2];
        compartment[compartment.length / 2] = null;

        // make a note of the event in output trace
        System.out.println(
            indentation +
            vial +
            " [ c"+ "_" + this.id + "_" + ((compartment.length / 2 + 1)) + " -> S" +" ]");

        // notify any waiting threads that the carousel has changed
        notifyAll();
        return vial;
    }
 
    /**
     * Rotate the carousel one position.
     * 
     * @throws OverloadException
     *             if a vial is rotated beyond the final compartment.
     * @throws InterruptedException
     *             if the thread executing is interrupted.
     */
    public synchronized void rotate() 
            throws InterruptedException, OverloadException {
        // if there is in the final compartment, or the carousel is empty,
        // or a vial needs to be removed for inspection, do not move the carousel
        while (isEmpty() || 
        		compartment[compartment.length-1] != null) {
            wait();
        }

        // double check that a vial cannot be rotated beyond the final compartment
        if (compartment[compartment.length-1] != null) {
            String message = "vial rotated beyond final compartment";
            throw new OverloadException(message);
        }

        // a vial cannot be rotated if it is defective and not tagged and in the middle of carousel
        while (compartment[compartment.length / 2] != null &&
            compartment[compartment.length / 2].isDefective() &&
            !compartment[compartment.length / 2].isTagged()) {
                wait();
            }


        // move the elements along, making position 0 null
        for (int i = compartment.length-1; i > 0; i--) {
            if (this.compartment[i-1] != null) {
                System.out.println(
                		indentation +
                		this.compartment[i-1] +
                        " [ c" + "_" + this.id + "_" + (i) + " -> c" + "_" + this.id + "_" + (i+1) +" ]");
            }
            compartment[i] = compartment[i-1];
        }
        compartment[0] = null;
        
        // notify any waiting threads that the carousel has changed
        notifyAll();
    }
 
    /**
     * Check whether the carousel is currently empty.
     * @return true if the carousel is currently empty, otherwise false
     */
    private boolean isEmpty() {
        for (int i = 0; i < compartment.length; i++) {
            if (compartment[i] != null) {
                return false;
            }
        }
        return true;
    }
    
    public String toString() {
        return java.util.Arrays.toString(compartment);
    }
    
    @Override
    public String getNameByShuttle(){
        return "c" + "_" + this.id + "_0"; 
    }

}
