package ru.hikkiyomi.dao.methods;

import org.hibernate.Session;

public class DeleteMethod<T> extends BaseMethod<Void> {
    T content;

    public DeleteMethod(T obj) {
        this.content = obj;
    }

    @Override
    public Void action(Session session) {
        session.delete(content);

        return null;
    }
}
