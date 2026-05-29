package util;

import model.publishing.Autor;
import model.publishing.Ksiazka;
import model.university.Student;
import model.university.Kurs;
import model.university.Zapis;
import model.pizzeria.Pizza;
import model.pizzeria.Skladnik;
import model.transport.Pojazd;
import model.transport.Autobus;
import model.transport.Ciezarowka;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

public class HibernateUtil {

    private static final SessionFactory sessionFactory = buildSessionFactory();

    private static SessionFactory buildSessionFactory() {
        try {
            System.setProperty("org.jboss.logging.provider", "slf4j");

            Configuration configuration = new Configuration();

            configuration.addAnnotatedClass(Autor.class);
            configuration.addAnnotatedClass(Ksiazka.class);

            configuration.addAnnotatedClass(Student.class);
            configuration.addAnnotatedClass(Kurs.class);
            configuration.addAnnotatedClass(Zapis.class);

            // === pizzeria (kompozycja) ===
            configuration.addAnnotatedClass(Pizza.class);
            configuration.addAnnotatedClass(Skladnik.class);

            // === transport (dziedziczenie JOINED) ===
            configuration.addAnnotatedClass(Pojazd.class);
            configuration.addAnnotatedClass(Autobus.class);
            configuration.addAnnotatedClass(Ciezarowka.class);

            ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                    .applySettings(configuration.getProperties())
                    .build();

            return configuration.buildSessionFactory(serviceRegistry);
        } catch (Exception e) {
            throw new ExceptionInInitializerError(e);
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public static void shutdown() {
        if (sessionFactory != null && !sessionFactory.isClosed()) {
            sessionFactory.close();
        }
    }
}
