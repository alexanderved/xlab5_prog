package proglab.utils;

import proglab.domain.OrganizationTemplate;
import proglab.exceptions.InputDeniedException;
import proglab.exceptions.InputFailedException;
import proglab.exceptions.UnexpectedEODException;
import proglab.utils.fields.AddressFields;
import proglab.utils.fields.CoordinatesFields;
import proglab.utils.fields.LocationFields;
import proglab.utils.fields.OrganizationFields;
import proglab.utils.parsers.AddressParser;
import proglab.utils.parsers.CoordinatesParser;
import proglab.utils.parsers.LocationParser;
import proglab.utils.parsers.OrganizationTemplateParser;
import proglab.view.View;
import proglab.view.inputrequests.InputRequestFactory;

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
