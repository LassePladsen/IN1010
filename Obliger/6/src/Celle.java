public class Celle {
    private Celle[] naboer = new Celle[8];
    private int ledigIndeks = 0; // neste ledige indeks for array naboer
    public int antNaboer = 0;
    public int antLevendeNaboer = 0;
    public boolean levende = false; // 0 = dead, 1 = alive

    public void settDoed() {
        levende = false;
    }

    public void settLevende() {
        levende = true;
    }

    public boolean erLevende() {
        return levende;
    }

    public char hentStatusTegn() {
        if (levende) {
            return 'O';
        } else {
            return '.';
        }
    }

    // For gui bruk
    public String konverterStatusTegn() {
        if (levende) {
            return "O";
        } else {
            return "";
        }
    }

    public void leggTilNabo(Celle nabo) {
        antNaboer++;
        naboer[ledigIndeks] = nabo;
        ledigIndeks++;
        if (ledigIndeks > 8) {
            ledigIndeks = 0;
        }
    }

    public void tellLevendeNaboer() {
        int telling = 0;
        for (Celle nabo : naboer) {
            if (nabo == null) {
                continue;
            }
            if (nabo.levende) {
                telling++;
            }
        }

        antLevendeNaboer = telling;
    }

    public void oppdaterStatus() {
        // Levende celle regler
        if (levende) {
            // Underpopulasjon: drep celle
            if (antLevendeNaboer < 2) {
                settDoed();
            }

            // Overpopulasjon: drep celle
            else if (antLevendeNaboer > 3) {
                settDoed();
            }
        }

        // Død celle regler
        else {
            // Reproduksjon
            if (antLevendeNaboer == 3) {
                settLevende();
            }
        }
    }

    // public Celle() {
    // }

    // public void settAntLevendeNaboer(int val) {
    // antLevendeNaboer = val;
    // }
}
