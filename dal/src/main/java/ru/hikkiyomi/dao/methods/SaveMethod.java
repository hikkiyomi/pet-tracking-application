package ru.hikkiyomi.dao.methods;

import org.hibernate.Session;

public class SaveMethod<T> extends BaseMethod<Void> {
    T content;

    public SaveMethod(T obj) {
        this.content = obj;
    }

    @Override
    public Void action(Session session) {
        session.save(content);

        return null;
    }
}
