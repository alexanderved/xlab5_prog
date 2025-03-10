package proglab.exceptions;

/**
 * Исключение, выбрасываемое в том случае, когда данные не могут быть прочитаны из репозитория.
 */
public class RepositoryDataCorruptedException extends Exception {
    /**
     * Пустой конструктор.
     */
    public RepositoryDataCorruptedException() {
        super("Данные в репозитории повреждены и не могут быть загружены");
    }

    /**
     * Конструктор исключения, сообщающего о причине ошибки.
     * 
     * @param reason Причина ошибки
     */
    public RepositoryDataCorruptedException(String reason) {
        super("Данные в репозитории повреждены и не могут быть загружены: " + reason);
    }

    /**
     * Конструктор исключения, сообщающего об исключении, ставшем причиной ошибки.
     * 
     * @param e Ошибка или исключение, из-за которого не прочитать данные из репозитория
     */
    public RepositoryDataCorruptedException(Throwable e) {
        super("Данные в репозитории повреждены и не могут быть загружены: " + e.getMessage(), e);
    }
}
