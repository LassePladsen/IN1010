import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        KnivMonitor kmon = new KnivMonitor();
        SaksMonitor smon = new SaksMonitor();
        ArrayList<Thread> kTraader = new ArrayList<>(); 
        ArrayList<Thread> sTraader = new ArrayList<>(); 
        
        // KNIVER
        for (int i = 1; i < 10; i++) {
            Thread traad = new Thread(new KnivProdusent(10, kmon));
            traad.start();
            kTraader.add(traad);
        }
        
        for (Thread traad : kTraader) {
            try {
                traad.join();
            }
            catch (InterruptedException e) {
                System.err.println("Avbrutt");
            }
        }
        
        
        // SAKSER 
        for (int i = 1; i < 10; i++) {
            Thread traad = new Thread(new SaksProdusent(5, smon, kmon));
            traad.start();
            kTraader.add(traad);
        }

        for (Thread traad : sTraader) {
            try {
                traad.join();
            }
            catch (InterruptedException e) {
                System.err.println("Avbrutt");
            }
        }
        
        System.err.println("Ferdig, antall kniver=" + kmon.antall() + ", antall sakser=" + smon.antall());
    }
}
