package proglab5.exceptions;

/**
 * Исключение, выбрасываемое в том случае, когда данные, получаемые от
 * пользователя, перестали поступать раньше окончания их чтения.
 */
public class UnexpectedEODException extends Exception {
    /**
     * Пустой конструктор.
     */
    public UnexpectedEODException() {
        super("Неожиданное окончание потока данных");
    }
}
