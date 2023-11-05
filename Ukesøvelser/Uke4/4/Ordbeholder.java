import java.util.ArrayList;

public class Ordbeholder {

    public ArrayList<String> ordbeholder = new ArrayList<>();

    public boolean settInn(String ord) {
        if (ordbeholder.contains(ord))
            return false;

        ordbeholder.add(ord);
        return true;
    }

    public int antallOrd() {
        return ordbeholder.size();
    }

    public String pop() {
        if (antallOrd() == 0)
            return null;

        return ordbeholder.remove(antallOrd() -1);
    }    

}