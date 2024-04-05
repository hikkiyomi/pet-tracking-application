package ru.hikkiyomi.dao.methods;

import org.hibernate.Session;
import org.hibernate.Transaction;
import ru.hikkiyomi.utils.HibernateSessionFactory;

public abstract class BaseMethod<T> {
    public T execute() {
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();

        T result = action(session);

        transaction.commit();
        session.close();

        return result;
    }

    public abstract T action(Session session);
}
