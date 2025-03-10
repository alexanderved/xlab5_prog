package proglab.utils.parsers;

import java.util.Map;

import proglab.domain.Address;
import proglab.domain.Coordinates;
import proglab.domain.OrganizationTemplate;
import proglab.domain.OrganizationType;
import proglab.exceptions.DataParserException;
import proglab.exceptions.InvalidFieldFormatException;
import proglab.utils.fields.AddressFields;
import proglab.utils.fields.CoordinatesFields;
import proglab.utils.fields.OrganizationFields;
import proglab.utils.validators.OrganizationValidator;

public final class OrganizationTemplateParser {
    public static OrganizationTemplate parse(Map<OrganizationFields, Object> data)
            throws DataParserException {
        OrganizationTemplate.Builder builder = new OrganizationTemplate.Builder();

        try {
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
            return builder.build();
        } catch (IllegalStateException e) {
            throw new DataParserException(e);
        }
    }

    public static String parseName(String data) throws DataParserException {
        if (!OrganizationValidator.validateName(data)) {
            throw new DataParserException(
                    new InvalidFieldFormatException(OrganizationFields.NAME.getName()));
        }

        return data;
    }

    public static Coordinates parseCoordinates(Map<CoordinatesFields, Object> data)
            throws DataParserException {
        return CoordinatesParser.parse(data);
    }

    public static Float parseAnnualTurnover(String data) throws DataParserException {
        try {
            Float annualTurnover = Float.parseFloat(data);
            if (!OrganizationValidator.validateAnnualTurnover(annualTurnover)) {
                throw new InvalidFieldFormatException(OrganizationFields.ANNUAL_TURNOVER.getName());
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
                throw new InvalidFieldFormatException(OrganizationFields.EMPLOYEES_COUNT.getName());
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
                throw new InvalidFieldFormatException(OrganizationFields.TYPE.getName());
            }

            return type;
        } catch (IllegalArgumentException | NullPointerException
                | InvalidFieldFormatException e) {
            throw new DataParserException(e);
        }
    }

    public static Address parseOfficialAddress(Map<AddressFields, Object> data)
            throws DataParserException {
        return AddressParser.parse(data);
    }
}
