package proglab5.exceptions;

/**
 * Исключение, выбрасываемое в том случае, когда не удалось запросить ввод из
 * предтавления.
 */
public class InputDeniedException extends Exception {
    /**
     * @param message Сообщение о возникшей ошибке
     */
    public InputDeniedException(String message) {
        super(message);
    }

    /**
     * @param e Ошибка или исключение, из-за возниконвения которого не удалось распарсить
     *          данные
     */
    public InputDeniedException(Throwable e) {
        super(e);
    }

    @Override
    public String getMessage() {
        return "Запрос на ввод к представлению отклонен: " + super.getMessage();
    }
}