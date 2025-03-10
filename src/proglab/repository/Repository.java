package proglab.repository;

import java.util.HashSet;

import proglab.domain.Organization;
import proglab.exceptions.RepositoryAccessDeniedException;
import proglab.exceptions.RepositoryDataCorruptedException;

/**
 * Репозиторий данных организаций.
 */
public abstract class Repository {
    /**
     * Загружает данные организаций из репозитория.
     * 
     * @return данные организаций
     * @throws RepositoryAccessDeniedException  если не удается получить доступ к
     *                                          репозиторию
     * @throws RepositoryDataCorruptedException если не удается прочитать данные из
     *                                          репозитория
     */
    public abstract HashSet<Organization> load()
            throws RepositoryAccessDeniedException, RepositoryDataCorruptedException;

    /**
     * Сохраняет данные организаций в репозиторий.
     * 
     * @param collection Данные организаций
     * @throws RepositoryAccessDeniedException если не удается получить доступ к
     *                                         репозиторию
     */
    public abstract void save(HashSet<Organization> collection)
            throws RepositoryAccessDeniedException;
}