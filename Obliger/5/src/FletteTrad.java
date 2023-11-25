import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.CountDownLatch;

public class FletteTrad extends Thread {
    private final Monitor2 monitor;
    private CountDownLatch barriere;

    public FletteTrad(Monitor2 monitor, CountDownLatch barriere) {
        this.monitor = monitor;
        this.barriere = barriere;
    }

    /** Henter ut to traader, fletter de og setter den tilbake inn i monitor */
    public void run() {
        while (!monitor.erFerdig()) {
            ArrayList<HashMap<String, Subsekvens>> liste = null;
            while (liste == null) {
                liste = monitor.taUtTo();
            }
            HashMap<String, Subsekvens> map1 = liste.remove(0);
            HashMap<String, Subsekvens> map2 = liste.remove(0);
            monitor.settInnFlettet(SubsekvensRegister.flett(map1, map2));
        }
        barriere.countDown();
    }
}
