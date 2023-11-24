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

    /** Setter inn et hashmap i indeks 0*/
    public void settInn(HashMap<String, Subsekvens> ny) {
        beholder.add(0, ny);
    }

    /** Setter inn et hashmap i gitt indeks */
    public void settInn(int indeks, HashMap<String, Subsekvens> ny) {
        beholder.add(indeks, ny);
    }

    /** Tar ut et hashmap ved indeks 0 */
    public HashMap<String, Subsekvens> taUt() {
        return beholder.remove(0);
    }

    /** Tar ut et hashmap ved gitt indeks */
    public HashMap<String, Subsekvens> taUt(int indeks) {
        return beholder.remove(indeks);
    }

    /** Gir totalt antall subsekvenser */
    public int stoerrelse() {
        return beholder.size();
    }

    /** Leser inn subsekvenser fra fil */
    public static HashMap<String, Subsekvens> lesFraFil(String filnavn) {
        int lengde = 3; // lengden på subsekvensene

        HashMap<String, Subsekvens> map = new HashMap<>();
        try {
            Scanner fil = new Scanner(new File(filnavn));
            while (fil.hasNextLine()) {
                String linje = fil.nextLine();
                if (linje.length() < lengde) {
                    return map;  // avslutt 
                }

                // Legg til string-subsekvens med lengde subsekvensLengde
                for (int i = 0; i < linje.length() - (lengde - 1); i++) {

                    String sekvens = "" + linje.charAt(i) + linje.charAt(i + 1)
                            + linje.charAt(i + 2);

                    // Bare legg til hvis sekvensen allerede ikke er lagt til
                    if (!map.containsKey(sekvens)) {
                        map.put(sekvens, new Subsekvens(sekvens, 1));
                    }
                }

            }
        } catch (FileNotFoundException e) {
            System.out.println("Finner ikke filen " + filnavn);
        }
        return map;
    }

    /** Slaar sammen to HashMaps av subsekvenser */
    public static HashMap<String, Subsekvens> flett(
            HashMap<String, Subsekvens> map1,
            HashMap<String, Subsekvens> map2) {

        // Lag kopi av forste hashmap
        HashMap<String, Subsekvens> nyMap = new HashMap<>(map1);

        // Sett inn fra andre hashmap til foerste hashmap
        for (String sekvens : map2.keySet()) {
            // Duplikat: oek antall
            if (nyMap.containsKey(sekvens)) {
                nyMap.get(sekvens).oekAntall();
            } else { // Ikke duplikat: sett inn
                nyMap.put(sekvens, new Subsekvens(sekvens, 1));
            }
        }

        return nyMap;
    }
}