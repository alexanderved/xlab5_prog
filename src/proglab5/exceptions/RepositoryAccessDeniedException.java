package proglab5.exceptions;

public class RepositoryAccessDeniedException extends Exception {
    public RepositoryAccessDeniedException(String message) {
        super(message);
    }

    @Override
    public String getMessage() {
        return "Доступ к репозиторию запрещен: " + super.getMessage();
    }
}