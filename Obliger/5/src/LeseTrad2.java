import java.util.concurrent.CountDownLatch;

public class LeseTrad2 extends Thread {
    private String filnavn;
    private Monitor2 monitor;
    private CountDownLatch barriere;

    public LeseTrad2(String filnavn, Monitor2 monitor, CountDownLatch barriere) {
        this.filnavn = filnavn;
        this.monitor = monitor;
        this.barriere = barriere;
    }

    /** Leser en fil og legger resulterende hashmap inn i monitor */
    public void run() {
        monitor.settInn(SubsekvensRegister.lesFraFil(filnavn));
        barriere.countDown();
    }
}