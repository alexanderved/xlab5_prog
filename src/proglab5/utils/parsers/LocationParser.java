package proglab5.utils.parsers;

import java.util.Map;

import proglab5.domain.Location;
import proglab5.exceptions.DataParserException;
import proglab5.exceptions.InvalidFieldFormatException;
import proglab5.utils.validators.LocationValidator;

public final class LocationParser {
    public static final String X = "x";
    public static final String Y = "y";
    public static final String Z = "z";

    public static Location parse(Map<String, Object> data)
            throws DataParserException {
        try {
            return new Location((Long) data.get(X),
                    (Long) data.get(Y), (Integer) data.get(Z));
        } catch (InvalidFieldFormatException | ClassCastException
                | NullPointerException e) {
            throw new DataParserException(e);
        }
    }

    public static Long parseX(String data) throws DataParserException {
        try {
            return Long.parseLong(data);
        } catch (NumberFormatException | NullPointerException e) {
            throw new DataParserException(e);
        }
    }

    public static Long parseY(String data) throws DataParserException {
        try {
            Long y = Long.parseLong(data);
            if (!LocationValidator.validateY(y)) {
                throw new InvalidFieldFormatException(Y);
            }

            return y;
        } catch (NumberFormatException | NullPointerException
                | InvalidFieldFormatException e) {
            throw new DataParserException(e);
        }
    }

    public static Integer parseZ(String data) throws DataParserException {
        try {
            Integer z = Integer.parseInt(data);
            if (!LocationValidator.validateZ(z)) {
                throw new InvalidFieldFormatException(Z);
            }

            return z;
        } catch (NumberFormatException | NullPointerException
                | InvalidFieldFormatException e) {
            throw new DataParserException(e);
        }
    }
}
