package proglab.utils.parsers;

import java.time.ZonedDateTime;
import java.time.format.DateTimeParseException;
import java.util.Map;

import proglab.domain.Address;
import proglab.domain.Coordinates;
import proglab.domain.Organization;
import proglab.domain.OrganizationTemplate;
import proglab.domain.OrganizationType;
import proglab.exceptions.DataParserException;
import proglab.exceptions.InvalidFieldFormatException;
import proglab.utils.fields.AddressFields;
import proglab.utils.fields.CoordinatesFields;
import proglab.utils.fields.OrganizationFields;
import proglab.utils.validators.OrganizationValidator;

public final class OrganizationParser {
    public static Organization parse(Map<OrganizationFields, Object> data) throws DataParserException {
        int id = 0;
        ZonedDateTime creationDate = null;
        OrganizationTemplate.Builder builder = new OrganizationTemplate.Builder();

        try {
            id = (Integer) data.get(OrganizationFields.ID);
            creationDate = (ZonedDateTime) data.get(OrganizationFields.CREATION_DATE);

            builder.setName((String) data.get(OrganizationFields.NAME));
            builder.setCoordinates((Coordinates) data.get(OrganizationFields.COORDINATES));
            builder.setAnnualTurnover((Float) data.get(OrganizationFields.ANNUAL_TURNOVER));
            builder.setFullName((String) data.get(OrganizationFields.FULL_NAME));
            builder.setEmployeesCount((Integer) data.get(OrganizationFields.EMPLOYEES_COUNT));
            builder.setType((OrganizationType) data.get(OrganizationFields.TYPE));
            builder.setOfficialAddress((Address) data.get(OrganizationFields.OFFICIAL_ADDRESS));
        } catch (InvalidFieldFormatException | ClassCastException | NullPointerException e) {
            throw new DataParserException(e);
        }

        try {
            return new Organization(id, creationDate, builder.build());
        } catch (IllegalStateException e) {
            throw new DataParserException(e);
        }
    }

    public static Integer parseId(String data) throws DataParserException {
        try {
            Integer id = Integer.parseInt(data);
            if (!OrganizationValidator.validateId(id)) {
                throw new InvalidFieldFormatException(OrganizationFields.ID.getName());
            }

            return id;
        } catch (NumberFormatException | NullPointerException | InvalidFieldFormatException e) {
            throw new DataParserException(e);
        }
    }

    public static String parseName(String data) throws DataParserException {
        return OrganizationTemplateParser.parseName(data);
    }

    public static Coordinates parseCoordinates(Map<CoordinatesFields, Object> data) throws DataParserException {
        return OrganizationTemplateParser.parseCoordinates(data);
    }

    public static ZonedDateTime parseCreationDate(String data) throws DataParserException {
        try {
            ZonedDateTime creationDate = ZonedDateTime.parse(data);
            if (!OrganizationValidator.validateCreationDate(creationDate)) {
                throw new InvalidFieldFormatException(OrganizationFields.CREATION_DATE.getName());
            }

            return creationDate;
        } catch (DateTimeParseException | NullPointerException | InvalidFieldFormatException e) {
            throw new DataParserException(e);
        }
    }

    public static Float parseAnnualTurnover(String data) throws DataParserException {
        return OrganizationTemplateParser.parseAnnualTurnover(data);
    }

    public static String parseFullName(String data) throws DataParserException {
        return OrganizationTemplateParser.parseFullName(data);
    }

    public static Integer parseEmployeesCount(String data) throws DataParserException {
        return OrganizationTemplateParser.parseEmployeesCount(data);
    }

    public static OrganizationType parseType(String data) throws DataParserException {
        return OrganizationTemplateParser.parseType(data);
    }

    public static Address parseOfficialAddress(Map<AddressFields, Object> data) throws DataParserException {
        return OrganizationTemplateParser.parseOfficialAddress(data);
    }
}
