abstract public class Resept {
    protected final Legemiddel legemiddel;
    protected final Lege utskrivendeLege;
    protected final int pasientId;
    protected final int id;
    protected int reit;
    
    private static int ledig_id;

    public Resept(
        Legemiddel legemiddel, 
        Lege utskrivendeLege, 
        int pasientId, 
        int reit
    ) {
        this.legemiddel = legemiddel;
        this.utskrivendeLege = utskrivendeLege;
        this.pasientId = pasientId;
        this.reit = reit;
        id = ledig_id;
        ledig_id++;
    }

    public Legemiddel hentLegemiddel() {
        return legemiddel;
    }

    public Lege hentLege() {
        return utskrivendeLege;
    }

    public int hentId() {
        return id;
    }

    public int hentPasientId() {
        return pasientId;
    }

    public int henttReit() {
        return reit;
    }

    /* Prøver å bruke resepten én gang. Returnerer true hvis det gikk,
     * false hvis ikke.
     */
    public boolean bruk() {
        if (reit == 0) {
            return false;
        } else {
            reit --;
            return true;
        }
    }

    /* Returnerer reseptens farge */
    abstract public String farge();

    /* Returnerer reseptens pris */
    abstract public int prisAaBetale();

    @Override
    public String toString() {
        return "legemiddel=(" + legemiddel.toString() + ")"
            + ", utskrivende lege=(" + utskrivendeLege.toString() + ")"
            + ", pasient id=" + pasientId
            + ", reit=" + reit
            + ", id=" + id;
    }
}
