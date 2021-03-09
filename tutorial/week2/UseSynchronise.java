class P extends Thread
{
  //the shared synch instance
  protected Synchronise s;

  public P(Synchronise s)
  {
    this.s = s;
  }

  public void run()
  {
    while (true) {
      task1p();
      s.synch();
      task2p();
    }
  }

  private void task1p()
  {
    System.out.println("1p");
    s.isP1Finished = true;
  }

  private void task2p()
  {
    System.out.println("2p");
    s.isP1Finished = false;
  }
}

class Q extends Thread
{
  //the shared synch instance
  protected Synchronise s;

  public Q(Synchronise s)
  {
    this.s = s;
  }

  public void run()
  {
    while (true) {
      task1q();
      s.synch();
      task2q();
    }
  }

  private void task1q()
  {
    System.out.println("1q");
    s.isQ1Finished = true;
  }

  private void task2q()
  {
    System.out.println("2q");
    s.isQ1Finished = false;
  }
}

class Synchronise
{
  // any useful variables go here
  volatile boolean isP1Finished = false;
  volatile boolean isQ1Finished = false;

  public synchronized void synch()
  {
    // the code to synchronise goes here
    while (!isP1Finished || !isQ1Finished){
      try{
        wait();
      }
      catch (InterruptedException e){}
    }

  }
}

class UseSynchronise
{
  public static void main(String [] args)
  {
    Synchronise s = new Synchronise();
    Thread p = new P(s);
    Thread q = new Q(s);
    p.start();
    q.start();
  }
}
