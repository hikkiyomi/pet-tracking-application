package ru.hikkiyomi.dao.methods;

import org.hibernate.Session;
import ru.hikkiyomi.model.Kitten;
import ru.hikkiyomi.model.Owner;

import java.util.List;

public class FetchKittenMethod extends BaseMethod<List<Kitten>> {
    @Override
    public List<Kitten> action(Session session) {
        return (List<Kitten>)session.createQuery("FROM Kitten").list();
    }
}
