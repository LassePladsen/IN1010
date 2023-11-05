public class Lenkeliste<E> implements Liste<E> {
    protected Node start = null;
    protected Node slutt = null;
    protected int stoerrelse = 0;
    
    @Override
    public int stoerrelse() {
        return stoerrelse;
    }
    
    @Override
    public void leggTil(E nyData) {
        if (stoerrelse == 0) {
            start = new Node(nyData);
            slutt = start;
        }

        else if (stoerrelse == 1) {
            slutt = new Node(nyData);
            start.neste = slutt;
        }
        
        else {
            Node gammelSlutt = slutt;
            slutt = new Node(nyData);
            gammelSlutt.neste = slutt;
        }
        stoerrelse++;
    }
    
    @Override 
    public E hent() {
        if (stoerrelse == 0) {
            throw new UgyldigListeindeks(0);
        }
        return start.data;
    }
    
    @Override
    public E fjern() {
        if (stoerrelse == 0) {
            throw new UgyldigListeindeks(0);
        }
        Node gammelStart = start;
        start = gammelStart.neste;
        stoerrelse--;
        return gammelStart.data; 
    }
    
    @Override
    public String toString() {
        if (stoerrelse == 0) {
            return "[]";
        }
        String ut = "[" + start.data;
        Node node = start;
        while (node.neste != null) {
            node = node.neste;
            ut += ", " + node.data; 
        }
        
        return ut + "]";
        
    }
    
    protected class Node {
        protected Node neste = null;
        protected E data;
        
        protected Node(E data) {
            this.data = data;
        }
    }
}
