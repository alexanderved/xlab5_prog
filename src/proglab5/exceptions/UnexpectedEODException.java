package proglab5.exceptions;

public class UnexpectedEODException extends Exception {
    public UnexpectedEODException() {
        super("Неожиданное окончание потока данных");
    }
}
