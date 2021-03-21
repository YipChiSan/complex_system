interface Destination{
    public void putVialByShuttle(Vial vial) throws InterruptedException;
    public Vial getVialByShuttle() throws InterruptedException;
} 