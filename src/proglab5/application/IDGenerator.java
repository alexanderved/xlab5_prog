package proglab5.application;

/**
 * Генератор уникальных ID для организаций.
 */
public abstract class IDGenerator {
    /**
     * Генерирует ID организаций.
     * @return новый ID
     */
    public abstract int generateID();

    /**
     * Отмечает данный ID, как использованный.
     * @param id ID, который уже используется организацией
     */
    public abstract void reserve(int id);
}