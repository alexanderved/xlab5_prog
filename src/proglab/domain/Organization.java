package proglab.domain;

import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * Класс организации
 */
public final class Organization implements Comparable<Organization> {
    private final int id; // Значение поля должно быть больше 0,
                          // Значение этого поля должно быть уникальным,
                          // Значение этого поля должно генерироваться автоматически
    private String name; // Поле не может быть null, Строка не может быть пустой
    private Coordinates coordinates; // Поле не может быть null
    private final ZonedDateTime creationDate; // Поле не может быть null, Значение этого поля
                                              // должно генерироваться автоматически
    private Float annualTurnover; // Поле может быть null, Значение поля должно быть больше 0
    private String fullName; // Поле может быть null
    private int employeesCount; // Значение поля должно быть больше 0
    private OrganizationType type; // Поле не может быть null
    private Address officialAddress; // Поле может быть null

    /**
     * Конструктор организации, в котором датой создания устанавливается данный момент времени.
     * @param id ID организации
     * @param template Шаблон организации
     */
    public Organization(int id, OrganizationTemplate template) {
        this(id, ZonedDateTime.now(), template);
    }

    /**
     * @param id ID организации
     * @param creationDate Дата создания организации
     * @param template Шаблон организации
     */
    public Organization(int id, ZonedDateTime creationDate, OrganizationTemplate template) {
        if (id <= 0) {
            throw new IllegalArgumentException("Поле `id` класса `Organization`"
                + " должно быть больше 0");
        }

        if (creationDate == null) {
            throw new IllegalArgumentException("Поле `creationDate` класса `Organization`"
                + " не должно быть null");
        }

        this.id = id;
        name = template.getName();
        coordinates = template.getCoordinates();
        this.creationDate = creationDate;
        annualTurnover = template.getAnnualTurnover();
        fullName = template.getFullName();
        employeesCount = template.getEmployeesCount();
        type = template.getType();
        officialAddress = template.getOfficialAddress();
    }

    /**
     * Обновляет данные организации.
     * @param template Шаблон новых данных организации.
     */
    public void update(OrganizationTemplate template) {
        name = template.getName();
        coordinates = template.getCoordinates();
        annualTurnover = template.getAnnualTurnover();
        fullName = template.getFullName();
        employeesCount = template.getEmployeesCount();
        type = template.getType();
        officialAddress = template.getOfficialAddress();
    }

    /**
     * @return ID организации
     */
    public int getId() {
        return id;
    }

    /**
     * @return название организации
     */
    public String getName() {
        return name;
    }

    /**
     * @return координаты организации
     */
    public Coordinates getCoordinates() {
        return coordinates;
    }

    /**
     * @return дату создания организации
     */
    public ZonedDateTime getCreationDate() {
        return creationDate;
    }

    /**
     * @return годовой оборот организации или null, если данная информация отсутствует
     */
    public Float getAnnualTurnover() {
        return annualTurnover;
    }

    /**
     * @return полное название организации или null, если данная информация отсутствует
     */
    public String getFullName() {
        return fullName;
    }

    /**
     * @return число работников организации
     */
    public int getEmployeesCount() {
        return employeesCount;
    }

    /**
     * @return тип организации
     */
    public OrganizationType getType() {
        return type;
    }

    /**
     * @return официальный адрес организации или null, если данная информация отсутствует
     */
    public Address getOfficialAddress() {
        return officialAddress;
    }

    @Override
    public int compareTo(Organization org) {
        return getEmployeesCount() - org.getEmployeesCount();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Organization org = (Organization)o;

        if (annualTurnover == null && org.annualTurnover != null) {
            return false;
        }
        if (fullName == null && org.fullName != null) {
            return false;
        }
        if (officialAddress == null && org.officialAddress != null) {
            return false;
        }

        return id == org.id && name.equals(org.name)
                && coordinates.equals(org.coordinates)
                && creationDate.equals(org.creationDate)
                && annualTurnover.equals(org.annualTurnover)
                && fullName.equals(org.fullName)
                && employeesCount == org.employeesCount
                && type.equals(org.type)
                && officialAddress.equals(org.officialAddress);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, coordinates, creationDate, annualTurnover,
                fullName, employeesCount, type, officialAddress);
    }

    @Override
    public String toString() {
        String s = "Организация (ID: " + id + ", Дата добавления: " + creationDate + ")";
        s += "\n\tНазвание: " + name;
        s += "\n\tКоординаты: " + coordinates;
        if (annualTurnover != null) {
            s += "\n\tГодовой оборот: " + annualTurnover;
        }
        if (fullName != null) {
            s += "\n\tПолное название: " + fullName;
        }
        s += "\n\tЧисло сотрудников: " + employeesCount;
        s += "\n\tТип: " + type;
        if (officialAddress != null) {
            s += "\n\tОфициальный адрес: " + officialAddress;
        }

        return s;
    }
}