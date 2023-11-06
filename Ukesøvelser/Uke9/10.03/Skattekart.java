import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

public class Skattekart {
    private boolean funnet;
    private final int rader, kolonner;
    private final char[][] symboler;
    
    private Skattekart(char[][] symboler, int rader, int kolonner) {
        this.symboler = symboler;
        this.rader = rader;
        this.kolonner = kolonner;
        funnet = false;
    }
    
    public static Skattekart skattejakt(File f) throws FileNotFoundException {
        Scanner skanner = new Scanner(f);
        int rader = skanner.nextInt();
        int kolonner = skanner.nextInt();
        char[][] symboler = new char[rader][kolonner];
        
        for (int rad = 0; rad<rader; rad++) {
            String linje = skanner.next();
            for (int kol = 0; kol<kolonner; kol++) {
                symboler[rad][kol] = linje.charAt(kol);
            }
        }
        skanner.close();
        return new Skattekart(symboler, rader, kolonner);
    }
    
    public void skrivUt() {
        for (int rad = 0; rad<rader; rad++) {
            for (int kol = 0; kol<kolonner; kol++) {
                char symbol = symboler[rad][kol];
                
                // Kun print skatt om den er funnet
                if (symbol == 'X' && funnet) {
                    System.err.print("X");
                } else {
                    System.err.print("O");
                }
            }
            System.err.println();
        }
        System.err.println();
    }
    
    public boolean sjekk(int rad, int kol) {
        if (rad >= rader || rad < 0 || kol >= kolonner || kol < 0) {
            throw new IllegalArgumentException("Ugyldig rad/kolonne.");
        }
        char symbol = symboler[rad][kol];
        funnet = symbol == 'X';
        return funnet;
    }
    
    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Usage: Skattekart {infile.kart}");
            System.exit(0);
        }
        try {
            Skattekart sk = skattejakt(new File(args[0]));
            Scanner skanner = new Scanner(System.in);
            sk.skrivUt();
            
            System.err.println("Finn skatten! Gjett koordinater paa formatet (kol rad)");
            while (!sk.funnet) {
                try {
                    if (sk.sjekk(skanner.nextInt(), skanner.nextInt())) {
                        System.err.println("Du fant skatten!");
                        sk.skrivUt();
                    }
                    else {
                        System.err.println("Ingen skatt der! Gjett igjen:");
                    }
                }
                catch (IllegalArgumentException e) {
                    System.err.println("Ugyldig rad/kolonne! Prøv igjen:");
                }
            }
        }
        catch (FileNotFoundException e) {
            System.err.println("File not found: " + args[0]);
        }
    }
}
