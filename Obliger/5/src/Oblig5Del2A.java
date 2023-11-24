import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Oblig5Del2A {
    private static Monitor1 monitor = new Monitor1();

    public static void testOppgave3og4(String mappe) {
        // Finn filer som skal leses
        String metafil = mappe + "/metadata.csv";
        ArrayList<LeseTrad> trader = new ArrayList<>();
        try {
            Scanner skanner = new Scanner(new File(metafil));
            // Loop over filer som skal leses, les og legg hashmap til
            // registeret
            while (skanner.hasNextLine()) {
                String fil = mappe + "/" + skanner.nextLine();
                LeseTrad trad = new LeseTrad(fil, monitor);
                trad.start();
                trader.add(trad);
            }
        } catch (FileNotFoundException e) {
            System.out.println("Fant ikke metadatafilen: " + metafil);
            return;
        }

        // Vent paa alle traader som leser filene
        for (LeseTrad trad : trader) {
            try {
                trad.join();
            } catch (InterruptedException e) {
            }
        }

        // Flett alle hashmap sammen
        while (true) {
            if (monitor.stoerrelse() == 1) {
                break;
            }
            HashMap<String, Subsekvens> test1 = monitor.taUt();
            HashMap<String, Subsekvens> test2 = monitor.taUt();
            HashMap<String, Subsekvens> testflett = SubsekvensRegister
                    .flett(test1, test2);
            monitor.settInn(testflett);
        }

        // Skriv ut subsekvensen med flest forekomster
        Subsekvens flestSekvens = null;
        int flest = 0;
        HashMap<String, Subsekvens> map = monitor.taUt();
        for (Subsekvens sekvens : map.values()) {
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