package proglab5.repository;

import java.util.HashSet;
import proglab5.domain.Organization;
import proglab5.exceptions.RepositoryAccessDeniedException;
import proglab5.exceptions.RepositoryDataCorruptedException;

public abstract class Repository {
    public abstract HashSet<Organization> load()
        throws RepositoryAccessDeniedException, RepositoryDataCorruptedException;
    public abstract void save(HashSet<Organization> collection)
            throws RepositoryAccessDeniedException;
}