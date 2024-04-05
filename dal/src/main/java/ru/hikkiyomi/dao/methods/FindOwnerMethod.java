package ru.hikkiyomi.dao.methods;

import org.hibernate.Session;
import ru.hikkiyomi.model.Owner;

public class FindOwnerMethod extends BaseMethod<Owner> {
    private final int id;

    public FindOwnerMethod(int id) {
        this.id = id;
    }

    @Override
    public Owner action(Session session) {
        return session.get(Owner.class, id);
    }
}
