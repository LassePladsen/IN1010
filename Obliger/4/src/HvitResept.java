public class HvitResept extends Resept {
    private final int pris;
    
    public HvitResept(
        Legemiddel legemiddel,
        Lege utskrivendeLege,
        Pasient pasient,
        int reit,
        int pris
    ) {
        super(legemiddel, utskrivendeLege, pasient, reit);
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
