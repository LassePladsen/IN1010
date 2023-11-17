public class Spesialist extends Lege implements Godkjenningsfritak {
    private final String kontrollkode;

    public Spesialist(String navn, String kontrollkode) {
        super(navn);
        this.kontrollkode = kontrollkode;
    }

    @Override
    public String hentKontrollkode() {
        return kontrollkode;
    }

    @Override
    public String toString() {
        String superStreng = super.toString();
        return "Spesialist["
                + superStreng.substring(5, superStreng.length() - 1)
                + ", kontrollkode=" + kontrollkode + "]";
    }

    @Override
    public BlaaResept skrivBlaaResept(Legemiddel legemiddel, Pasient pasient,
            int reit) {
        // Spesialist lege kan skrive ut narkotisk legemiddel for blaa resept

        BlaaResept ny = new BlaaResept(legemiddel, this, pasient, reit,
                legemiddel.hentPris());
        hentUtskrevneResepter().leggTil(ny);
        pasient.hentReseptListe().leggTil(ny);
        return ny;
    }
}