
public class Varebil extends Motorkjøretøy {
    private int årsAvgift;

    public Varebil(String regNr, String merke, String eier, int årsAvgift) {
        super(regNr, merke, eier);
        this.årsAvgift = årsAvgift;
    }

    @Override
    public void skrivInfo() {
        System.out.println("Varebil:");
        super.skrivInfo();
        System.out.println("Årsavgift: " + årsAvgift);
    }
    
    public int hentÅrsAvgift() { return årsAvgift; }
}

    