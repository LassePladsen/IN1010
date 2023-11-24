
public class LeseTrad extends Thread {
    private String filnavn;
    private Monitor1 monitor;

    public LeseTrad(String filnavn, Monitor1 monitor) {
        this.filnavn = filnavn;
        this.monitor = monitor;
    }

    /** Leser en fil og legger resulterende hashmap inn i monitor */
    public void run() {
        monitor.settInn(SubsekvensRegister.lesFraFil(filnavn));
    }
}