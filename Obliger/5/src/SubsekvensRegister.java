import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

/**
 * 
 */
public class SubsekvensRegister {
    private ArrayList<HashMap<String, Subsekvens>> beholder = new ArrayList<>();

    /** Setter inn et hashmap */
    public void settInn(HashMap<String, Subsekvens> ny) {
        beholder.add(ny);
    }

    /** Tar ut et hashmap ved indeks 0*/
    public HashMap<String, Subsekvens> taUt() {
        return beholder.remove(0);
    }

    /** Tar ut et hashmap ved gitt indeks*/
    public HashMap<String, Subsekvens> taUt(int indeks) {
        return beholder.remove(indeks);
    }

    /** Gir totalt antall subsekvenser */
    public int stoerrelse() {
        return beholder.size();
    }

    /** Leser inn subsekvenser fra fil */
    public static HashMap<String, Subsekvens> lesFraFil(String filnavn) {
        HashMap<String, Subsekvens> sekvenser = new HashMap<>();
        int subsekvensLengde = 3; // lengden på subsekvensene
        try {
            Scanner fil = new Scanner(new File(filnavn));
            while (fil.hasNextLine()) {
                String linje = fil.nextLine();
                if (linje.length() < subsekvensLengde) {
                    return sekvenser;
                }

                // Legg til string-subsekvens med lengde subsekvensLengde
                for (int i = 0; i < linje.length()
                        - (subsekvensLengde - 1); i++) {

                    String sekvens = "" + linje.charAt(i) + linje.charAt(i + 1)
                            + linje.charAt(i + 2);

                    // Bare legg til hvis sekvensen allerede ikke er lagt til
                    if (!sekvenser.containsKey(sekvens)) {
                        sekvenser.put(sekvens, new Subsekvens(sekvens, 1));
                    }
                }

            }
        } catch (FileNotFoundException e) {
            System.out.println("Finner ikke filen " + filnavn);
        }
        return sekvenser;
    }

    /** Slaar sammen to HashMaps av subsekvenser */
    public static HashMap<String, Subsekvens> flett(
            HashMap<String, Subsekvens> sekvenser1,
            HashMap<String, Subsekvens> sekvenser2) {

        // Lag kopi av forste hashmap
        HashMap<String, Subsekvens> ny = new HashMap<>(sekvenser1);

        // Sett inn fra andre hashmap til foerste hashmap
        for (String sekvens : sekvenser2.keySet()) {
            // Duplikat: oek antall
            if (ny.containsKey(sekvens)) {
                ny.get(sekvens).oekAntall();
            } else { // Ikke duplikat: sett inn
                ny.put(sekvens, new Subsekvens(sekvens, 1));
            }
        }

        return ny;
    }
}