public class IkkeMerPlassException extends Exception {
    
    public IkkeMerPlassException(String arg) {
        super("Det er ikke mer plass for " + arg);
    }
}
