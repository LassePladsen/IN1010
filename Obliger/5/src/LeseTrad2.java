public class LeseTrad2 extends Thread {
    private String filnavn;
    private Monitor2 monitor;

    public LeseTrad2(String filnavn, Monitor2 monitor) {
        this.filnavn = filnavn;
        this.monitor = monitor;
    }

    /** Leser en fil og legger resulterende hashmap inn i monitor */
    public void run() {
        monitor.settInn(SubsekvensRegister.lesFraFil(filnavn));
    }
}