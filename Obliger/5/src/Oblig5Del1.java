import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Oblig5Del1 {
    private static SubsekvensRegister register = new SubsekvensRegister();

    public static void testOppgave3og4(String mappe) {
        // Finn filer som skal leses
        String metafil = mappe + "/metadata.csv";
        try {
            Scanner skanner = new Scanner(new File(metafil));
            // Loop over filer som skal leses, les og legg hashmap til
            // registeret
            while (skanner.hasNextLine()) {
                String fil = mappe + "/" + skanner.nextLine();
                register.settInn(SubsekvensRegister.lesFraFil(fil));
            }
        } catch (FileNotFoundException e) {
            System.out.println("Fant ikke metadatafilen: " + metafil);
            return;
        }

        // Flett alle hashmap sammen
        while (true) {
            if (register.stoerrelse() == 1) {
                break;
            }
            HashMap<String, Subsekvens> test1 = register.taUt();
            // test1.values();
            HashMap<String, Subsekvens> test2 = register.taUt();
            // test2.values();
            // System.out.println(test1);
            // System.out.println();
            // System.out.println(test2);
            // System.out.println();
            HashMap<String, Subsekvens> testflett = SubsekvensRegister
                    .flett(test1, test2);
            // testflett.values();
            // System.out.println(testflett);
            // System.out.println("\n");
            register.settInn(testflett);
        }

        // Skriv ut subsekvensen med flest forekomster
        Subsekvens flestSekvens = null;
        int flest = 0;
        HashMap<String, Subsekvens> map = register.taUt();
        for (Subsekvens sekvens : map.values()) {
            // System.out.print(sekvens);
            if (sekvens.hentAntall() > flest) {
                flestSekvens = sekvens;
                flest = sekvens.hentAntall();
            }
        }
        System.out.println("Mappen " + mappe + ": "
                + "Subsekvensen med flest forekomster er " + flestSekvens);

    }

    private static void testFlett() {
        HashMap<String, Subsekvens> map1 = new HashMap<>();
        map1.put("ABC", new Subsekvens("ABC", 1));
        map1.put("BCD", new Subsekvens("BCD", 1));
        map1.put("CDE", new Subsekvens("CDE", 1));
        HashMap<String, Subsekvens> map2 = new HashMap<>();
        map2.put("ABC", new Subsekvens("ABC", 1));
        map2.put("EGE", new Subsekvens("EGE", 1));
        map2.put("CDE", new Subsekvens("CDE", 1));

        HashMap<String, Subsekvens> actual = SubsekvensRegister.flett(map1,
                map2);
        if (!actual.get("ABC").toString().equals("(ABC,2)")) {
            System.out.println("Feil i ABC, fikk: " + actual.get("ABC"));
        }
        if (!actual.get("BCD").toString().equals("(BCD,1)")) {
            System.out.println("Feil i BCD, fikk: " + actual.get("BCD"));
        }
        if (!actual.get("CDE").toString().equals("(CDE,2)")) {
            System.out.println("Feil i CDE, fikk:" + actual.get("CDE"));
        }
        if (!actual.get("EGE").toString().equals("(EGE,1)")) {
            System.out.print("Feil i EGE, fikk: " + actual.get("EGE"));
        }
    }

    public static void main(String[] args) {
        testFlett();
        testOppgave3og4("../TestDataLike");
        testOppgave3og4("../TestDataLitenLike");
    }
}