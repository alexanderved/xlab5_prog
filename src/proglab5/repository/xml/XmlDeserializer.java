package proglab5.repository.xml;

import java.io.Reader;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.events.XMLEvent;
import proglab5.domain.Organization;
import proglab5.exceptions.DataParserException;
import proglab5.exceptions.RepositoryDataCorruptedException;
import proglab5.utils.fields.AddressFields;
import proglab5.utils.fields.CoordinatesFields;
import proglab5.utils.fields.Fields;
import proglab5.utils.fields.LocationFields;
import proglab5.utils.fields.OrganizationFields;
import proglab5.utils.parsers.AddressParser;
import proglab5.utils.parsers.CoordinatesParser;
import proglab5.utils.parsers.LocationParser;
import proglab5.utils.parsers.OrganizationParser;
import proglab5.utils.parsers.OrganizationTemplateParser;

final class XmlDeserializer {
    private final static String COLLECTION = "collection";
    private final static String ORGANIZATION = "organization";

    static HashSet<Organization> deserializeOrganizationCollection(
            Reader reader) throws RepositoryDataCorruptedException {
        HashSet<Organization> collection = new HashSet<>();

        XMLInputFactory factory = XMLInputFactory.newFactory();
        XMLStreamReader xmlReader = null;
        try {
            xmlReader = factory.createXMLStreamReader(reader);

            boolean isBreak = false;
            while (xmlReader.hasNext()) {
                int event = xmlReader.next();
                switch (event) {
                    case XMLEvent.START_DOCUMENT:
                        continue;

                    case XMLEvent.CHARACTERS:
                        if (xmlReader.getText().isBlank()) {
                            continue;
                        }
                        throw new RepositoryDataCorruptedException();

                    case XMLEvent.END_ELEMENT:
                        if (xmlReader.getLocalName().equals(COLLECTION)) {
                            isBreak = true;
                            break;
                        }
                        throw new RepositoryDataCorruptedException();

                    case XMLEvent.START_ELEMENT:
                        switch (xmlReader.getLocalName()) {
                            case COLLECTION:
                                continue;

                            case ORGANIZATION:
                                collection.add(deserializeOrganization(xmlReader));
                                break;

                            default:
                                throw new RepositoryDataCorruptedException();
                        }

                        break;

                    default:
                        break;
                }

                if (isBreak) {
                    break;
                }
            }

            if (!isBreak) {
                throw new RepositoryDataCorruptedException();
            }
        } catch (XMLStreamException | DataParserException e) {
            throw new RepositoryDataCorruptedException(e.getMessage());
        } finally {
            try {
                xmlReader.close();
            } catch (XMLStreamException e) {
                throw new RepositoryDataCorruptedException(e.getMessage());
            }
        }

        return collection;
    }

    private static Organization deserializeOrganization(XMLStreamReader xmlReader)
            throws RepositoryDataCorruptedException, DataParserException, XMLStreamException {
        final Map<OrganizationFields, Object> orgData = new HashMap<>();

        boolean isBreak = false;
        while (xmlReader.hasNext()) {
            int event = xmlReader.next();
            switch (event) {
                case XMLEvent.CHARACTERS:
                    if (xmlReader.getText().isBlank()) {
                        continue;
                    }
                    throw new RepositoryDataCorruptedException();

                case XMLEvent.END_ELEMENT:
                    if (xmlReader.getLocalName().equals(ORGANIZATION)) {
                        isBreak = true;
                        break;
                    }
                    throw new RepositoryDataCorruptedException();

                case XMLEvent.START_ELEMENT:
                    String localName = xmlReader.getLocalName();
                    switch (localName) {
                        case "id":
                            deserializeSimpleField(xmlReader, orgData, OrganizationFields.ID,
                                    OrganizationParser::parseId);
                            break;

                        case "name":
                            deserializeSimpleField(xmlReader, orgData, OrganizationFields.NAME,
                                    OrganizationTemplateParser::parseName);
                            break;

                        case "coordinates":
                            deserializeCoordinates(xmlReader, orgData);
                            break;

                        case "creationDate":
                            deserializeSimpleField(xmlReader, orgData, OrganizationFields.CREATION_DATE,
                                    OrganizationParser::parseCreationDate);
                            break;

                        case "annualTurnover":
                            deserializeSimpleField(xmlReader, orgData, OrganizationFields.ANNUAL_TURNOVER,
                                    OrganizationTemplateParser::parseAnnualTurnover);
                            break;

                        case "fullName":
                            deserializeSimpleField(xmlReader, orgData, OrganizationFields.FULL_NAME,
                                    OrganizationTemplateParser::parseFullName);
                            break;

                        case "employeesCount":
                            deserializeSimpleField(xmlReader, orgData, OrganizationFields.EMPLOYEES_COUNT,
                                    OrganizationTemplateParser::parseEmployeesCount);
                            break;

                        case "type":
                            deserializeSimpleField(xmlReader, orgData, OrganizationFields.TYPE,
                                    OrganizationTemplateParser::parseType);
                            break;

                        case "officialAddress":
                            deserializeOfficialAddress(xmlReader, orgData);
                            break;

                        default:
                            throw new RepositoryDataCorruptedException();
                    }

                    break;

                default:
                    break;
            }

            if (isBreak) {
                break;
            }
        }

        return OrganizationParser.parse(orgData);
    }

