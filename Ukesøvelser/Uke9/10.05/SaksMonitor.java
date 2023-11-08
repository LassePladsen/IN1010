import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.Condition;
import java.util.ArrayList;

public class SaksMonitor {
    private ArrayList<Saks> sakser = new ArrayList<>();
    private final Lock saksLaas = new ReentrantLock();
    private final Condition ikkeTomt = saksLaas.newCondition();
    
    public void settInn(Saks saks) {
        saksLaas.lock();
        try {
            sakser.add(saks);
            ikkeTomt.signal();
        }
        finally {
            saksLaas.unlock();
        }
    }

    public int antall() {
        return sakser.size();
    }
}
