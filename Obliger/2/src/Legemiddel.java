abstract public class Legemiddel {
    public final String navn;
    public final double virkestoff;
    public final int id;
    
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
    
    public void settPris(int pris) {
        this.pris = pris;
    }
    
    @Override
    public String toString() {
        return "Navn=" + navn + ", virkestoff=" + virkestoff + ", id=" + id;
    }
}