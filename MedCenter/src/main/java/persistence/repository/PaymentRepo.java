package persistence.repository;

import model.Appointment;
import model.Payment;
import org.hibernate.Session;
import org.hibernate.Transaction;
import persistence.hibernate.HibernateUtils;

public class PaymentRepo {

    public Payment add(Payment payment) {
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
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
        return null;
    }
}
