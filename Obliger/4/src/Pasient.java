public class Pasient {
    private final int id;
    private final String navn;
    private final String fodsNr;
    private Liste<Resept> reseptListe = new IndeksertListe<>();
    private static int ledigId = 0;
    
    public Pasient(String navn, String fodsNr) {
        this.navn = navn;
        this.fodsNr = fodsNr;
        id = ledigId;
        ledigId++;
    }

    public void leggTilResept(Resept resept) {
        reseptListe.leggTil(resept);
    }

    public String hentFodsNr() {
        return fodsNr; 
    }
    
    public int hentId() {
        return id; 
    }
    
    public String hentNavn() {
        return navn; 
    }

    public Liste<Resept> hentReseptListe() {
        return reseptListe;
    }

    @Override
    public String toString() {
        return "Pasient[" + "navn=" + navn + ", foedselsnr=" + fodsNr
                 + ", id=" + id + "]";
    }
}
