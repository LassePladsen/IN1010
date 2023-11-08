import java.util.ArrayList;

public class BankRunnable implements Runnable {
    private final Bank bank;
    private final double belop;

    public BankRunnable(Bank bank, double belop) {
        this.bank = bank;
        this.belop = belop;
    }
    
    @Override
    public void run() {
        for (int i = 0; i < 1000; i++) {
            bank.ta(belop);
            bank.gi(belop);
        }
        System.err.println("Belopet er " + bank.saldo());
    }

    public static void main(String[] args) {
        Bank bank = new Bank(500.0);
        ArrayList<Thread> traader = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Thread traad = new Thread(new BankRunnable(bank, i*10));
            traader.add(traad);
            traad.start();
        }
        
        for (Thread traad : traader) {
            try {
                traad.join();
            }
            catch (InterruptedException e) {
                System.err.println("Avbrutt2!");
            }
        }

        System.err.println("Ferdig! Slutt-saldo er " + bank.saldo());
    }
}