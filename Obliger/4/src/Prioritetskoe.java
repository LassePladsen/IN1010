public class Prioritetskoe<E extends Comparable<E>> extends Lenkeliste<E> {
    @Override 
    public void leggTil(E nyData) {
        if (stoerrelse == 0) {
            start = new Node(nyData);
            slutt = start;
        }
        
        else if (stoerrelse == 1) {
            
            // nyData mindre enn eller lik eksisterende data -> sett som ny start
            if (nyData.compareTo(start.data) <= 0) {
                Node gammelStart = start;
                start = new Node(nyData);
                start.neste = gammelStart;
                slutt = gammelStart;
            } 
            // nyData stoerre enn eksisterende data -> sett inn som slutt
            else if (nyData.compareTo(start.data) > 0) {
                start.neste = new Node(nyData);
                slutt = start.neste;
            }
        }
        
        else {
            Node node = start;
            Node forrigeNode = null;
            boolean funnet = false;
            while (node != null) {
                // nyData mindre enn eller lik node -> sett inn foer node
                if (nyData.compareTo(node.data) <= 0) {

                    // nyData mindre enn start -> ny start
                    if (forrigeNode == null) {
                        start = new Node(nyData);
                        start.neste = node;
                    }

                    else {
                        forrigeNode.neste = new Node(nyData);
                        forrigeNode.neste.neste = node;
                    }
                    funnet = true;
                    break;
                } 
                
                forrigeNode = node;
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
