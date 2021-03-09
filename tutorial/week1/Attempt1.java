class Count extends Thread {

    // number of increments per thread
    static int N = 20000;

    // shared data
    static volatile int counter = 0;
    
    // protocol variables
    static volatile int shared_turn = 1;
    private int turn;

    public void run() {
      int temp;
      for (int i = 0; i < 2*N; i++) {
        // non-critical section

        // pre-protocol section
        while (shared_turn != turn);
      
        // critical section
        temp = counter;
        counter = temp + 1;

        // post-protocol section
        shared_turn = -this.turn;
      }
    }

    public Count(Integer turn){
    
      this.turn = turn;
    }

    public static void main(String[] args) {
      Count p = new Count(1);
      Count q = new Count(-1);
      p.start();
      q.start();
      try { p.join(); q.join(); }
      catch (InterruptedException e) { }
      System.out.println("The final value of the counter is " + counter);
    }
}
