import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Monitor2 {
    private SubsekvensRegister beholder = new SubsekvensRegister();
    private ReentrantLock laas = new ReentrantLock();
    private Condition ikkeTom = laas.newCondition();
    private boolean erFerdig = false;

    /** Setter inn et hashmap i indeks 0 */
    public void settInn(HashMap<String, Subsekvens> ny) {
        laas.lock();
        try {
            beholder.settInn(ny);
        } finally {
            ikkeTom.signal();
            laas.unlock();
        }
    }

    /** Setter inn et hashmap i gitt indeks */
    public void settInn(int indeks, HashMap<String, Subsekvens> ny) {
        laas.lock();
        try {
            beholder.settInn(indeks, ny);
        } finally {
            ikkeTom.signal();
            laas.unlock();
        }
    }

    /** Tar ut et hashmap ved indeks 0 */
    public HashMap<String, Subsekvens> taUt() {
        return beholder.taUt();
    }

    /** Tar ut et hashmap ved gitt indeks */
    public HashMap<String, Subsekvens> taUt(int indeks) {
        return beholder.taUt(indeks);
    }

    /** Gir totalt antall subsekvenser */
    public int stoerrelse() {
        return beholder.stoerrelse();
    }

    /** Henter ut to hashmap */
    public ArrayList<HashMap<String, Subsekvens>> taUtTo() {
        ArrayList<HashMap<String, Subsekvens>> ut = new ArrayList<>();
        laas.lock();
        try {
            while (stoerrelse() < 2) {
                ikkeTom.await();
            }
            ut.add(taUt());
            ut.add(taUt());
            return ut;
        } catch (InterruptedException e) {
        } finally {
            laas.unlock();
        }
        return null;
    }

    public void settInnFlettet(HashMap<String, Subsekvens> ny) {
        laas.lock();
        try {
            beholder.settInn(ny);
        } finally {
            ikkeTom.signal();
            laas.unlock();
        }
    }

    public boolean erFerdig() {
        return erFerdig;
    }

    public void settFerdig() {
        erFerdig = true;
    }
}