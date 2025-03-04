package proglab5.domain;

import java.util.Objects;
import proglab5.exceptions.InvalidFieldFormatException;
import proglab5.utils.validators.OrganizationValidator;

/**
 * Шаблон организации, представляющий собой набор данных этой организации.
 */
public final class OrganizationTemplate implements Comparable<Organization> {
    private final String name;
    private final Coordinates coordinates;
    private final Float annualTurnover;
    private final String fullName;
    private final int employeesCount;
    private final OrganizationType type;
    private final Address officialAddress;

    private OrganizationTemplate(Builder builder) {
        name = builder.name;
        coordinates = builder.coordinates;
        annualTurnover = builder.annualTurnover;
        fullName = builder.fullName;
        employeesCount = builder.employeesCount;
        type = builder.type;
        officialAddress = builder.officialAddress;
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

        OrganizationTemplate org = (OrganizationTemplate)o;

        if (annualTurnover == null && org.annualTurnover != null) {
            return false;
        }
        if (fullName == null && org.fullName != null) {
            return false;
        }
        if (officialAddress == null && org.officialAddress != null) {
            return false;
        }

        return name.equals(org.name)
                && coordinates.equals(org.coordinates)
                && annualTurnover.equals(org.annualTurnover)
                && fullName.equals(org.fullName)
                && employeesCount == org.employeesCount
                && type.equals(org.type)
                && officialAddress.equals(org.officialAddress);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, coordinates, annualTurnover,
                fullName, employeesCount, type, officialAddress);
    }

    @Override
    public String toString() {
        String s = "Шаблон Организации";
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

    /**
     * Строитель экземпляров класса OrganizationTemplate.
     */
    public static final class Builder {
        private String name;
        private Coordinates coordinates;
        private Float annualTurnover;
        private String fullName;
        private Integer employeesCount;
        private OrganizationType type;
        private Address officialAddress;

        /**
         * Устанавливает название организации.
         * @param name Название организации
         * @return Строитель экземпляров класса
         * @throws InvalidFieldFormatException если поле не удовлетворяет формату
         */
        public Builder setName(String name) throws InvalidFieldFormatException {
            if (!OrganizationValidator.validateName(name)) {
                throw new InvalidFieldFormatException("name");
            }
            this.name = name;

            return this;
        }

        public Builder setCoordinates(Coordinates coordinates)
            throws InvalidFieldFormatException
        {
            if (!OrganizationValidator.validateCoordinates(coordinates)) {
                throw new InvalidFieldFormatException("coordinates");
            }
            this.coordinates = coordinates;

            return this;
        }

        public Builder setAnnualTurnover(Float annualTurnover)
            throws InvalidFieldFormatException
        {
            if (!OrganizationValidator.validateAnnualTurnover(annualTurnover)) {
                throw new InvalidFieldFormatException("annualTurnover");
            }
            this.annualTurnover = annualTurnover;

            return this;
        }

        public Builder setFullName(String fullName) {
            this.fullName = fullName;

            return this;
        }

        public Builder setEmployeesCount(int employeesCount)
            throws InvalidFieldFormatException
        {
            if (!OrganizationValidator.validateEmployeesCount(employeesCount)) {
                throw new InvalidFieldFormatException("employeesCount");
            }
            this.employeesCount = employeesCount;

            return this;
        }

        public Builder setType(OrganizationType type) throws InvalidFieldFormatException {
            if (!OrganizationValidator.validateType(type)) {
                throw new InvalidFieldFormatException("type");
            }
            this.type = type;

            return this;
        }

        public Builder setOfficialAddress(Address officialAddress) {
            this.officialAddress = officialAddress;

            return this;
        }

        public boolean isComplete() {
            return name != null && coordinates != null
                && employeesCount != null && type != null;
        }

        public OrganizationTemplate build() {
            if (!isComplete()) {
                throw new IllegalStateException(
                    "Метод `build` класса `OrganizationTemplate.Builder` не может быть"
                    + " вызван пока не будут установлены все обязательные поля");
            }

            return new OrganizationTemplate(this);
        }
    }
}