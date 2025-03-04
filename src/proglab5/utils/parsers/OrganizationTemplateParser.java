package proglab5.utils.parsers;

import java.util.Map;

import proglab5.domain.Address;
import proglab5.domain.Coordinates;
import proglab5.domain.OrganizationTemplate;
import proglab5.domain.OrganizationType;
import proglab5.exceptions.DataParserException;
import proglab5.exceptions.InvalidFieldFormatException;
import proglab5.utils.validators.OrganizationValidator;

public final class OrganizationTemplateParser {
    public static final String NAME = "name";
    public static final String COORDINATES = "coordinates";
    public static final String ANNUAL_TURNOVER = "annualTurnover";
    public static final String FULL_NAME = "fullName";
    public static final String EMPLOYEES_COUNT = "employeesCount";
    public static final String TYPE = "type";
    public static final String OFFICIAL_ADDRESS = "officialAddress";

    public static OrganizationTemplate parse(Map<String, Object> data)
            throws DataParserException {
        OrganizationTemplate.Builder builder = new OrganizationTemplate.Builder();

        try {
            builder.setName((String) data.get(NAME));
            builder.setCoordinates((Coordinates) data.get(COORDINATES));
            builder.setAnnualTurnover((Float) data.get(ANNUAL_TURNOVER));
            builder.setFullName((String) data.get(FULL_NAME));
            builder.setEmployeesCount((Integer) data.get(EMPLOYEES_COUNT));
            builder.setType((OrganizationType) data.get(TYPE));
            builder.setOfficialAddress((Address) data.get(OFFICIAL_ADDRESS));
        } catch (InvalidFieldFormatException | ClassCastException | NullPointerException e) {
            throw new DataParserException(e);
        }

        try {
            return builder.build();
        } catch (IllegalStateException e) {
            throw new DataParserException(e);
        }
    }

    public static String parseName(String data) throws DataParserException {
        if (!OrganizationValidator.validateName(data)) {
            throw new DataParserException(
                    new InvalidFieldFormatException(NAME));
        }

        return data;
    }

    public static Coordinates parseCoordinates(Map<String, Object> data)
            throws DataParserException {
        return CoordinatesParser.parse(data);
    }

    public static Float parseAnnualTurnover(String data) throws DataParserException {
        try {
            Float annualTurnover = Float.parseFloat(data);
            if (!OrganizationValidator.validateAnnualTurnover(annualTurnover)) {
                throw new InvalidFieldFormatException(ANNUAL_TURNOVER);
            }

            return annualTurnover;
        } catch (NumberFormatException | NullPointerException
                | InvalidFieldFormatException e) {
            throw new DataParserException(e);
        }
    }

    public static String parseFullName(String data) throws DataParserException {
        return data;
    }

    public static Integer parseEmployeesCount(String data) throws DataParserException {
        try {
            int employeesCount = Integer.parseInt(data);
            if (!OrganizationValidator.validateEmployeesCount(employeesCount)) {
                throw new InvalidFieldFormatException(EMPLOYEES_COUNT);
            }

            return employeesCount;
        } catch (NumberFormatException | NullPointerException
                | InvalidFieldFormatException e) {
            throw new DataParserException(e);
        }
    }

    public static OrganizationType parseType(String data) throws DataParserException {
        try {
            OrganizationType type = OrganizationType.valueOf(data);
            if (!OrganizationValidator.validateType(type)) {
                throw new InvalidFieldFormatException(TYPE);
            }

            return type;
        } catch (IllegalArgumentException | NullPointerException
                | InvalidFieldFormatException e) {
            throw new DataParserException(e);
        }
    }

    public static Address parseOfficialAddress(Map<String, Object> data)
            throws DataParserException {
        return AddressParser.parse(data);
    }
}
