package persistence.repository;

import model.Person;
import org.hibernate.Session;
import org.hibernate.Transaction;
import persistence.hibernate.HibernateUtils;

public class PersonRepo {

    public Person add(Person person) {
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
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
        return null;
    }
}
