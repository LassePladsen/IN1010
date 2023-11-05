public class Katt implements Comparable<Katt> {
    public final String navn;
    public final int alder;
    
    public Katt(String navn, int alder) {
        this.navn = navn;
        this.alder = alder;
    }

    @Override
    public String toString() {
        return "Katt[navn=" + navn + ", alder=" + alder + "]";
    }

    @Override
    public int compareTo(Katt annen) {
        if (alder > annen.alder) return 1;
        if (alder < annen.alder) return -1;
        return 0;
    }

    public static void main(String[] args) {
        Katt[] katter = new Katt[5];
        katter[0] = new Katt("1", 5);
        katter[1] = new Katt("2", 123);
        katter[2] = new Katt("3", 25);
        katter[3] = new Katt("4", 1435);
        katter[4] = new Katt("5", 0);

        Katt maks = katter[0];
        for (int i=0; i<katter.length; i++) {
            if (katter[i].alder > maks.alder) maks = katter[i];
        }
        System.out.println(maks);
    }
}
