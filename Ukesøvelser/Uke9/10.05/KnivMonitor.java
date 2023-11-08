import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.Condition;
import java.util.ArrayList;

public class KnivMonitor {
    private ArrayList<Kniv> kniver = new ArrayList<>();
    private final Lock knivLaas = new ReentrantLock();
    private final Condition ikkeTomt = knivLaas.newCondition();
    
    public void settInn(Kniv kniv) {
        knivLaas.lock();
        try {
            kniver.add(kniv);
            ikkeTomt.signal();
        }
        finally {
            knivLaas.unlock();
        }
    }
    
    public Kniv[] taUt2() {
        knivLaas.lock();
        try {
            while (kniver.size() < 2) {
                ikkeTomt.await();
            }
            Kniv Kniv1 = kniver.remove(0);
            Kniv Kniv2 = kniver.remove(0);
            Kniv[] ut = {Kniv1, Kniv2};
            return ut;
        }
        catch (InterruptedException e) {
            System.err.println("Avbrutt u KnivMonitor");
            return null;
        }
        finally {
            knivLaas.unlock();
        }
    }

    public int antall() {
        return kniver.size();
    }
}
