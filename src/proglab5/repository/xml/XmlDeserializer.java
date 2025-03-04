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
import proglab5.domain.OrganizationTemplate;
import proglab5.exceptions.DataParserException;
import proglab5.exceptions.RepositoryDataCorruptedException;
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
        final Map<String, Object> orgData = new HashMap<>();
        final Map<String, Object> orgTemplateData = new HashMap<>();

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
                        case OrganizationParser.ID:
                            deserializeSimpleField(localName, xmlReader, orgData, OrganizationParser::parseId);
                            break;

                        case OrganizationTemplateParser.NAME:
                            deserializeSimpleField(localName, xmlReader,
                                    orgTemplateData, OrganizationTemplateParser::parseName);
                            break;

                        case OrganizationTemplateParser.COORDINATES:
                            deserializeCoordinates(xmlReader, orgTemplateData);
                            break;

                        case OrganizationParser.CREATION_DATE:
                            deserializeSimpleField(localName, xmlReader,
                                    orgData, OrganizationParser::parseCreationDate);
                            break;

                        case OrganizationTemplateParser.ANNUAL_TURNOVER:
                            deserializeSimpleField(localName, xmlReader,
                                    orgTemplateData, OrganizationTemplateParser::parseAnnualTurnover);
                            break;

                        case OrganizationTemplateParser.FULL_NAME:
                            deserializeSimpleField(localName, xmlReader,
                                    orgTemplateData, OrganizationTemplateParser::parseFullName);
                            break;

                        case OrganizationTemplateParser.EMPLOYEES_COUNT:
                            deserializeSimpleField(localName, xmlReader,
                                    orgTemplateData, OrganizationTemplateParser::parseEmployeesCount);
                            break;

                        case OrganizationTemplateParser.TYPE:
                            deserializeSimpleField(localName, xmlReader,
                                    orgTemplateData, OrganizationTemplateParser::parseType);
                            break;

                        case OrganizationTemplateParser.OFFICIAL_ADDRESS:
                            deserializeOfficialAddress(xmlReader, orgTemplateData);
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

        OrganizationTemplate template = OrganizationTemplateParser.parse(orgTemplateData);
        orgData.put(OrganizationParser.TEMPLATE, template);

        return OrganizationParser.parse(orgData);
    }

    private static <T> void deserializeSimpleField(String fieldName, XMLStreamReader xmlReader,
            Map<String, Object> storage, FieldDataHandler<T> handler)
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
                    if (xmlReader.getLocalName().equals(fieldName)) {
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

        storage.put(fieldName, handler.handle(fieldString));
    }

    private static void deserializeCoordinates(XMLStreamReader xmlReader, Map<String, Object> storage)
            throws RepositoryDataCorruptedException, DataParserException, XMLStreamException {
        final Map<String, Object> coordsData = new HashMap<>();

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
                        case CoordinatesParser.X:
                            deserializeSimpleField(localName, xmlReader, coordsData, CoordinatesParser::parseX);
                            break;

                        case CoordinatesParser.Y:
                            deserializeSimpleField(localName, xmlReader, coordsData, CoordinatesParser::parseY);
                            break;

                        default:
                            throw new RepositoryDataCorruptedException();
                    }

                    break;

                case XMLEvent.END_ELEMENT:
                    if (xmlReader.getLocalName().equals(OrganizationTemplateParser.COORDINATES)) {
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

        storage.put(OrganizationTemplateParser.COORDINATES, CoordinatesParser.parse(coordsData));
    }

    private static void deserializeOfficialAddress(XMLStreamReader xmlReader, Map<String, Object> storage)
            throws RepositoryDataCorruptedException, DataParserException, XMLStreamException {
        final Map<String, Object> addrData = new HashMap<>();

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
                        case AddressParser.STREET:
                            deserializeSimpleField(localName, xmlReader, addrData, AddressParser::parseStreet);
                            break;

                        case AddressParser.TOWN:
                            deserializeTown(xmlReader, addrData);
                            break;

                        default:
                            throw new RepositoryDataCorruptedException();
                    }

                    break;

                case XMLEvent.END_ELEMENT:
                    if (xmlReader.getLocalName().equals(OrganizationTemplateParser.OFFICIAL_ADDRESS)) {
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

        storage.put(OrganizationTemplateParser.OFFICIAL_ADDRESS, AddressParser.parse(addrData));
    }

    private static void deserializeTown(XMLStreamReader xmlReader, Map<String, Object> storage)
            throws RepositoryDataCorruptedException, DataParserException, XMLStreamException {
        final Map<String, Object> locData = new HashMap<>();

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
                        case LocationParser.X:
                            deserializeSimpleField(localName, xmlReader, locData, LocationParser::parseX);
                            break;

                        case LocationParser.Y:
                            deserializeSimpleField(localName, xmlReader, locData, LocationParser::parseY);
                            break;

                        case LocationParser.Z:
                            deserializeSimpleField(localName, xmlReader, locData, LocationParser::parseZ);
                            break;

                        default:
                            throw new RepositoryDataCorruptedException();
                    }

                    break;

                case XMLEvent.END_ELEMENT:
                    if (xmlReader.getLocalName().equals(AddressParser.TOWN)) {
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

        storage.put(AddressParser.TOWN, LocationParser.parse(locData));
    }

    @FunctionalInterface
    private interface FieldDataHandler<T> {
        T handle(String field) throws DataParserException;
    }
}
