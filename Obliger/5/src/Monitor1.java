import java.util.HashMap;
import java.util.concurrent.locks.ReentrantLock;

/** Monitor for SubsekvensRegister */
public class Monitor1 {
    private SubsekvensRegister beholder = new SubsekvensRegister();
    private ReentrantLock laas = new ReentrantLock();

    /** Setter inn et hashmap i indeks 0 */
    public void settInn(HashMap<String, Subsekvens> ny) {
        laas.lock();
        try {
            beholder.settInn(ny);
        } finally {
            laas.unlock();
        }
    }

    /** Setter inn et hashmap i gitt indeks */
    public void settInn(int indeks, HashMap<String, Subsekvens> ny) {
        laas.lock();
        try {
            beholder.settInn(indeks, ny);
        } finally {
            laas.unlock();
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
}