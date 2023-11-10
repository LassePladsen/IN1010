public class Narkotisk extends Legemiddel {
    private final int styrke;

    public Narkotisk(String navn, int pris, double virkestoff, int styrke) {
        super(navn, pris, virkestoff);
        this.styrke = styrke;
    }

    @Override
    public String toString() {
        return "Narkotisk[" + super.toString() + ", styrke=" + styrke + "]";
    }

    public int hentStyrke() {
        return styrke;
    }
    
}
