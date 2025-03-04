package proglab5.repository.xml;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashSet;

import proglab5.domain.Organization;
import proglab5.exceptions.RepositoryAccessDeniedException;
import proglab5.exceptions.RepositoryDataCorruptedException;
import proglab5.repository.Repository;

public class XmlRepository extends Repository {
    private String filepath;

    public XmlRepository(String filepath) {
        this.filepath = filepath;
    }

    @Override
    public HashSet<Organization> load()
        throws RepositoryAccessDeniedException, RepositoryDataCorruptedException
    {
        try (BufferedReader reader = new BufferedReader(new FileReader(filepath))) {
            return XmlDeserializer.deserializeOrganizationCollection(reader);
        } catch (IOException e) {
            throw new RepositoryAccessDeniedException(e.getMessage());
        }
    }

    @Override
    public void save(HashSet<Organization> collection)
        throws RepositoryAccessDeniedException
    {
        try (PrintWriter writer = new PrintWriter(filepath);) {
            XmlSerializer.serializeOrganizationCollection(collection, writer);
        } catch (IOException e) {
            throw new RepositoryAccessDeniedException(e.getMessage());
        }
    }
}
