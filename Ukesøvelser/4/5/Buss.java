
public class Buss extends Motorkjøretøy {
    private int antallPlasser;
    
    public Buss(String regnr, String merke, String eier, int antallPlasser) {
        super(regnr, merke, eier);
        this.antallPlasser = antallPlasser;
    }

    @Override
    public void skrivInfo() {
        System.out.println("Buss:");
        super.skrivInfo();
        System.out.println("Antall sitteplasser: " + antallPlasser);
    }

    public int hentAntallPlasser() { return antallPlasser; }

}   

