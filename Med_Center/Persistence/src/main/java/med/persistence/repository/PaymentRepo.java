package med.persistence.repository;

import med.model.Payment;
import med.model.Person;
import med.persistence.hibernate.HibernateUtils;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class PaymentRepo {

    public synchronized Payment add(Payment payment) {
        Session session = HibernateUtils.getSessionFactory().openSession();
        try (session) {
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                session.save(payment);
                tx.commit();
                return payment;
            } catch (RuntimeException ex) {
                System.err.println("Error on saving " + ex);
                if (tx != null)
                    tx.rollback();
            }
        }
        session.close();
        return null;
    }

    public synchronized List<Payment> getAll() {
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                String entityName = Person.class.getName();
                List<Payment> paymentList = session.createQuery(" from " + entityName + " C", Payment.class).list();
                tx.commit();
                return paymentList;
            } catch (RuntimeException ex) {
                System.err.println("Eroare la select " + ex);
                if (tx != null)
                    tx.rollback();
                return null;
            }
        }
    }
}
