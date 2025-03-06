package proglab5.utils.fields;

public enum CoordinatesFields {
    X("x", "X"),
    Y("y", "Y");

    private final String name;
    private final String displayName;
    private final String extra;

    private CoordinatesFields(String name, String displayName) {
        this(name, displayName, null);
    }

    private CoordinatesFields(String name, String displayName, String extra) {
        this.name = name;
        this.displayName = displayName;
        this.extra = extra;
    }

    public static CoordinatesFields fromName(String name) {
        for (CoordinatesFields coordsFields : CoordinatesFields.values()) {
            if (coordsFields.getName().equals(name)) {
                return coordsFields;
            }
        }

        throw new IllegalArgumentException("Отсутствует константа `CoordinatesFields` с названием " + name);
    }

    public String getName() {
        return name;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getExtra() {
        return extra;
    }
}
