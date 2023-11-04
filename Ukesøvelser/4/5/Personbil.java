
public class Personbil extends Motorkjøretøy{
    private int årsAvgift;
    private int antallPlasser;

    public Personbil(String regNr, String merke, String eier,
     int antallPlasser, int årsAvgift) {
        super(regNr, merke, eier);
        this.antallPlasser = antallPlasser;
        this.årsAvgift = årsAvgift;
    }

    @Override
    public void skrivInfo() {
        System.out.println("Personbil:");
        super.skrivInfo();
        System.out.println("Antall sitteplasser: " + antallPlasser);
        System.out.println("Årsavgift: " + årsAvgift);
    }
    
    public int hentAntallPlasser() { return antallPlasser; }

    public int hentÅrsAvgift() { return årsAvgift; }
}
