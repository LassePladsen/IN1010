public class Lege implements Comparable<Lege> {
    private final String navn;
    private final IndeksertListe<Resept> utskrevneResepter = new IndeksertListe<>();
    
    public Lege(String navn) {
        this.navn = navn;
    }
    
    public String hentNavn() {
        return navn;
    }
    
    @Override
    public String toString() {
        return "Lege[" + "navn=" + navn + "]";
    }
    
    @Override
    public int compareTo(Lege annen) {
        return navn.compareTo(annen.hentNavn());
    }
    
    public Liste<Resept> hentUtskrevneResepter() {
        return utskrevneResepter;
    }
    
    public HvitResept skrivHvitResept(Legemiddel legemiddel,Pasient pasient, int reit
                                    ) throws UlovligUtskrift {
            // Vanlig lege kan ikke skrive ut narkotisk legemiddel
            narkotiskSjekk(legemiddel);
            
            HvitResept ny = new HvitResept(
            legemiddel,
            this,
            pasient,
            reit,
            legemiddel.hentPris()
            );
            utskrevneResepter.leggTil(ny);
            return ny;
        }
    
    public MilResept skrivMilResept(Legemiddel legemiddel, Pasient pasient)
                                    throws UlovligUtskrift {
            // Vanlig lege kan ikke skrive ut narkotisk legemiddel
            narkotiskSjekk(legemiddel);
            
            MilResept ny = new MilResept(
            legemiddel,
            this,
            pasient,
            legemiddel.hentPris()
            );
            utskrevneResepter.leggTil(ny);
            return ny;
        }
    
    public PResept skrivPResept(Legemiddel legemiddel, Pasient pasient, int reit)
                                throws UlovligUtskrift {
            // Vanlig lege kan ikke skrive ut narkotisk legemiddel
            narkotiskSjekk(legemiddel);
            
            PResept ny = new PResept(
            legemiddel,
            this,
            pasient,
            reit,
            legemiddel.hentPris()
            );
            utskrevneResepter.leggTil(ny);
            return ny;
        }
    
    public BlaaResept skrivBlaaResept(Legemiddel legemiddel, Pasient pasient, int reit)
                                throws UlovligUtskrift {
            // Vanlig lege kan ikke skrive ut narkotisk legemiddel
            narkotiskSjekk(legemiddel);
            
            BlaaResept ny = new BlaaResept(
            legemiddel,
            this,
            pasient,
            reit,
            legemiddel.hentPris()
            );
            utskrevneResepter.leggTil(ny);
            return ny;
        }

    private void narkotiskSjekk(Legemiddel legemiddel) throws UlovligUtskrift {
        if (legemiddel instanceof Narkotisk) {
                throw (new UlovligUtskrift(this, legemiddel));
            }
    }
}
