package proglab5.utils;

import proglab5.domain.OrganizationTemplate;
import proglab5.exceptions.InputDeniedException;
import proglab5.exceptions.InputFailedException;
import proglab5.exceptions.UnexpectedEODException;
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
                .createCompositeDataInputRequest("координаты",
                        OrganizationTemplateParser::parseCoordinates)
                .addFieldRequest(CoordinatesParser.X,
                        inReqFactory.createPrimitiveDataInputRequest("X",
                                CoordinatesParser::parseX))
                .addFieldRequest(CoordinatesParser.Y,
                        inReqFactory.createPrimitiveDataInputRequest("Y",
                                CoordinatesParser::parseY));

        var townReq = inReqFactory
                .createCompositeDataInputRequest("координаты города", AddressParser::parseTown)
                .addFieldRequest(LocationParser.X,
                        inReqFactory.createPrimitiveDataInputRequest("X",
                                LocationParser::parseX))
                .addFieldRequest(LocationParser.Y,
                        inReqFactory.createPrimitiveDataInputRequest("Y",
                                LocationParser::parseY))
                .addFieldRequest(LocationParser.Z,
                        inReqFactory.createPrimitiveDataInputRequest("Z",
                                LocationParser::parseZ));

        var officialAddressReq = inReqFactory
                .createCompositeDataInputRequest("официальный адрес",
                        OrganizationTemplateParser::parseOfficialAddress)
                .addFieldRequest(AddressParser.STREET,
                        inReqFactory.createPrimitiveDataInputRequest("улицу",
                                AddressParser::parseStreet))
                .addFieldRequest(AddressParser.TOWN, townReq);

        var templateReq = inReqFactory
                .createCompositeDataInputRequest("данные организации",
                        OrganizationTemplateParser::parse)
                .addFieldRequest(OrganizationTemplateParser.NAME,
                        inReqFactory.createPrimitiveDataInputRequest("название",
                                OrganizationTemplateParser::parseName))
                .addFieldRequest(OrganizationTemplateParser.COORDINATES, coordinatesReq)
                .addFieldRequest(OrganizationTemplateParser.ANNUAL_TURNOVER,
                        inReqFactory.createPrimitiveDataInputRequest("годовой оборот",
                                OrganizationTemplateParser::parseAnnualTurnover))
                .addFieldRequest(OrganizationTemplateParser.FULL_NAME,
                        inReqFactory.createPrimitiveDataInputRequest("полное название",
                                OrganizationTemplateParser::parseFullName))
                .addFieldRequest(OrganizationTemplateParser.EMPLOYEES_COUNT,
                        inReqFactory.createPrimitiveDataInputRequest("число сотрудников",
                                OrganizationTemplateParser::parseEmployeesCount))
                .addFieldRequest(OrganizationTemplateParser.TYPE,
                        inReqFactory.createPrimitiveDataInputRequest("тип",
                                OrganizationTemplateParser::parseType))
                .addFieldRequest(OrganizationTemplateParser.OFFICIAL_ADDRESS,
                        inReqFactory.createOptionalInputRequest(officialAddressReq));

        try {
            return view.input(templateReq);
        } catch (InputDeniedException | InputFailedException e) {
            view.error("Не удается считать данные организации: " + e.getMessage());

            return null;
        }
    }
}
