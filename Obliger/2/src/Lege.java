public class Lege {
    private final String navn;

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
}
