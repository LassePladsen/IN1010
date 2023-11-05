public class Rektangel {
    double lengde;
    double bredde;

    public Rektangel (double l, double b) {   // Konstruktør
      lengde = l;
      bredde = b;
    }

    public void oekLengde (int okning) {    // Oek lengden som angitt
        lengde = lengde + okning;
    }

    public void oekBredde (int okning) {    // Oek bredden som angitt
        bredde = bredde + okning;
    }

    public double areal () {     // Beregn mitt areal
        return lengde * bredde;
    }

    public double omkrets () {   // Beregn min omkrets
        return lengde * 2 + bredde * 2;
    }
}

class Main {
    public static void main(String[] args) {
        Rektangel rec = new Rektangel(2.0, 3.0);
        System.out.println(rec.lengde);
        rec.oekLengde(5);
        System.out.println(rec.lengde);
        System.out.println(rec.areal());
        System.out.println(rec.omkrets());

    }
}
