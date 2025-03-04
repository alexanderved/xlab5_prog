package proglab5.utils.validators;

import java.util.Collection;
import java.util.HashSet;
import proglab5.domain.Organization;

public final class OrganizationCollectionValidator {
    public static boolean validate(Collection<Organization> collection) {
        HashSet<Integer> ids = new HashSet<>();

        for (Organization org : collection) {
            if (!OrganizationValidator.validate(org)) {
                return false;
            }

            ids.add(org.getId());
        }

        return collection.size() == ids.size();
    }
}
