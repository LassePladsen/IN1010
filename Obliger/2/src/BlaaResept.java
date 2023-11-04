public class BlaaResept extends Resept {
    protected final int pris;
    
    public BlaaResept(
        Legemiddel legemiddel,
        Lege utskrivendeLege,
        int pasientId,
        int reit,
        int pris
    ) {
        super(legemiddel, utskrivendeLege, pasientId, reit);
        this.pris = pris;
    }
    
    @Override
    public String farge() {
        return "Blaa";
    }
    
    @Override
    public int prisAaBetale() {
        return (int) Math.round(0.25 * pris);
    }
    
    @Override
    public String toString() {
        return "BlaaResept[" + super.toString() + "]";
    }
}   