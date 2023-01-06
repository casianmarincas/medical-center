package med.persistence.repository;

import med.persistence.hibernate.HibernateUtils;
import med.model.Person;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class PersonRepo {

    public synchronized Person add(Person person) {
        Session session = HibernateUtils.getSessionFactory().openSession();
        try (session) {
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                session.save(person);
                tx.commit();
                return person;
            } catch (RuntimeException ex) {
                System.err.println("Error on saving " + ex);
                if (tx != null)
                    tx.rollback();
            }
        }
        session.close();
        return null;
    }

    public synchronized List<Person> getAll() {
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                String entityName = Person.class.getName();
                List<Person> personList = session.createQuery(" from " + entityName + " C", Person.class).list();
                tx.commit();
                return personList;
            } catch (RuntimeException ex) {
                System.err.println("Eroare la select " + ex);
                if (tx != null)
                    tx.rollback();
                return null;
            }
        }
    }
}
