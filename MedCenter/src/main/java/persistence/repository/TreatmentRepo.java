package persistence.repository;

import model.Treatment;
import org.hibernate.Session;
import org.hibernate.Transaction;
import persistence.hibernate.HibernateUtils;

public class TreatmentRepo {

    public Treatment add(Treatment treatment) {
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                session.save(treatment);
                tx.commit();
                return treatment;
            } catch (RuntimeException ex) {
                System.err.println("Error on saving " + ex);
                if (tx != null)
                    tx.rollback();
            }
        }
        return null;
    }
}
