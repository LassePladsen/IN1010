public class KnivProdusent implements Runnable {
    private final KnivMonitor monitor;
    private final int antallKniver;

    public KnivProdusent(int antallKniver, KnivMonitor monitor) {
        this.monitor = monitor;
        this.antallKniver = antallKniver;
    }

    @Override
    public void run() {
        for (int i = 0; i < antallKniver; i++) {
            monitor.settInn(new Kniv());
        }
    }
}
