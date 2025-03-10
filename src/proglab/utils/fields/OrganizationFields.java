package proglab.utils.fields;

import java.util.List;
import java.util.stream.Stream;

import proglab.domain.OrganizationType;

public enum OrganizationFields implements Fields {
    ID("id", "ID"),
    NAME("name", "название"),
    COORDINATES("coordinates", "координаты"),
    CREATION_DATE("creationDate", "время создания"),
    ANNUAL_TURNOVER("annualTurnover", "годовой оборот"),
    FULL_NAME("fullName", "полное название"),
    EMPLOYEES_COUNT("employeesCount", "число сотрудников"),
    TYPE("type", "тип", getTypeExtraInfo()),
    OFFICIAL_ADDRESS("officialAddress", "официальный адрес");

    private final String name;
    private final String displayName;
    private final String extra;

    private OrganizationFields(String name, String displayName) {
        this(name, displayName, null);
    }

    private OrganizationFields(String name, String displayName, String extra) {
        this.name = name;
        this.displayName = displayName;
        this.extra = extra;
    }

    public static OrganizationFields fromName(String name) {
        for (OrganizationFields orgFields : OrganizationFields.values()) {
            if (orgFields.getName().equals(name)) {
                return orgFields;
            }
        }

        throw new IllegalArgumentException("Отсутствует константа `OrganizationFields` с названием " + name);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getDisplayName() {
        return displayName;
    }

    @Override
    public String getExtra() {
        return extra;
    }

    private static String getTypeExtraInfo() {
        List<String> typeStrings = Stream.of(OrganizationType.values())
                .map(OrganizationType::toString)
                .toList();

        return String.join("/", typeStrings);
    }
}
