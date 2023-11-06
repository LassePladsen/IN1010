import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.Condition;

public class Monitor {
    private static final Lock laas = new ReentrantLock();
    private static final Condition klar = laas.newCondition();
    private static int sisteTall = -1;

    public static void skrivTall(int tall) {
        laas.lock();
        try {
            while (tall != sisteTall + 1) {
                klar.await();
            }
            System.err.println(tall);
            sisteTall = tall;
            klar.signalAll();
        }
        catch (InterruptedException e) {
            System.err.println("Avbrutt");
        }
        finally {
            laas.unlock();
        }
    }
}
