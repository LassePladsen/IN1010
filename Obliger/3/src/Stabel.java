public class Stabel<E> extends Lenkeliste<E> {
    @Override
    public void leggTil(E nyData) {
        if (stoerrelse == 0) {
            slutt = start;
            start = new Node(nyData);
        }
        else if (stoerrelse == 1) {
            slutt = start;
            start = new Node(nyData);
            start.neste = slutt;
        }
        else {
            Node gammelStart = start;
            start = new Node(nyData);
            start.neste = gammelStart;
        }
        stoerrelse++;
    }
}

