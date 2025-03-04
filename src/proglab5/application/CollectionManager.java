package proglab5.application;

import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import proglab5.domain.Organization;
import proglab5.domain.OrganizationTemplate;
import proglab5.exceptions.RepositoryAccessDeniedException;
import proglab5.exceptions.RepositoryDataCorruptedException;
import proglab5.repository.Repository;
import proglab5.utils.validators.OrganizationCollectionValidator;

/**
 * Класс, оперирующий коллекцией организаций.
 */
public class CollectionManager {
    private HashSet<Organization> collection;
    private IDGenerator idGen;
    private Repository repo;
    private ZonedDateTime initDate;
    private ZonedDateTime lastModDate;

    /**
     * @param idGen Генератор ID для добавляемых в коллекцию организаций
     * @param repo Репозиторий организаций
     * @throws RepositoryAccessDeniedException если не удается получить
     *                                         доступ к репозиторию
     * @throws RepositoryDataCorruptedException если данные в репозитории не соответстую
     *                                          ожидаемому формату
     * @see IDGenerator
     * @see Repository
     */
    public CollectionManager(IDGenerator idGen, Repository repo)
        throws RepositoryAccessDeniedException, RepositoryDataCorruptedException
    {
        if (idGen == null) {
            throw new IllegalArgumentException(
                    "`idGen` в классе `CollectionManager` не должно быть null");
        }
        this.idGen = idGen;

        if (repo == null) {
            throw new IllegalArgumentException(
                    "`repo` в классе `CollectionManager` не должно быть null");
        }
        this.repo = repo;

        collection = loadCollection(repo, idGen);
        initDate = ZonedDateTime.now();
        lastModDate = initDate;
    }

    private static HashSet<Organization> loadCollection(Repository repo, IDGenerator idGen)
        throws RepositoryAccessDeniedException, RepositoryDataCorruptedException
    {
        HashSet<Organization> collection = repo.load();
        if (!OrganizationCollectionValidator.validate(collection)) {
            throw new RepositoryDataCorruptedException();
        }

        collection.stream()
            .map(Organization::getId)
            .forEach(idGen::reserve);

        return collection;
    }

    /**
     * @return размер управляемой коллекции
     */
    public int getCollectionSize() {
        return collection.size();
    }

    /**
     * @return времяя инициализации управляемой коллекции
     */
    public ZonedDateTime getInitDate() {
        return initDate;
    }

    /**
     * @return время модификации управляемой коллекции
     */
    public ZonedDateTime getLastModDate() {
        return lastModDate;
    }

    private void updateLastModDate() {
        lastModDate = ZonedDateTime.now();
    }

    /**
     * @return тип управляемой коллекции
     */
    public String getCollectionType() {
        return collection.getClass().getName();
    }

    /**
     * Добавляет в коллекцию новую организацию, созданную по шаблону.
     * 
     * @param template Шаблон новой организации
     * @see OrganizationTemplate
     */
    public void add(OrganizationTemplate template) {
        updateLastModDate();

        collection.add(new Organization(idGen.generateID(), template));
    }

    /**
     * Удалаяет организацию из коллекции по ее ID.
     * @param id ID организации, которую нужно удалить
     * @return {@code true}, если элемент с данным ID присутствовал в коллекции
     */
    public boolean removeById(int id) {
        if (collection.removeIf((org) -> org.getId() == id)) {
            updateLastModDate();

            return true;
        }

        return false;
    }

    /**
     * Удаляет из коллекции все организации, которые меньше,
     * чем организация, созданная по данному шаблону.
     * @param template Шаблон организации, с которой происходит
     *                 сравнение элементов коллекции
     * @see OrganizationTemplate
     */
    public void removeLower(OrganizationTemplate template) {
        if (collection.removeIf((org) -> template.compareTo(org) > 0)) {
            updateLastModDate();
        }
    }

    /**
     * Обновляет информацию организации с данным ID с использованием данного шаблона.
     * @param id ID организации, которую нужно обновить
     * @param template Шаблон новых данных организации
     * @return {@code true}, если элемент с данным ID присутствует в коллекции
     */
    public boolean update(int id, OrganizationTemplate template) {
        Optional<Organization> org = collection.stream()
            .filter((o) -> o.getId() == id)
            .findFirst();

        if (org.isEmpty()) {
            return false;
        }
        org.get().update(template);
        updateLastModDate();

        return true;
    }

    /**
     * Очищает коллекцию.
     */
    public void clear() {
        updateLastModDate();

        collection.clear();
    }

    /**
     * Сохраняет коллекцию организаций в репозиторий
     * @throws RepositoryAccessDeniedException если не удается получить
     *                                         доступ к репозиторию
     */
    public void save() throws RepositoryAccessDeniedException {
        repo.save(collection);
    }

    /**
     * @return наибольший элемент коллекции
     * @see Organization
     */
    public Organization max() {
        return collection.stream()
            .max(Organization::compareTo)
            .orElse(null);
    }

    /**
     * @return наименьший элемент коллекции
     * @see Organization
     */
    public Organization min() {
        return collection.stream()
            .min(Organization::compareTo)
            .orElse(null);
    }

    /**
     * @return среднее арифметическое количеств сотрудников каждой из организаций
     */
    public int averageOfEmployeesCount() {
        int nbOrgs = getCollectionSize();
        if (nbOrgs == 0) {
            return 0;
        }

        return collection.stream()
            .map((org) -> org.getEmployeesCount())
            .reduce(0, (a, b) -> a + b) / nbOrgs;
    }

    /**
     * Преобразует все организации, полные названия которых начинаются
     * с данной подстроки, в строку.
     * @param fullName Подстрока, с которой должны начинаться
     *                 полные названия организаций
     * @return строковое представление организаций, удовлетворяющих требованиям
     */
    public String toStringFilteredFullName(String fullName) {
        String s = "{\n";
        for (Organization org : collection) {
            if (org.getFullName() == null || !org.getFullName().startsWith(fullName)) {
                continue;
            }

            s += org.toString() + ";\n";
        }
        s += "}";

        return s;
    }

    /**
     * @return список годовых оборотов всех организаций
     */
    public List<Float> getAnnualTurnovers() {
        return collection.stream()
            .map((org) -> org.getAnnualTurnover())
            .filter((annualTurnover) -> annualTurnover != null)
            .toList();
    }

    @Override
    public String toString() {
        String s = "{\n";
        for (Organization org : collection) {
            s += org.toString() + ";\n";
        }
        s += "}";

        return s;
    }
}