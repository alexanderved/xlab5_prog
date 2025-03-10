package proglab.utils.validators;

import proglab.domain.Location;

public final class LocationValidator {
    public static boolean validate(Location location) {
        return location != null
            && validateY(location.getY()) 
            && validateZ(location.getZ());
    }

    public static boolean validateY(Long y) {
        return y != null;
    }

    public static boolean validateZ(Integer z) {
        return z != null;
    }
}
