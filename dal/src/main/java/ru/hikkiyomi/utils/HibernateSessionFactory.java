package ru.hikkiyomi.utils;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import ru.hikkiyomi.model.Kitten;
import ru.hikkiyomi.model.Owner;

public class HibernateSessionFactory {
    private static SessionFactory sessionFactory;

    private HibernateSessionFactory() {}

    public static SessionFactory getSessionFactory() {
        if (sessionFactory != null) {
            return sessionFactory;
        }

        try {
            Configuration config = new Configuration().configure();

            config.addAnnotatedClass(Kitten.class);
            config.addAnnotatedClass(Owner.class);

            StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder()
                    .applySettings(config.getProperties());

            sessionFactory = config.buildSessionFactory(builder.build());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return sessionFactory;
    }
}
