
public abstract class Motorkjøretøy {
    private String regNr;
    private String merke;
    private String eier;

    public Motorkjøretøy(String regNr, String merke, String eier) {
        this.regNr = regNr;
        this.merke = merke;
        this.eier = eier;
    }

    public void skrivInfo() {
        System.out.println("Registreringsnummer: " + regNr);
        System.out.println("Fabrikkmerke: " + merke);
        System.out.println("Navn på eier: " + eier);
    }

    public String hentRegNr()  {return regNr; }

    public String hentMerke() { return merke; }

    public String hentEier() { return eier; }
}
