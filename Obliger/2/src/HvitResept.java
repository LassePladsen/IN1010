public class HvitResept extends Resept {
    protected final int pris;
    
    public HvitResept(
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
        return "Hvit";
    }
    
    @Override
    public int prisAaBetale() {
        return pris;
    }   

    @Override
    public String toString() {
        return "HvitResept[" + super.toString() + "]";
    }
    
}
