package ru.hikkiyomi.dao.methods;

import org.hibernate.Session;

public class UpdateMethod<T> extends BaseMethod<Void> {
    T content;

    public UpdateMethod(T obj) {
        this.content = obj;
    }

    @Override
    public Void action(Session session) {
        session.update(content);

        return null;
    }
}
