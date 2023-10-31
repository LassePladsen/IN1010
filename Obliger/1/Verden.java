class Verden {
    private Rutenett rutenett;
    public int genNr = 0;

    public Verden(int antallRader, int antallKolonner) {
        rutenett = new Rutenett(antallRader, antallKolonner);
        rutenett.fyllMedTilfeldigeCeller();
        rutenett.kobleAlleCeller();
    }

    public void tegn() {
        System.out.println("Generasjon nr " + genNr + ":");
        rutenett.tegnRutenett();
        System.out.println("Det er " + rutenett.antallLevende() + " levende celler.");
    }

    public void oppdatering(){
        // Update all cell's living neighbours
        for (int rad = 0; rad < rutenett.antRader; rad++) {
            for (int kol = 0; kol < rutenett.antKolonner; kol++) {
                Celle celle = rutenett.hentCelle(rad, kol);
                celle.tellLevendeNaboer();
            }

        }

        // Update all cell's status
        for (int rad = 0; rad < rutenett.antRader; rad++) {
            for (int kol = 0; kol < rutenett.antKolonner; kol++) {
                Celle celle = rutenett.hentCelle(rad, kol);
                celle.oppdaterStatus();
            }
        }
        
        genNr++;
    }

    public static void main(String[] args) {
        Verden verden = new Verden(5, 5);
        verden.tegn();
        verden.oppdatering();
        verden.tegn();
        verden.oppdatering();
        verden.tegn();
    }
}