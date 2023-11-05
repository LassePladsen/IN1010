public class KvadratStabel {
    private Node start = null;

    public void leggPaa(Kvadrat ny) {
        if (erTom()) {
            start = new Node(ny);
            return;
        }
        Node gammelStart = start;
        start = new Node(ny);
        start.neste = gammelStart;
    }

    public Kvadrat taAv() {
        if (erTom()) {
            return null;
        }
        Node gammelStart = start;
        start = gammelStart.neste;
        return gammelStart.data;
    }
    
    public boolean erTom() {
        return (start == null);
    }

    private class Node {
        private Node neste = null;
        private Kvadrat data;

        private Node(Kvadrat data) {
            this.data = data;
        }

    }
}
