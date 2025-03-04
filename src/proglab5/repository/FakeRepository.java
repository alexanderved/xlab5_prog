package proglab5.repository;

import java.util.HashSet;

import proglab5.domain.Organization;
import proglab5.exceptions.RepositoryAccessDeniedException;

public class FakeRepository extends Repository {
    private HashSet<Organization> collection;

    public FakeRepository(HashSet<Organization> collection) {
        this.collection = collection;
    }

    public HashSet<Organization> load() throws RepositoryAccessDeniedException {
        return collection;
    }

    public void save(HashSet<Organization> collection)
            throws RepositoryAccessDeniedException {
        this.collection = collection;
    }
}