package proglab5.utils.parsers;

import java.time.ZonedDateTime;
import java.time.format.DateTimeParseException;
import java.util.Map;

import proglab5.domain.Organization;
import proglab5.domain.OrganizationTemplate;
import proglab5.exceptions.DataParserException;
import proglab5.exceptions.InvalidFieldFormatException;
import proglab5.utils.validators.OrganizationValidator;

public final class OrganizationParser {
    public static final String ID = "id";
    public static final String CREATION_DATE = "creationDate";
    public static final String TEMPLATE = "template";

    public static Organization parse(Map<String, Object> data) throws DataParserException {
        try {
            if (data.containsKey(CREATION_DATE)) {
                return new Organization((Integer) data.get(ID), (ZonedDateTime) data.get(CREATION_DATE),
                        (OrganizationTemplate) data.get(TEMPLATE));
            }

            return new Organization((Integer) data.get(ID), (OrganizationTemplate) data.get(TEMPLATE));
        } catch (IllegalArgumentException | NullPointerException | ClassCastException e) {
            throw new DataParserException(e);
        }
    }

    public static Integer parseId(String data) throws DataParserException {
        try {
            Integer id = Integer.parseInt(data);
            if (!OrganizationValidator.validateId(id)) {
                throw new InvalidFieldFormatException(ID);
            }

            return id;
        } catch (NumberFormatException | NullPointerException | InvalidFieldFormatException e) {
            throw new DataParserException(e);
        }
    }

    public static ZonedDateTime parseCreationDate(String data) throws DataParserException {
        try {
            ZonedDateTime creationDate = ZonedDateTime.parse(data);
            if (!OrganizationValidator.validateCreationDate(creationDate)) {
                throw new InvalidFieldFormatException(CREATION_DATE);
            }

            return creationDate;
        } catch (DateTimeParseException | NullPointerException | InvalidFieldFormatException e) {
            throw new DataParserException(e);
        }
    }

    public static OrganizationTemplate parseTemplate(Map<String, Object> data) throws DataParserException {
        return OrganizationTemplateParser.parse(data);
    }
}
