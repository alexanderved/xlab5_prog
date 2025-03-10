package proglab.repository.xml;

import java.io.IOException;
import java.io.Writer;
import java.util.Collection;

import proglab.domain.Address;
import proglab.domain.Coordinates;
import proglab.domain.Location;
import proglab.domain.Organization;

final class XmlSerializer {
    private static final String TAB = "    ";

    static void serializeOrganizationCollection(
        Collection<Organization> collection, Writer writer) throws IOException
    {
        writer.append("<collection>\n");
        for (Organization org : collection) {
            serializeOrganiztion(org, writer, 1);
        }
        writer.append("</collection>\n");
    }

    private static void serializeOrganiztion(
        Organization org, Writer writer, int level) throws IOException
    {
        writer.append(getPadding(level) + "<organization>\n");
        writer.append(getPadding(level + 1) + "<id>" + org.getId() + "</id>\n");
        writer.append(getPadding(level + 1) + "<name>" + org.getName() + "</name>\n");
        serializeCoordinates(org.getCoordinates(), writer, level + 1);
        writer.append(getPadding(level + 1) + "<creationDate>"
            + org.getCreationDate() + "</creationDate>\n");
        
        if (org.getAnnualTurnover() != null) {
            writer.append(getPadding(level + 1) + "<annualTurnover>"
                + org.getAnnualTurnover() + "</annualTurnover>\n");
        }
        if (org.getFullName() != null) {
            writer.append(getPadding(level + 1) + "<fullName>"
                + org.getFullName() + "</fullName>\n");
        }
        
        writer.append(getPadding(level + 1) + "<employeesCount>"
            + org.getEmployeesCount() + "</employeesCount>\n");
        writer.append(getPadding(level + 1) + "<type>" + org.getType() + "</type>\n");

        if (org.getOfficialAddress() != null) {
            serializeAddress(org.getOfficialAddress(), writer, level + 1);
        }

        writer.append(getPadding(level) + "</organization>\n");
    }

    private static void serializeCoordinates(
        Coordinates coords, Writer writer, int level) throws IOException
    {
        writer.append(getPadding(level) + "<coordinates>\n");
        writer.append(getPadding(level + 1) + "<x>" + coords.getX() + "</x>\n");
        writer.append(getPadding(level + 1) + "<y>" + coords.getY() + "</y>\n");
        writer.append(getPadding(level) + "</coordinates>\n");
    }

    private static void serializeAddress(
        Address addr, Writer writer, int level) throws IOException
    {
        writer.append(getPadding(level) + "<officialAddress>\n");
        writer.append(getPadding(level + 1) + "<street>"
            + addr.getStreet() + "</street>\n");
        serializeLocation(addr.getTown(), writer, level + 1);
        writer.append(getPadding(level) + "</officialAddress>\n");
    }

    private static void serializeLocation(
        Location loc, Writer writer, int level) throws IOException
    {
        writer.append(getPadding(level) + "<town>\n");
        writer.append(getPadding(level + 1) + "<x>" + loc.getX() + "</x>\n");
        writer.append(getPadding(level + 1) + "<y>" + loc.getY() + "</y>\n");
        writer.append(getPadding(level + 1) + "<z>" + loc.getZ() + "</z>\n");
        writer.append(getPadding(level) + "</town>\n");
    }

    private static String getPadding(int level) {
        return TAB.repeat(level);
    }
}
