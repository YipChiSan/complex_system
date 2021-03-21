/**
 * The main class for the vaccine fill/finish system simulator.
 */

public class Sim {
    /**
     * The main method to run the simulator.
     */
    public static void main(String[] args) {
        
    	// Create system components
        Carousel carousel = new Carousel();
        Producer producer = new Producer(carousel);
        Consumer consumer = new Consumer(carousel);
        CarouselDrive driver = new CarouselDrive(carousel);
        InspectionBay inspectionBay = new InspectionBay();
        InspectionBayDriver inspectionBayDriver = new InspectionBayDriver(inspectionBay);
        Shuttle shuttle = new Shuttle(carousel, inspectionBay);

        // start threads
        consumer.start();
        producer.start();
        driver.start();
        inspectionBayDriver.start();
        shuttle.start();

        // check all threads still live
        while (consumer.isAlive() && 
               producer.isAlive() && 
               driver.isAlive() &&
               inspectionBayDriver.isAlive() &&
               shuttle.isAlive())
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                VaccineHandlingThread.terminate(e);
            }

        // interrupt other threads
        consumer.interrupt();
        producer.interrupt();
        driver.interrupt();
        inspectionBayDriver.interrupt();
        shuttle.interrupt();

        System.out.println("Sim terminating");
        System.out.println(VaccineHandlingThread.getTerminateException());
        System.exit(0);
    }
}
