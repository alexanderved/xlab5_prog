package proglab5.domain;

import java.util.Objects;
import proglab5.exceptions.InvalidFieldFormatException;
import proglab5.utils.validators.AddressValidator;

/**
 * Класс адреса.
 */
public final class Address {
    private final String street; // Строка не может быть пустой, Поле может быть null
    private final Location town; // Поле не может быть null

    /**
     * @param street Название улицы
     * @param town Координаты города
     * @throws InvalidFieldFormatException если аргументы не удовлетворяют ожидаемому формату
     */
    public Address(String street, Location town) throws InvalidFieldFormatException {
        if (!AddressValidator.validateStreet(street)) {
            throw new InvalidFieldFormatException("street",
                    "Поле `street` не может быть пустой строкой");
        }
        this.street = street;

        if (!AddressValidator.validateTown(town)) {
            throw new InvalidFieldFormatException("town",
                    "Поле `town` не может null");
        }
        this.town = town;
    }

    /**
     * @return название улицы
     */
    public String getStreet() {
        return street;
    }

    /**
     * @return координаты города.
     */
    public Location getTown() {
        return town;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Address a = (Address)o;

        return street.equals(a.street) && town.equals(a.town);
    }

    @Override
    public int hashCode() {
        return Objects.hash(street, town);
    }

    @Override
    public String toString() {
        return "Улица " + street + ", " + "Город на координатах " + town.toString();
    }
}