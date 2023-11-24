import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Monitor2 {
    private SubsekvensRegister beholder = new SubsekvensRegister();
    private ReentrantLock laas = new ReentrantLock();
    private Condition harTo = laas.newCondition();
    private Condition ferdig = laas.newCondition();

    /** Setter inn et hashmap i indeks 0 */
    public void settInn(HashMap<String, Subsekvens> ny) {
        laas.lock();
        try {
            beholder.settInn(ny);
        } finally {
            laas.unlock();
            if (stoerrelse() >= 2) {
                harTo.signal();
            }
        }
    }

    /** Setter inn et hashmap i gitt indeks */
    public void settInn(int indeks, HashMap<String, Subsekvens> ny) {
        laas.lock();
        try {
            beholder.settInn(indeks, ny);
        } finally {
            laas.unlock();
            if (stoerrelse() >= 2) {
                harTo.signal();
            }
        }
    }

    /** Tar ut et hashmap ved indeks 0 */
    public HashMap<String, Subsekvens> taUt() {
        laas.lock();
        try {
            return beholder.taUt();
        } finally {
            laas.unlock();
        }
    }

    /** Tar ut et hashmap ved gitt indeks */
    public HashMap<String, Subsekvens> taUt(int indeks) {
        laas.lock();
        try {
            return beholder.taUt(indeks);
        } finally {
            laas.unlock();
        }
    }

    /** Gir totalt antall subsekvenser */
    public int stoerrelse() {
        return beholder.stoerrelse();
    }

    /** Henter ut to hashmap */
    public ArrayList<HashMap<String, Subsekvens>> taUtTo() {
        ArrayList<HashMap<String, Subsekvens>> ut = new ArrayList<>();
        if (stoerrelse() < 2) {
            try {
                harTo.await();
                ut.add(taUt());
                ut.add(taUt());
            } catch (InterruptedException e) {
            }
        }
        return ut;
    }

    public void settInnFlettet(HashMap<String, Subsekvens> ny) {
        laas.lock();
        try {
            beholder.settInn(ny);
        } finally {
            laas.unlock();
            if (stoerrelse() >= 2) {
                harTo.signal();
            }
        }
    }
}