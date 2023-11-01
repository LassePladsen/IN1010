public class DuplikatException extends Exception{

    public DuplikatException(String arg) {
        super("Kunne ikke legge til " + arg + " fordi det er en duplikat.");
    }
}
