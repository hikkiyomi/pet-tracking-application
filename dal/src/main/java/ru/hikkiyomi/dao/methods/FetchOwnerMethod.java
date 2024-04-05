package ru.hikkiyomi.dao.methods;

import org.hibernate.Session;
import ru.hikkiyomi.model.Owner;

import java.util.List;

public class FetchOwnerMethod extends BaseMethod<List<Owner>> {
    @Override
    public List<Owner> action(Session session) {
        return (List<Owner>)session.createQuery("FROM Owner").list();
    }
}
