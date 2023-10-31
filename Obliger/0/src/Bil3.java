public class Bil3 {
    private int nummer;

    public Bil3(int nr) {
        nummer = nr;
    }

    public void skriv() {
        System.out.println("Jeg er en bil med nummer " + nummer);
    }

    public int hentNummer() {
        return nummer;
    }
}