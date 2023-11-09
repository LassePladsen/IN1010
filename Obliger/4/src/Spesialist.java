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
        return "Spesialist[" + super.toString() 
        + ", kontrollkode=" + kontrollkode + "]";
    }

    @Override
    public BlaaResept skrivBlaaResept(Legemiddel legemiddel, Pasient pasient, int reit) {
            // Spesialist lege kan skrive ut narkotisk legemiddel
            
            BlaaResept ny = new BlaaResept(
            legemiddel,
            this,
            pasient,
            reit,
            legemiddel.hentPris()
            );
            hentUtskrevneResepter().leggTil(ny);
            return ny;
        }
}