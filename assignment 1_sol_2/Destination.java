interface Destination{
    public void putVial(Vial vial) throws InterruptedException;
    public Vial getVialByShuttle() throws InterruptedException;
    public String getNameByShuttle();
} 