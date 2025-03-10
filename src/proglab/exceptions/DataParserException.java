package proglab.exceptions;

/**
 * Исключение, выбрасываемое в том случае, когда не удалось распарсить строку и
 * получить данные.
 */
public class DataParserException extends Exception {
    /**
     * @param e Ошибка или исключение, из-за возниконвения которого не удалось распарсить
     *          данные
     */
    public DataParserException(Throwable e) {
        super(e);
    }
}