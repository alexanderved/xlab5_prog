package proglab5.utils.validators;

import java.time.ZonedDateTime;

import proglab5.domain.Address;
import proglab5.domain.Coordinates;
import proglab5.domain.Organization;
import proglab5.domain.OrganizationType;

public final class OrganizationValidator {
    public static boolean validate(Organization org) {
        return org != null
            && validateId(org.getId())
            && validateName(org.getName())
            && validateCoordinates(org.getCoordinates())
            && validateCreationDate(org.getCreationDate())
            && validateAnnualTurnover(org.getAnnualTurnover())
            && validateEmployeesCount(org.getEmployeesCount())
            && validateType(org.getType())
            && validateOfficialAddress(org.getOfficialAddress());
    }

    public static boolean validateId(int id) {
        return id > 0;
    }

    public static boolean validateName(String name) {
        return name != null && !name.isEmpty();
    }

    public static boolean validateCoordinates(Coordinates coordinates) {
        return CoordinatesValidator.validate(coordinates);
    }

    public static boolean validateCreationDate(ZonedDateTime creationDate) {
        return creationDate != null;
    }

    public static boolean validateAnnualTurnover(Float annualTurnover) {
        return annualTurnover == null || annualTurnover > 0;
    }

    public static boolean validateEmployeesCount(int employeesCount) {
        return employeesCount > 0;
    }

    public static boolean validateType(OrganizationType type) {
        return type != null;
    }

    public static boolean validateOfficialAddress(Address officialAddress) {
        return officialAddress == null || AddressValidator.validate(officialAddress);
    }
}
