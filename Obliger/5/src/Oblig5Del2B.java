import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.concurrent.CountDownLatch;

public class Oblig5Del2B {
    private static Monitor2 monitor = new Monitor2();

    public static void testOppgave3og4(String mappe) {
        // Finn filer som skal leses
        String metafil = mappe + "/metadata.csv";
        ArrayList<String> filer = new ArrayList<>();
        try {
            Scanner skanner = new Scanner(new File(metafil));
            // Loop over filer som skal leses, les og legg hashmap til
            // registeret
            while (skanner.hasNextLine()) {
                String fil = mappe + "/" + skanner.nextLine();
                filer.add(fil);
            }
        } catch (FileNotFoundException e) {
            System.out.println("Fant ikke metadatafilen: " + metafil);
            return;
        }

        CountDownLatch leseBarriere = new CountDownLatch(filer.size());
        for (String fil : filer) {
            new LeseTrad2(fil, monitor, leseBarriere).start();
        }

        // try {
        // leseBarriere.await();
        // } catch (InterruptedException e) {
        // }
        // System.out.println("Lesing ferdig");
        // System.out.println(monitor.stoerrelse());
        // System.exit(2);

        // Flett alle hashmap sammen til alt er ferdig
        int antallFletteTrader = 1;
        CountDownLatch fletteBarriere = new CountDownLatch(antallFletteTrader);
        Thread[] fletteTrader = new Thread[antallFletteTrader];
        for (int i = 0; i < antallFletteTrader; i++) {
            Thread trad = new FletteTrad(monitor, fletteBarriere);
            trad.start();
            fletteTrader[i] = trad;
        }

        // Vent til fletting er ferdig -> er ferdig naar lesing er
        // ferdig og monitor har kun én hashmap igjen

        // System.out.println("Ferdigtest");
        // System.exit(2);
        // System.out.println(monitor.stoerrelse());
        // while (leseBarriere.getCount() > 0 && monitor.stoerrelse() != 1) {
        // System.out.println(monitor.stoerrelse());
        // }

        try {
            leseBarriere.await();
        } catch (InterruptedException e) {
        } 
        while (monitor.stoerrelse() > 1) {}
        monitor.settFerdig();
        for (Thread fletteTrad : fletteTrader) {
            fletteTrad.interrupt();
        }
        try {
            fletteBarriere.await();
        } catch (InterruptedException e) {
            System.out.println("AVBRUTT:");
            e.printStackTrace();
        }

        System.out.println(
                "Fletting ferdig?, stoerrelse: " + monitor.stoerrelse());
        System.out.println("Barriere count: " + leseBarriere.getCount());
        for (Thread flettetrad : fletteTrader) {
            flettetrad.interrupt();
        }

        // Skriv ut subsekvensen med flest forekomster Subsekvens
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