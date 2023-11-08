import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Bank {
    private double balanse;
    private Lock laas = new ReentrantLock();
    
    public Bank(double startBalanse) {
        balanse = startBalanse;
    }
    
    public void ta(double belop) {
        laas.lock();
        try {
            balanse -= belop;
        }
        finally {
            laas.unlock();
        }
    }
    
    public void gi(double belop) {
        laas.lock();
        try {
            balanse += belop;
        }
        finally {
            laas.unlock();
        }
    }
    
    public double saldo() {
        return balanse;
    }
}
