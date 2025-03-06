package proglab5.utils.parsers;

import java.util.Map;

import proglab5.domain.Address;
import proglab5.domain.Location;
import proglab5.exceptions.DataParserException;
import proglab5.exceptions.InvalidFieldFormatException;
import proglab5.utils.validators.AddressValidator;
import proglab5.utils.fields.AddressFields;
import proglab5.utils.fields.LocationFields;

public final class AddressParser {
    public static Address parse(Map<AddressFields, Object> data) throws DataParserException {
        try {
            return new Address((String)data.get(AddressFields.STREET),
                (Location)data.get(AddressFields.TOWN));
        } catch (InvalidFieldFormatException | ClassCastException
                | NullPointerException e) {
            throw new DataParserException(e);
        }
    }

    public static String parseStreet(String data) throws DataParserException {
        if (!AddressValidator.validateStreet(data)) {
            throw new DataParserException(
                new InvalidFieldFormatException(AddressFields.STREET.getName()));
        }

        return data;
    }

    public static Location parseTown(Map<LocationFields, Object> data)
        throws DataParserException
    {
        return LocationParser.parse(data);
    }
}
