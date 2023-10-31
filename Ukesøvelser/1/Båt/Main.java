class Baat {

    private static int antProd = 0;
    private int prodnr;
    private String merke;

    public Baat(String mrk) {
        prodnr = antProd;
        antProd++;
        merke = mrk;
    }

    public String hentInfo() {
        return "Produksjonsnummer: " + prodnr + ", merke: " + merke;
    }
}

class Baathus {

    private Baat[] baathus;

    public Baathus(int antallPlasser) {
        baathus = new Baat[antallPlasser];
    }

    public void settInn(Baat baat) {
        for (int i = 0; i < baathus.length; i++) {

            // Found room in array
            if (baathus[i] == null) {
                baathus[i] = baat;
                return;
            }

        }

        // No more room in array
        System.out.println("Baathus " + baathus + " med lengde " + baathus.length + " har ikke mer plass");
    }

    public void skrivBaater() {
        for (Baat baat : baathus) {
            if (baat != null) {
                System.out.println(baat.hentInfo());
            }
        }
    }
}

class TestBaathus {
    public static void main(String[] args) {
        Baathus baathus = new Baathus(3);

        // Try setting in five new boats
        for (int i = 0; i < 5; i++) {
            baathus.settInn(new Baat(Integer.toString(i)));
        }

        // Write boats infos
        baathus.skrivBaater();
    }
}