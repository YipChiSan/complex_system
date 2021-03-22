import java.util.Random;

/**
 * A consumer continually, at random intervals, tries to take vials 
 * from the final compartment of a carousel.
 */

public class Consumer extends VaccineHandlingThread {

    // the carousel from which the consumer takes vials
    protected Carousel carousel;

    // consumer id
    private int id;

    // format printing information
    final private static String indentation = "                  ";

    /**
     * Create a new Consumer that consumes from a carousel
     */
    public Consumer(Carousel carousel, int id) {
        super();
        this.carousel = carousel;
        this.id = id;
    }

    /**
     * Loop indefinitely trying to get vials from the carousel
     */
    public void run() {
        Vial vial;
        while (!isInterrupted()) {
            try {
                vial = carousel.getVial();
                // make a note of the event in output trace
                System.out.print(indentation + indentation);
                System.out.println(vial + " removed by Consumer" + this.id);

                // let some time pass ...
                Random random = new Random();
                int sleepTime = Params.CONSUMER_MIN_SLEEP + 
                		random.nextInt(Params.CONSUMER_MAX_SLEEP - 
                				Params.CONSUMER_MIN_SLEEP);
                sleep(sleepTime);
            } catch (InterruptedException e) {
                this.interrupt();
            }
        }
        System.out.println("Consumer terminated");
    }
}
