public class NteTall implements Runnable {
    private final int start, slutt, n;
    
    public NteTall(int start, int slutt, int n) {
        this.start = start;
        this.slutt = slutt;
        this.n = n;
    }
    
    @Override
    public void run() {
        int i = start;
        while (i < slutt) {
            Monitor.skrivTall(i);
            i += n;
        }
    }
    
    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            Thread traad = new Thread(new NteTall(i, 10000, 10));
            traad.start();
        }
    }
}
