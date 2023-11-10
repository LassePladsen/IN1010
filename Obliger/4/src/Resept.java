abstract public class Resept {
    private final Legemiddel legemiddel;
    private final Lege utskrivendeLege;
    private final Pasient pasient;
    private final int id;
    private int reit;
    
    private static int ledig_id;

    public Resept(
        Legemiddel legemiddel, 
        Lege utskrivendeLege, 
        Pasient pasient, 
        int reit
    ) {
        this.legemiddel = legemiddel;
        this.utskrivendeLege = utskrivendeLege;
        this.pasient = pasient;
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
        return pasient.hentId();
    }

    public int hentReit() {
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
            + ", pasient id=" + pasient.hentId()
            + ", reit=" + reit
            + ", id=" + id;
    }
}
