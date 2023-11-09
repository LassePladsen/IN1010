public class IndeksertListe<E> extends Lenkeliste<E> {
    public void leggTil(int pos, E nyData) {
        if (pos < 0 || pos > stoerrelse) {
            throw new UgyldigListeindeks(0);
        } 
        
        if (pos == 0) {
            Node gammelStart = start;
            start = new Node(nyData);
            start.neste = gammelStart;
        } 
        else {
            Node node = hentNode(pos-1);
            
            Node temp = null;
            if (node.neste != null) {
                temp = node.neste;
            }
            node.neste = new Node(nyData);
            node.neste.neste = temp;
        }
        stoerrelse++;
    }
    public void sett(int pos, E nyData) { 
        if (pos < 0 || pos >= stoerrelse) {
            throw new UgyldigListeindeks(0);
        } 
        
        if (pos == 0) {
            Node gammelStartNeste = start.neste;
            start = new Node(nyData);
            start.neste = gammelStartNeste;
        } 

        else {
            Node node = hentNode(pos-1);
            
            Node temp = null;
            if (node.neste != null) {
                if (node.neste.neste != null) {
                    temp = node.neste.neste;
                }
            }
            node.neste = new Node(nyData);
            node.neste.neste = temp;
        }
    }
    
    public E hent(int pos) { 
        return hentNode(pos).data;
    }
    
    public E fjern(int pos) {
        if (pos < 0 || pos >= stoerrelse) {
            throw new UgyldigListeindeks(0);
        } 
        Node node = hentNode(pos-1);
        E temp = node.neste.data;
        
        node.neste = node.neste.neste;
        stoerrelse--;
        return temp;
    }
    
    protected Node hentNode(int pos) {
        if (pos < 0 || pos >= stoerrelse) {
            throw new UgyldigListeindeks(0);
        } 
        Node node = start;
        int i = 0;
        while (i < pos) {
            node = node.neste;
            i++;
        }
        return node;
    }

}