package proglab.domain;

import java.util.Objects;

import proglab.exceptions.InvalidFieldFormatException;
import proglab.utils.validators.LocationValidator;

/**
 * Класс координат локации в трехмерном пространстве.
 */
public final class Location {
    private final long x;
    private final Long y; // Поле не может быть null
    private final Integer z; // Поле не может быть null

    /**
     * @param x Координата X
     * @param y Координата Y
     * @param z Координата Z
     * @throws InvalidFieldFormatException если аргументы не удовлетворяют ожидаемому формату
     */
    public Location(long x, Long y, Integer z) throws InvalidFieldFormatException {
        this.x = x;

        if (!LocationValidator.validateY(y)) {
            throw new InvalidFieldFormatException("y", "Поле `y` не может null");
        }
        this.y = y;

        if (!LocationValidator.validateZ(z)) {
            throw new InvalidFieldFormatException("z", "Поле `z` не может null");
        }
        this.z = z;
    }

    /**
     * @return координату X
     */
    public long getX() {
        return x;
    }

    /**
     * @return координату Y
     */
    public Long getY() {
        return y;
    }

    /**
     * @return координату Z
     */
    public Integer getZ() {
        return z;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Location l = (Location)o;

        return x == l.x && y.equals(l.y) && z.equals(l.z);
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y, z);
    }

    @Override
    public String toString() {
        return "(" + x + ", " + y + ", " + z + ")";
    }
}