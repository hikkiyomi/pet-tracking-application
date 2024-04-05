package ru.hikkiyomi.dao.methods;

import org.hibernate.Session;
import ru.hikkiyomi.model.Kitten;

public class FindKittenMethod extends BaseMethod<Kitten> {
    private final int id;

    public FindKittenMethod(int id) {
        this.id = id;
    }

    @Override
    public Kitten action(Session session) {
        return session.get(Kitten.class, id);
    }
}
