public class Subsekvens {
    public final String tekst;
    private int antall;

    public Subsekvens(String tekst, int antall) {
        this.tekst = tekst;
        this.antall = antall;
    }

    public String toString() {
        return "(" + tekst + "," + antall + ")";
    }

    public int hentAntall() {
        return antall;
    }

    public void oekAntall() {
        this.antall += 1;
    }

    public void oekAntall(int antall) {
        this.antall += antall;
    }
}