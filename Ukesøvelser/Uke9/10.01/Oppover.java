class Oppover implements Runnable{
  private Monitor monitor;
  private int tall;
  private boolean minMindreEnnStorst = true;

  public Oppover(Monitor monitor){
    this.monitor = monitor;
    tall = Integer.MIN_VALUE;
  }

  @Override
  public void run(){
      while(monitor.settMinste(tall)){
        tall++;
      }
      System.out.println("STOPP! Tallet er ikke lenger minst: " + tall);
    }
}