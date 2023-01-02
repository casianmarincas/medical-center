package med.persistence.repository;

import med.model.Appointment;
import med.persistence.hibernate.HibernateUtils;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.time.LocalDateTime;

public class AppointmentRepo {

    public Appointment add(Appointment appointment) {
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                appointment.setAppointmentDateTime(LocalDateTime.now());
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
