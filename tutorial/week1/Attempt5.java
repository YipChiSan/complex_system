class Count extends Thread {

    // number of increments per thread
    static int N = 10000;

    // shared data
    static volatile int counter = 0;
    
    // protocol variables
    // ...
    private int turn;
    static volatile int q = 0;
    static volatile int p = 0;
    static volatile int shared_turn = 1;

    public void run() {
      int temp;
      for (int i = 0; i < 2*N; i++) {
        // non-critical section

        // pre-protocol section
        if(this.turn == 1){//p
          Count.p = 1;
          while(Count.q != 0){
            if(shared_turn == 2){
              Count.p = 0;
              while(shared_turn == 2);
              Count.p = 1;
            }

          };
          
        }else if(this.turn == -1){//q
          Count.q = 1;
          while(Count.p != 0){
            if(shared_turn == 1){
              Count.q = 0;
              while(shared_turn == 1);
              Count.q = 1;
            }

          };
          
        }

        // critical section
        temp = counter;
        counter = temp + 1;

        // post-protocol section
        if(this.turn == 1){//p
          Count.shared_turn = 2;
          Count.p = 0;
        }else if(this.turn == -1){//q
          Count.shared_turn = 1;
          Count.q = 0;
        }
      }
    }

    public Count(int turn){
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
