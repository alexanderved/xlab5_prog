package proglab5.utils.parsers;

import java.util.Map;

import proglab5.domain.Coordinates;
import proglab5.exceptions.DataParserException;
import proglab5.exceptions.InvalidFieldFormatException;
import proglab5.utils.fields.CoordinatesFields;
import proglab5.utils.validators.CoordinatesValidator;

public final class CoordinatesParser {
    public static Coordinates parse(Map<CoordinatesFields, Object> data)
            throws DataParserException {
        try {
            return new Coordinates((Float) data.get(CoordinatesFields.X), (Integer) data.get(CoordinatesFields.Y));
        } catch (InvalidFieldFormatException | ClassCastException
                | NullPointerException e) {
            throw new DataParserException(e);
        }
    }

    public static Float parseX(String data) throws DataParserException {
        try {
            return Float.parseFloat(data);
        } catch (NumberFormatException | NullPointerException e) {
            throw new DataParserException(e);
        }
    }

    public static Integer parseY(String data) throws DataParserException {
        try {
            Integer y = Integer.parseInt(data);
            if (!CoordinatesValidator.validateY(y)) {
                throw new InvalidFieldFormatException(CoordinatesFields.Y.getName());
            }

            return y;
        } catch (NumberFormatException | NullPointerException
                | InvalidFieldFormatException e) {
            throw new DataParserException(e);
        }
    }
}
