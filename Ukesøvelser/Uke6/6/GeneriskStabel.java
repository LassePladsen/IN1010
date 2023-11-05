public class GeneriskStabel<T> {
    private Node start = null;

    public void leggPaa(T ny) {
        if (erTom()) {
            start = new Node(ny);
            return;
        }
        Node gammelStart = start;
        start = new Node(ny);
        start.neste = gammelStart;
    }

    public T taAv() {
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
        private T data;

        private Node(T data) {
            this.data = data;
        }

    }
}
