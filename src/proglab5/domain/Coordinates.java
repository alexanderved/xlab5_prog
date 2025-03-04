package proglab5.domain;

import java.util.Objects;
import proglab5.exceptions.InvalidFieldFormatException;
import proglab5.utils.validators.CoordinatesValidator;

/** 
 * Класс координат на плоскости.
 */
public final class Coordinates {
    private final float x;
    private final Integer y; // Поле не может быть null

    /**
     * @param x Координата X
     * @param y Координата Y
     * @throws InvalidFieldFormatException если аргументы не удовлетворяют ожидаемому формату
     */
    public Coordinates(float x, Integer y) throws InvalidFieldFormatException {
        this.x = x;

        if (!CoordinatesValidator.validateY(y)) {
            throw new InvalidFieldFormatException("y", "Поле `y` не может null");
        }
        this.y = y;
    }

    /**
     * @return координату X
     */
    public float getX() {
        return x;
    }

    /**
     * @return Координату Y
     */
    public Integer getY() {
        return y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Coordinates c = (Coordinates)o;

        return x == c.x && y.equals(c.y);
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }
}