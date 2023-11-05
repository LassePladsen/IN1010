public class Kvadrat {
    public final double lengde;

    public Kvadrat(double lengde) {
        this.lengde = lengde;
    }

    public double areal() {
        return lengde * lengde;
    }

    public double omkrets() {
        return lengde * 4;
    }
}
