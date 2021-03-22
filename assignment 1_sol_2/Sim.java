/**
 * The main class for the vaccine fill/finish system simulator.
 */

public class Sim {
    /**
     * The main method to run the simulator.
     */
    public static void main(String[] args) {
        
    	// Create system components
        Carousel mainCarousel = new Carousel(5, 1);
        Carousel subCarousel = new Carousel(2, 2);

        Producer producer = new Producer(mainCarousel);
        Consumer mainConsumer = new Consumer(mainCarousel, 1);
        Consumer subConsumber = new Consumer(subCarousel, 2);

        CarouselDrive mainDriver = new CarouselDrive(mainCarousel);
        CarouselDrive subDriver = new CarouselDrive(subCarousel);
        InspectionBay inspectionBay = new InspectionBay();
        InspectionBayDriver inspectionBayDriver = new InspectionBayDriver(inspectionBay);
        Shuttle mainShuttle = new Shuttle(mainCarousel, inspectionBay);
        Shuttle subShuttle = new Shuttle(inspectionBay, subCarousel);

        // start threads
        mainConsumer.start();
        subConsumber.start();
        producer.start();
        mainDriver.start();
        subDriver.start();
        inspectionBayDriver.start();
        mainShuttle.start();
        subShuttle.start();

        // check all threads still live
        while (mainConsumer.isAlive() && 
               subConsumber.isAlive() &&
               subDriver.isAlive() &&
               subShuttle.isAlive() &&
               producer.isAlive() && 
               mainDriver.isAlive() &&
               inspectionBayDriver.isAlive() &&
               mainShuttle.isAlive())
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                VaccineHandlingThread.terminate(e);
            }

        // interrupt other threads
        mainConsumer.interrupt();
        subConsumber.interrupt();
        producer.interrupt();
        mainDriver.interrupt();
        subDriver.interrupt();
        inspectionBayDriver.interrupt();
        mainShuttle.interrupt();
        subShuttle.interrupt();

        System.out.println("Sim terminating");
        System.out.println(VaccineHandlingThread.getTerminateException());
        System.exit(0);
    }
}
