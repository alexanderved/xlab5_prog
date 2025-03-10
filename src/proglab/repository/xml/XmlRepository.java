package proglab.repository.xml;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashSet;

import proglab.domain.Organization;
import proglab.exceptions.RepositoryAccessDeniedException;
import proglab.exceptions.RepositoryDataCorruptedException;
import proglab.repository.Repository;

/**
 * Репозиторий, хранящий данные организаций в формате XML.
 */
public class XmlRepository extends Repository {
    private String filepath;

    /**
     * @param filepath Путь до файла, в который будут сохранены данные организаций
     */
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
            throw new RepositoryAccessDeniedException(e);
        }
    }

    @Override
    public void save(HashSet<Organization> collection)
        throws RepositoryAccessDeniedException
    {
        try (PrintWriter writer = new PrintWriter(filepath);) {
            XmlSerializer.serializeOrganizationCollection(collection, writer);
        } catch (IOException e) {
            throw new RepositoryAccessDeniedException(e);
        }
    }
}
