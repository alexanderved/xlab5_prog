package proglab.exceptions;

/**
 * Исключение, выбрасываемое в том случае, когда не удалось получить ддоступ к
 * репозиторию.
 */
public class RepositoryAccessDeniedException extends Exception {
    /**
     * @param message Сообщение о возникшей ошибке
     */
    public RepositoryAccessDeniedException(String message) {
        super(message);
    }

    /**
     * @param e Ошибка или исключение, из-за которого не удалось получить доступ к репозиторию
     */
    public RepositoryAccessDeniedException(Throwable e) {
        super(e);
    }

    @Override
    public String getMessage() {
        return "Доступ к репозиторию запрещен: " + super.getMessage();
    }
}