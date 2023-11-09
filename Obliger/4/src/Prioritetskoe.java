public class Prioritetskoe<E extends Comparable<E>> extends Lenkeliste<E> {
    @Override 
    public void leggTil(E nyData) {
        if (stoerrelse == 0) {
            start = new Node(nyData);
            slutt = start;
        }
        
        else if (stoerrelse == 1) {
            
            // nyData mindre enn eller lik eksisterende data -> sett som ny start
            if (start.data.compareTo(nyData) >= 0) {
                Node gammelStart = start;
                start = new Node(nyData);
                start.neste = gammelStart;
                slutt = gammelStart;
            } 
            // nyData stoerre enn eksisterende data -> sett inn som slutt
            else if (start.data.compareTo(nyData) < 0) {
                start.neste = new Node(nyData);
                slutt = start.neste;
            }
        }
        
        else {
            Node node = start;
            Node sisteNode = null;
            boolean funnet = false;
            while (node.neste != null) {

                // nyData mindre enn node -> sett inn foer node
                if (node.data.compareTo(nyData) > 0) {
                    Node gammelNode = node;

                    // nyData mindre enn start -> ny start
                    if (sisteNode == null) {
                        start = new Node(nyData);
                        start.neste = gammelNode;
                    }

                    else {
                        sisteNode.neste = new Node(nyData);
                        sisteNode.neste.neste = gammelNode;
                        if (gammelNode.neste == null) slutt = gammelNode;
                    }
                    funnet = true;
                    break;
                } 
                
                sisteNode = node;
                node = node.neste;
            }
            
            // nyData Stoerre enn alle elementer -> sett inn som slutt
            if (!funnet) {
                node.neste = new Node(nyData);
                slutt = node.neste;
            }
        }
        
        stoerrelse++;
    }
}
