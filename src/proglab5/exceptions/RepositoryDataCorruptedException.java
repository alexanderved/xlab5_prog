package proglab5.exceptions;

public class RepositoryDataCorruptedException extends Exception {
    public RepositoryDataCorruptedException() {
        super("Данные в репозитории повреждены и не могут быть загружены");
    }

    public RepositoryDataCorruptedException(String reason) {
        super("Данные в репозитории повреждены и не могут быть загружены: " + reason);
    }
}
