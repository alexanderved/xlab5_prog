package proglab.utils.parsers;

import java.util.Map;

import proglab.domain.Coordinates;
import proglab.exceptions.DataParserException;
import proglab.exceptions.InvalidFieldFormatException;
import proglab.utils.fields.CoordinatesFields;
import proglab.utils.validators.CoordinatesValidator;

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
