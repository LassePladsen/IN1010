public class Vanedannende extends Legemiddel {
    private final int styrke;

    public Vanedannende(String navn, int pris, double virkestoff, int styrke) {
        super(navn, pris, virkestoff);
        this.styrke = styrke;
    }

    @Override
    public String toString() {
        return "Vanedannende[" + super.toString() + ", styrke=" + styrke + "]";
    }

    public int hentStyrke() {
        return styrke;
    }
    
}
