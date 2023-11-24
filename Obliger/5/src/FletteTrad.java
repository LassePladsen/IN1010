import java.util.ArrayList;
import java.util.HashMap;

public class FletteTrad extends Thread {
    private final int antallTrader = 8;
    private final Monitor2 monitor = new Monitor2();

    /** Henter ut to traader, fletter de og setter den tilbake inn i monitor */
    public void run() {
        int teller = 0;
        while (teller < antallTrader) {
            ArrayList<HashMap<String, Subsekvens>> liste = monitor.taUtTo();
            HashMap<String, Subsekvens> map1 = liste.get(0);
            HashMap<String, Subsekvens> map2 = liste.get(0);
            monitor.settInnFlettet(SubsekvensRegister.flett(map1, map2));
            teller += 2;
        }
    }
}
