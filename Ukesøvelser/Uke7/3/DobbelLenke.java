public class DobbelLenke<T> {
    private Node start = null;    
    private Node slutt = null;
    
    public void settInn(T nyData) {
        if (erTom()) {
            start = new Node(nyData);
            slutt = start;
            return;
        }
        
        Node gammelSlutt = slutt;
        
        slutt = new Node(nyData);
        slutt.forrige = gammelSlutt;
        
        gammelSlutt.neste = slutt;
    }
    
    // public T fjernSiste() {
    //     if (erTom()) {
    //         return null;
    //     }
    //     Node gammelStart = start;
    //     start = gammelStart.neste;
    //     return gammelStart.data;
    // }
    
    public boolean erTom() {
        return (start == null);
    }
    
    private class Node {
        Node neste = null;
        Node forrige = null;
        T data;
        
        Node(T data) {
            this.data = data;
        }
    }
}
