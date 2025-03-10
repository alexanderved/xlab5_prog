package proglab.exceptions;

/**
 * Исключение, выбрасываемое в том случае, когда в процессе ввода возникла
 * ошибка.
 */
public class InputFailedException extends Exception {
    /**
     * @param message Сообщение о возникшей ошибке
     */
    public InputFailedException(String message) {
        super(message);
    }

    /**
     * @param e Ошибка или исключение, из-за которого возникла ошибка ввода
     */
    public InputFailedException(Throwable e) {
        super(e);
    }
}
