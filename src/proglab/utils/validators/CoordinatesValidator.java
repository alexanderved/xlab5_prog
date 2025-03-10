package proglab.utils.validators;

import proglab.domain.Coordinates;

public final class CoordinatesValidator {
    public static boolean validate(Coordinates coordinates) {
        return coordinates != null && validateY(coordinates.getY());
    }

    public static boolean validateY(Integer y) {
        return y != null;
    }
}