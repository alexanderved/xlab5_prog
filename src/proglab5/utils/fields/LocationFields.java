package proglab5.utils.fields;

public enum LocationFields {
    X("x", "X"),
    Y("y", "Y"),
    Z("z", "Z");

    private final String name;
    private final String displayName;
    private final String extra;

    private LocationFields(String name, String displayName) {
        this(name, displayName, null);
    }

    private LocationFields(String name, String displayName, String extra) {
        this.name = name;
        this.displayName = displayName;
        this.extra = extra;
    }

    public static LocationFields fromName(String name) {
        for (LocationFields locFields : LocationFields.values()) {
            if (locFields.getName().equals(name)) {
                return locFields;
            }
        }

        throw new IllegalArgumentException("Отсутствует константа `LocationFields` с названием " + name);
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
