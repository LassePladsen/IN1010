class Rutenett {
    public int antRader;
    public int antKolonner;
    public Celle[][] rutene;

    public Rutenett(int antRader, int antKolonner) {
        this.antRader = antRader;
        this.antKolonner = antKolonner;
        rutene = new Celle[antRader][antKolonner];
    }

    public void lagCelle(int rad, int kol) {
        // Check row and column fit in Rutenett
        if (rad > antRader - 1) {
            throw new IllegalArgumentException("Rad " + rad + " passer ikke inn i rutenettet.");
        }if (kol > antKolonner - 1) {
            throw new IllegalArgumentException("Kolonne " + kol + " passer ikke inn i rutenettet.");
        }

        // Create new Celle with 1/3 chance to spawn alive
        Celle ny = new Celle();
        if (Math.random() <= 0.3333) {
            ny.settLevende();
        }
        rutene[rad][kol] = ny;
    }

    public void fyllMedTilfeldigeCeller() {
        for (int rad = 0; rad < antRader; rad++) {
            for (int kol = 0; kol < antKolonner; kol++) {
                lagCelle(rad, kol);
            }
        }
    }

    public Celle hentCelle(int rad, int kol) {
        // Check row and column fit in Rutenett
        if (rad > antRader - 1 || rad < 0 || kol > antKolonner - 1 || kol < 0) {
            return null;
        }
        return rutene[rad][kol];
    }

    public void tegnRutenett() {
        // // Empty lines to seperate prints
        // for (int i = 0; i < 10; i++) {
        //         System.out.println("");
        // }

        // Top edge line
        System.out.println("+" + "---+".repeat(antKolonner));
        for (int rad = 0; rad < antRader; rad++) {
            for (int kol = 0; kol < antKolonner; kol++) {
                // Left edge line
                System.out.print("|");
                
                // Print Celle sign
                Celle celle = hentCelle(rad, kol);
                System.out.print(" " + celle.hentStatusTegn() + " ");
            }
            // Right edge line
            System.out.println("|");

            // Row separator
            System.out.println("+" + "---+".repeat(antKolonner));
        }
    }

    public void settNaboer(int rad, int kol) {
        // Check row and column fit in Rutenett
        if (rad > antRader - 1) {
            throw new IllegalArgumentException("Rad " + rad + " passer ikke inn i rutenettet.");
        }if (kol > antKolonner - 1) {
            throw new IllegalArgumentException("Kolonne " + kol + " passer ikke inn i rutenettet.");
        }

        Celle celle = hentCelle(rad, kol);
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (i == 0 && j == 0) {
                    continue;
                } 

                Celle nabo = hentCelle(rad + i, kol + j);

                if (nabo == null) {
                    continue;
                }

                celle.leggTilNabo(nabo);
                }
            }
        }

    public void kobleAlleCeller() {
        for (int rad = 0; rad < antRader; rad++) {
            for (int kol = 0; kol < antKolonner; kol++) {
                settNaboer(rad, kol);
            }
        }
    }

    public int antallLevende() {
        int teller = 0;
        for (int rad = 0; rad < antRader; rad++) {
            for (int kol = 0; kol < antKolonner; kol++) {
                Celle celle = hentCelle(rad, kol);
                if (celle.levende) {
                    teller++;
                }
            }
        }
        return teller;
    }
}