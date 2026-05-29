import model.publishing.Autor;
import model.publishing.Ksiazka;
import model.university.Student;
import model.university.Kurs;
import model.university.Zapis;
import model.pizzeria.Pizza;
import model.transport.Autobus;
import model.transport.Ciezarowka;
import model.transport.Pojazd;
import util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.time.LocalDate;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        System.out.println("=== MP4 demo: 4 pakiety, Hibernate + H2 ===");

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {

            demoPublishing(session);
            demoUniversity(session);
            demoPizzeria(session);
            demoTransport(session);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            HibernateUtil.shutdown();
        }

        System.out.println("=== koniec demo ===");
    }

    private static void demoPublishing(Session session) {
        System.out.println("\n--- [1] publishing: asocjacja zwykla (Autor 1..N Ksiazka) ---");

        Transaction tx = session.beginTransaction();
        Autor mickiewicz = Autor.createAutor("Adam", "Mickiewicz", LocalDate.of(1798, 12, 24));
        Ksiazka.createKsiazka("Pan Tadeusz", 380, 1834, mickiewicz);
        Ksiazka.createKsiazka("Dziady", 220, 1832, mickiewicz);

        Autor lem = Autor.createAutor("Stanislaw", "Lem", LocalDate.of(1921, 9, 13));
        Ksiazka.createKsiazka("Solaris", 280, 1961, lem);

        session.persist(mickiewicz);
        session.persist(lem);
        tx.commit();

        session.clear();

        System.out.println(">>> JOIN");
        List<Autor> autorzy = session.createQuery("select distinct a from Autor a left join fetch a.ksiazki", Autor.class).getResultList();
        for (Autor a : autorzy) {
            System.out.println("  " + a + " -> ksiazek: " + a.getKsiazki().size());
            a.getKsiazki().forEach(k -> System.out.println("     * " + k.getTytul() + " (" + k.getRokWydania() + ")"));
        }
    }

    private static void demoUniversity(Session session) {
        System.out.println("\n--- [2] university: asocjacja z atrybutem (Student-Zapis-Kurs) ---");

        Transaction tx = session.beginTransaction();
        Student s1 = Student.createStudent("123456", "Anna", "Kowalska");
        Student s2 = Student.createStudent("123457", "Jan", "Nowak");
        Kurs mas = Kurs.createKurs("Modelowanie Aplikacji Systemowych", "MAS01", 6);
        Kurs bd  = Kurs.createKurs("Bazy Danych", "BD02", 5);

        Zapis z1 = Zapis.createZapis(s1, mas, LocalDate.of(2026, 2, 20), 4.5, 90);
        Zapis z2 = Zapis.createZapis(s1, bd,  LocalDate.of(2026, 2, 20), 5.0, 100);
        Zapis z3 = Zapis.createZapis(s2, mas, LocalDate.of(2026, 2, 21), 3.5, 70);

        session.persist(mas);
        session.persist(bd);
        session.persist(s1);
        session.persist(s2);
        session.persist(z1);
        session.persist(z2);
        session.persist(z3);
        tx.commit();
        System.out.println("persist OK");

        session.clear();

        System.out.println(">>> JOIN FETCH Student + Zapisy + Kurs (1 zapytanie):");
        List<Student> studenci = session.createQuery(
                "select distinct s from Student s " +
                "left join fetch s.zapisy z " +
                "left join fetch z.kurs", Student.class
        ).getResultList();
        for (Student s : studenci) {
            System.out.println("  " + s);
            s.getZapisy().forEach(z -> System.out.println("     -> " + z));
        }
    }

    private static void demoPizzeria(Session session) {
        System.out.println("\n--- [3] pizzeria: kompozycja (Pizza <>- Skladnik) ---");

        Transaction tx = session.beginTransaction();
        Pizza margherita = Pizza.createPizza("Margherita", 28.00, 32);
        margherita.dodajSkladnik("Sos pomidorowy", 80.0, true);
        margherita.dodajSkladnik("Mozzarella", 120.0, true);
        margherita.dodajSkladnik("Bazylia", 5.0, true);

        Pizza pepperoni = Pizza.createPizza("Pepperoni", 34.00, 40);
        pepperoni.dodajSkladnik("Sos pomidorowy", 80.0, true);
        pepperoni.dodajSkladnik("Mozzarella", 120.0, true);
        pepperoni.dodajSkladnik("Pepperoni", 90.0, false);

        session.persist(margherita);
        session.persist(pepperoni);
        tx.commit();
        System.out.println("persist OK");

        Long pepperoniId = pepperoni.getId();

        session.clear();
        System.out.println(">>> JOIN FETCH Pizza + Skladniki:");
        List<Pizza> pizze = session.createQuery(
                "select distinct p from Pizza p left join fetch p.skladniki", Pizza.class
        ).getResultList();
        pizze.forEach(p -> {
            System.out.println("  " + p + " skladniki=" + p.getSkladniki().size());
            p.getSkladniki().forEach(sk -> System.out.println("     * " + sk));
        });

        System.out.println(">>> kasacja pizzy -> skladniki znikaja (orphanRemoval + cascade ALL):");
        tx = session.beginTransaction();
        Pizza toDelete = session.find(Pizza.class, pepperoniId);
        session.remove(toDelete);
        tx.commit();

        Long countSkladnikow = session.createQuery(
                "select count(s) from Skladnik s where s.pizza.id = :pid", Long.class
        ).setParameter("pid", pepperoniId).getSingleResult();
        System.out.println("skladniki po DELETE pizzy id=" + pepperoniId + ": " + countSkladnikow + " (powinno byc 0)");
    }
    private static void demoTransport(Session session) {
        System.out.println("\n--- [4] transport: dziedziczenie disjoint JOINED (Pojazd / Autobus / Ciezarowka) ---");

        Transaction tx = session.beginTransaction();
        Autobus a1 = Autobus.createAutobus("WAW1234", 2022, "Solaris", 60, false);
        Autobus a2 = Autobus.createAutobus("WAW5678", 2024, "MAN", 80, true);
        Ciezarowka c1 = Ciezarowka.createCiezarowka("KR99001", 2020, "Volvo", 18000, 3);
        Ciezarowka c2 = Ciezarowka.createCiezarowka("KR99002", 2023, "Scania", 30000, 4);

        session.persist(a1);
        session.persist(a2);
        session.persist(c1);
        session.persist(c2);
        tx.commit();
        System.out.println("persist OK");

        session.clear();

        System.out.println(">>> polimorficzne SELECT po klasie bazowej Pojazd:");
        List<Pojazd> wszystkie = session.createQuery(
                "from Pojazd order by id", Pojazd.class
        ).getResultList();
        for (Pojazd p : wszystkie) {
            System.out.println("  " + p.getClass().getSimpleName() + " " + p
                    + " ubezp/rok=" + String.format("%.2f", p.obliczRocznyKosztUbezpieczenia()));
        }

        System.out.println(">>> SELECT tylko Autobus (typ konkretny):");
        List<Autobus> tylkoAutobusy = session.createQuery("from Autobus", Autobus.class).getResultList();
        tylkoAutobusy.forEach(b -> System.out.println("  " + b));
    }
}
