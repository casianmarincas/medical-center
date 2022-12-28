package persistence.repository;

import model.Appointment;
import org.hibernate.Session;
import org.hibernate.Transaction;
import persistence.hibernate.HibernateUtils;

public class AppointmentRepo {

    public Appointment add(Appointment appointment) {
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                session.save(appointment);
                tx.commit();
                return appointment;
            } catch (RuntimeException ex) {
                System.err.println("Error on saving " + ex);
                if (tx != null)
                    tx.rollback();
            }
        }
        return null;
    }

}
