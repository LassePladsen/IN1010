class Nedover implements Runnable{
  private Monitor monitor;
  private int tall;
  private boolean minMindreEnnStorst = true;

  public Nedover(Monitor monitor){
    this.monitor = monitor;
    tall = Integer.MAX_VALUE;
  }

  @Override
  public void run(){
      while(monitor.settStorste(tall)){
        tall--;
      }
      System.out.println("STOPP! Tallet er ikke lenger størst: " + tall);
    }
}