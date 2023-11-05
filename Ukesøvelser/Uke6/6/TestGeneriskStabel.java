public class TestGeneriskStabel {
    public static void main(String[] args) {
        GeneriskStabel<String> stabel = new GeneriskStabel<String>();
        stabel.leggPaa("foobar");
        stabel.leggPaa("bazar");
        stabel.leggPaa("baz");
        stabel.leggPaa("bar");
        stabel.leggPaa("Foo");
        String resultat = "";
        while (!stabel.erTom())
            resultat += stabel.taAv() + " ";
        System.out.printf("Resultatet er: '%s'\n", resultat);
    } 
}