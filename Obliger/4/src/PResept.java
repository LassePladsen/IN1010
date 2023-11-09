public class PResept extends HvitResept {
    
    public PResept(
        Legemiddel legemiddel,
        Lege utskrivendeLege,
        Pasient pasient,
        int reit,
        int pris
    ) {
        super(legemiddel, utskrivendeLege, pasient, reit, pris);
    }
    
    @Override
    public int prisAaBetale() {
        int nyPris = prisAaBetale() - 108;
        if (nyPris < 0) {
            return nyPris = 0;
        }
        return nyPris;
    }

    @Override
    public String toString() {
        return "PResept[" + super.toString().substring("HvitResept[".length());
    }
}
