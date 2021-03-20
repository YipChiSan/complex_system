interface Destination{
    public void putVial(Vial vial) throws InterruptedException;
    public Vial getVial() throws InterruptedException;
} 