public class Person {
    private Bil3 bil;

    public Person(Bil3 bil) {
        this.bil = bil;
    }

    public void skriv() {
        System.out.println("Person med bil nummer " + bil.hentNummer());
    }
}