public class TestOrdbeholder {
    public static void main(String[] args) {
        Ordbeholder o = new Ordbeholder();

        if (o.pop() != null) {
            System.out.println("1) Metode pop() returnerer ikke null på tom array.");
        }

        boolean res = o.settInn("Ord1");
        if (!res) {
            System.out.println("2) settInn() returnerte ikke true for første ord.");
        }

        boolean res2 = o.settInn("Ord1");
        if (res2) {
            System.out.println("3) settInn() returnerte ikke false for duplikat ord.");
        }

        o.settInn("ord2");
        o.settInn("ord3");
        o.settInn("ord4");
        if (o.antallOrd() != 4) {
            System.out.println("4) antallOrd() stemmer ikke med antall insatte ord.");
        }

        if (!o.pop().equals("ord4")) {
            System.out.println("5) pop() returnerer ikke siste insatte ord.");

        }if (o.antallOrd() != 3) {
            System.out.println("6) antallOrd() etter pop() stemmer ikke med antall insatte ord.");
        }

        
    }   
}
