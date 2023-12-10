import java.util.Scanner;

public class GameOfLife {

    private static void feilbruk() {
        System.out.println("Usage: GameOfLife <rows> <columns> <gui/cmd>");
        System.exit(1);
    }

    private static void startGUI(int rader, int kolonner) {
        new GUI(new Verden(rader, kolonner), rader, kolonner).startProgram();
    }

    private static void startTerminal(int rader, int kolonner) {
        System.out.print("Velg antall generasjoner > ");
        Scanner in = new Scanner(System.in);
        int antallGenerasjoner = in.nextInt();

        Verden verden = new Verden(rader, kolonner);
        System.out.print("\n\n\n\n");
        verden.tegn();
        for (int i = 0; i < antallGenerasjoner; i++) {
            verden.oppdatering();
            System.out.print("\n\n\n\n");
            verden.tegn();
        }
    }

    public static void main(String[] args) {
        System.out.println(args.length);
        if (args.length == 0) {
            // Standard som 8x12 gui
            int rader = 8;
            int kolonner = 12;
            startGUI(rader, kolonner);
        } else if (args.length == 2) {
            // Standard gui med funnet rader og kolonner 
            int rader = Integer.parseInt(args[0]);
            int kolonner = Integer.parseInt(args[1]);
            startGUI(rader, kolonner);
        } else if (args.length == 3) {
            int rader = Integer.parseInt(args[0]);
            int kolonner = Integer.parseInt(args[1]);
            String metode = args[2];

            if (metode.toLowerCase().equals("gui")) { // oblig 6 gui
                startGUI(rader, kolonner);

            } else if (metode.toLowerCase().equals("cmd")) { // oblig 1 terminal
                startTerminal(rader, kolonner);
            } else {
                feilbruk();
            }
        } else {
            feilbruk();
        }
    }
}