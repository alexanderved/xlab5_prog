package proglab5.exceptions;

public class InputFailedException extends Exception {
    public InputFailedException(String msg) {
        super(msg);
    }

    public InputFailedException(Exception e) {
        super(e);
    }
}
