public class MilResept extends HvitResept {
    
    public MilResept(
        Legemiddel legemiddel,
        Lege utskrivendeLege,
        int pasientId,
        int pris
    ) {
        super(legemiddel, utskrivendeLege, pasientId, 3, pris);
    }
    
    @Override
    public int prisAaBetale() {
        return 0;
    }

    @Override
    public String toString() {
        return "MilResept[" + super.toString().substring("HvitResept[".length());
    }
}
