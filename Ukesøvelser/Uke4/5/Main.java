public class Main {
    public static void main(String[] args) {
        Personbil a = new Personbil("AP2317", "Audi a6", "Steinar", 4, 40000);
        a.skrivInfo();
        System.out.println(a.hentAntallPlasser());
    }
}
