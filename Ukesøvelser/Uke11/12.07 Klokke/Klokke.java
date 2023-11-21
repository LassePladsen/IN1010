import java.time.LocalTime;
import javax.swing.JLabel;

public class Klokke extends Thread {
    JLabel label = new JLabel();

    public Klokke(JLabel label) {
        this.label = label;
    }

    public String tid() {
        LocalTime t = LocalTime.now();
        return String.format("%02d:%02d:%02d", t.getHour(), t.getMinute(),
                t.getSecond());
    }

    @Override
    public void run() {
        while (true) {
            try {
                label.setText(tid());
                sleep(1000);
            } catch (InterruptedException e) {
            }
        }
    }
}
