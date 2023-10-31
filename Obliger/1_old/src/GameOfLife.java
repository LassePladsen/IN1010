package src;
public class GameOfLife {
    public static void main(String[] args) {
        if (args.length != 3) {
            System.out.println("Usage: GameOfLife <rows> <columns> <generation>");
            System.exit(1);
        }

        int antallRader = Integer.parseInt(args[0]);
        int antallKolonner = Integer.parseInt(args[1]);
        int antallGenerasjoner = Integer.parseInt(args[2]);


        Verden verden = new Verden(antallRader, antallKolonner);
        System.out.print("\n\n\n\n");
        verden.tegn();
        for (int i = 0; i < antallGenerasjoner; i++) {
            verden.oppdatering();
            System.out.print("\n\n\n\n");
            verden.tegn();
        }
    }
}