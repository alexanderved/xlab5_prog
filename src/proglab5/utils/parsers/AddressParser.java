package proglab5.utils.parsers;

import java.util.Map;

import proglab5.domain.Address;
import proglab5.domain.Location;
import proglab5.exceptions.DataParserException;
import proglab5.exceptions.InvalidFieldFormatException;
import proglab5.utils.validators.AddressValidator;

public final class AddressParser {
    public static final String STREET = "street";
    public static final String TOWN = "town";

    public static Address parse(Map<String, Object> data) throws DataParserException {
        try {
            return new Address((String)data.get(STREET),
                (Location)data.get(TOWN));
        } catch (InvalidFieldFormatException | ClassCastException
                | NullPointerException e) {
            throw new DataParserException(e);
        }
    }

    public static String parseStreet(String data) throws DataParserException {
        if (!AddressValidator.validateStreet(data)) {
            throw new DataParserException(
                new InvalidFieldFormatException(STREET));
        }

        return data;
    }

    public static Location parseTown(Map<String, Object> data)
        throws DataParserException
    {
        return LocationParser.parse(data);
    }
}
