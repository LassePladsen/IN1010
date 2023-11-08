public class SaksProdusent implements Runnable {
    private final SaksMonitor smon;
    private final KnivMonitor kmon;
    private final int antallSakser;

    public SaksProdusent(int antallSakser, SaksMonitor smon, KnivMonitor kmon) {
        this.antallSakser = antallSakser;
        this.kmon = kmon;
        this.smon = smon;
    }

    @Override
    public void run() {
        for (int i = 0; i < antallSakser; i++) {
            smon.settInn(new Saks(kmon.taUt2()));
        }
    }
}
