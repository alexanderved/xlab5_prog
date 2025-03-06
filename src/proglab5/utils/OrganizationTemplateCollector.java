package proglab5.utils;

import proglab5.domain.OrganizationTemplate;
import proglab5.exceptions.InputDeniedException;
import proglab5.exceptions.InputFailedException;
import proglab5.exceptions.UnexpectedEODException;
import proglab5.utils.fields.AddressFields;
import proglab5.utils.fields.CoordinatesFields;
import proglab5.utils.fields.LocationFields;
import proglab5.utils.fields.OrganizationFields;
import proglab5.utils.parsers.AddressParser;
import proglab5.utils.parsers.CoordinatesParser;
import proglab5.utils.parsers.LocationParser;
import proglab5.utils.parsers.OrganizationTemplateParser;
import proglab5.view.View;
import proglab5.view.inputrequests.InputRequestFactory;

public class OrganizationTemplateCollector {
    public static OrganizationTemplate collect(View view)
            throws UnexpectedEODException {
        InputRequestFactory inReqFactory = view.getInputRequestFactory();

        var coordinatesReq = inReqFactory
                .createCompositeDataInputRequest(OrganizationFields.COORDINATES.getDisplayName(),
                        OrganizationTemplateParser::parseCoordinates)
                .addFieldRequest(CoordinatesFields.X,
                        inReqFactory.createPrimitiveDataInputRequest(CoordinatesFields.X.getDisplayName(),
                                CoordinatesParser::parseX))
                .addFieldRequest(CoordinatesFields.Y,
                        inReqFactory.createPrimitiveDataInputRequest(CoordinatesFields.Y.getDisplayName(),
                                CoordinatesParser::parseY));

        var townReq = inReqFactory
                .createCompositeDataInputRequest(AddressFields.TOWN.getDisplayName(), AddressParser::parseTown)
                .addFieldRequest(LocationFields.X,
                        inReqFactory.createPrimitiveDataInputRequest(LocationFields.X.getDisplayName(),
                                LocationParser::parseX))
                .addFieldRequest(LocationFields.Y,
                        inReqFactory.createPrimitiveDataInputRequest(LocationFields.Y.getDisplayName(),
                                LocationParser::parseY))
                .addFieldRequest(LocationFields.Z,
                        inReqFactory.createPrimitiveDataInputRequest(LocationFields.Z.getDisplayName(),
                                LocationParser::parseZ));

        var officialAddressReq = inReqFactory
                .createCompositeDataInputRequest(OrganizationFields.OFFICIAL_ADDRESS.getDisplayName(),
                        OrganizationTemplateParser::parseOfficialAddress)
                .addFieldRequest(AddressFields.STREET,
                        inReqFactory.createPrimitiveDataInputRequest(AddressFields.STREET.getDisplayName(),
                                AddressParser::parseStreet))
                .addFieldRequest(AddressFields.TOWN, townReq);

        var templateReq = inReqFactory
                .createCompositeDataInputRequest("данные организации",
                        OrganizationTemplateParser::parse)
                .addFieldRequest(OrganizationFields.NAME,
                        inReqFactory.createPrimitiveDataInputRequest(OrganizationFields.NAME.getDisplayName(),
                                OrganizationTemplateParser::parseName))
                .addFieldRequest(OrganizationFields.COORDINATES, coordinatesReq)
                .addFieldRequest(OrganizationFields.ANNUAL_TURNOVER,
                        inReqFactory.createPrimitiveDataInputRequest(OrganizationFields.ANNUAL_TURNOVER.getDisplayName(),
                                OrganizationTemplateParser::parseAnnualTurnover))
                .addFieldRequest(OrganizationFields.FULL_NAME,
                        inReqFactory.createPrimitiveDataInputRequest(OrganizationFields.FULL_NAME.getDisplayName(),
                                OrganizationTemplateParser::parseFullName))
                .addFieldRequest(OrganizationFields.EMPLOYEES_COUNT,
                        inReqFactory.createPrimitiveDataInputRequest(OrganizationFields.EMPLOYEES_COUNT.getDisplayName(),
                                OrganizationTemplateParser::parseEmployeesCount))
                .addFieldRequest(OrganizationFields.TYPE,
                        inReqFactory.createPrimitiveDataInputRequest(OrganizationFields.TYPE.getDisplayName(),
                                OrganizationFields.TYPE.getExtra(), OrganizationTemplateParser::parseType))
                .addFieldRequest(OrganizationFields.OFFICIAL_ADDRESS,
                        inReqFactory.createOptionalInputRequest(officialAddressReq));

        try {
            return view.input(templateReq);
        } catch (InputDeniedException | InputFailedException e) {
            view.error("Не удается считать данные организации: " + e.getMessage());

            return null;
        }
    }
}
