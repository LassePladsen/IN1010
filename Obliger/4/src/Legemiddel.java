abstract public class Legemiddel {
    private final String navn;
    private final double virkestoff;
    private final int id;
    private int pris;
    private static int ledigId = 0;
    
    public Legemiddel(String navn, int pris, double virkestoff) {
        this.navn = navn;
        this.pris = pris;
        this.virkestoff = virkestoff;
        id = ledigId;
        ledigId ++;
    }
    
    public int hentPris() {
        return pris; 
    }
    
    public int hentId() {
        return id; 
    }
    
    public String hentNavn() {
        return navn; 
    }
    
    public double hentVirkestoff() {
        return virkestoff; 
    }
    
    public void settPris(int pris) {
        this.pris = pris;
    }
    
    @Override
    public String toString() {
        return "Navn=" + navn + ", pris=" + pris + ", virkestoff="
         + virkestoff + ", id=" + id;
    }
}