    private static <E extends Fields, T> void deserializeSimpleField(XMLStreamReader xmlReader,
            Map<E, Object> storage, E field, FieldDataHandler<T> handler)
            throws RepositoryDataCorruptedException, DataParserException, XMLStreamException {
        String fieldString = null;

        boolean isBreak = false;
        while (xmlReader.hasNext()) {
            int event = xmlReader.next();

            switch (event) {
                case XMLEvent.CHARACTERS:
                    fieldString = xmlReader.getText().trim();
                    fieldString = fieldString.isEmpty() ? null : fieldString;

                    break;

                case XMLEvent.END_ELEMENT:
                    if (xmlReader.getLocalName().equals(field.getName())) {
                        isBreak = true;
                        break;
                    }
                    throw new RepositoryDataCorruptedException();

                default:
                    break;
            }

            if (isBreak) {
                break;
            }
        }

        storage.put(field, handler.handle(fieldString));
    }

    private static void deserializeCoordinates(XMLStreamReader xmlReader, Map<OrganizationFields, Object> storage)
            throws RepositoryDataCorruptedException, DataParserException, XMLStreamException {
        final Map<CoordinatesFields, Object> coordsData = new HashMap<>();

        boolean isBreak = false;
        while (xmlReader.hasNext()) {
            int event = xmlReader.next();

            switch (event) {
                case XMLEvent.CHARACTERS:
                    if (xmlReader.getText().isBlank()) {
                        continue;
                    }
                    throw new RepositoryDataCorruptedException();

                case XMLEvent.START_ELEMENT:
                    String localName = xmlReader.getLocalName();
                    switch (localName) {
                        case "x":
                            deserializeSimpleField(xmlReader, coordsData, CoordinatesFields.X,
                                    CoordinatesParser::parseX);
                            break;

                        case "y":
                            deserializeSimpleField(xmlReader, coordsData, CoordinatesFields.Y,
                                    CoordinatesParser::parseY);
                            break;

                        default:
                            throw new RepositoryDataCorruptedException();
                    }

                    break;

                case XMLEvent.END_ELEMENT:
                    if (xmlReader.getLocalName().equals("coordinates")) {
                        isBreak = true;
                        break;
                    }
                    throw new RepositoryDataCorruptedException();

                default:
                    throw new RepositoryDataCorruptedException();
            }

            if (isBreak) {
                break;
            }
        }

        storage.put(OrganizationFields.COORDINATES, CoordinatesParser.parse(coordsData));
    }

    private static void deserializeOfficialAddress(XMLStreamReader xmlReader, Map<OrganizationFields, Object> storage)
            throws RepositoryDataCorruptedException, DataParserException, XMLStreamException {
        final Map<AddressFields, Object> addrData = new HashMap<>();

        boolean isBreak = false;
        while (xmlReader.hasNext()) {
            int event = xmlReader.next();

            switch (event) {
                case XMLEvent.CHARACTERS:
                    if (xmlReader.getText().isBlank()) {
                        continue;
                    }
                    throw new RepositoryDataCorruptedException();

                case XMLEvent.START_ELEMENT:
                    String localName = xmlReader.getLocalName();
                    switch (localName) {
                        case "street":
                            deserializeSimpleField(xmlReader, addrData, AddressFields.STREET,
                                    AddressParser::parseStreet);
                            break;

                        case "town":
                            deserializeTown(xmlReader, addrData);
                            break;

                        default:
                            throw new RepositoryDataCorruptedException();
                    }

                    break;

                case XMLEvent.END_ELEMENT:
                    if (xmlReader.getLocalName().equals("officialAddress")) {
                        isBreak = true;
                        break;
                    }
                    throw new RepositoryDataCorruptedException();

                default:
                    throw new RepositoryDataCorruptedException();
            }

            if (isBreak) {
                break;
            }
        }

        storage.put(OrganizationFields.OFFICIAL_ADDRESS, AddressParser.parse(addrData));
    }

    private static void deserializeTown(XMLStreamReader xmlReader, Map<AddressFields, Object> storage)
            throws RepositoryDataCorruptedException, DataParserException, XMLStreamException {
        final Map<LocationFields, Object> locData = new HashMap<>();

        boolean isBreak = false;
        while (xmlReader.hasNext()) {
            int event = xmlReader.next();

            switch (event) {
                case XMLEvent.CHARACTERS:
                    if (xmlReader.getText().isBlank()) {
                        continue;
                    }
                    throw new RepositoryDataCorruptedException();

                case XMLEvent.START_ELEMENT:
                    String localName = xmlReader.getLocalName();
                    switch (localName) {
                        case "x":
                            deserializeSimpleField(xmlReader, locData, LocationFields.X, LocationParser::parseX);
                            break;

                        case "y":
                            deserializeSimpleField(xmlReader, locData, LocationFields.Y, LocationParser::parseY);
                            break;

                        case "z":
                            deserializeSimpleField(xmlReader, locData, LocationFields.Z, LocationParser::parseZ);
                            break;

                        default:
                            throw new RepositoryDataCorruptedException();
                    }

                    break;

                case XMLEvent.END_ELEMENT:
                    if (xmlReader.getLocalName().equals("town")) {
                        isBreak = true;
                        break;
                    }
                    throw new RepositoryDataCorruptedException();

                default:
                    throw new RepositoryDataCorruptedException();
            }

            if (isBreak) {
                break;
            }
        }

        storage.put(AddressFields.TOWN, LocationParser.parse(locData));
    }

    @FunctionalInterface
    private interface FieldDataHandler<T> {
        T handle(String field) throws DataParserException;
    }

    @FunctionalInterface
    private interface SubfieldDataHandler<E> {
        void handle(String subfield, Map<E, Object> storage) throws DataParserException;
    }
}
