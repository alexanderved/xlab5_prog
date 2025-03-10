package proglab.utils.fields;

public enum AddressFields implements Fields {
    STREET("street", "улица"),
    TOWN("town", "координаты города");

    private final String name;
    private final String displayName;
    private final String extra;

    private AddressFields(String name, String displayName) {
        this(name, displayName, null);
    }

    private AddressFields(String name, String displayName, String extra) {
        this.name = name;
        this.displayName = displayName;
        this.extra = extra;
    }

    public static AddressFields fromName(String name) {
        for (AddressFields addrFields : AddressFields.values()) {
            if (addrFields.getName().equals(name)) {
                return addrFields;
            }
        }

        throw new IllegalArgumentException("Отсутствует константа `AddressFields` с названием " + name);
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
}
