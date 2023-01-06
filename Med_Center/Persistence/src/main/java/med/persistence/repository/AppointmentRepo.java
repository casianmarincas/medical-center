package med.persistence.repository;

import med.model.Appointment;
import med.model.Person;
import med.persistence.hibernate.HibernateUtils;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.time.LocalDateTime;
import java.util.List;

public class AppointmentRepo {

    public synchronized Appointment add(Appointment appointment) {
        Session session = HibernateUtils.getSessionFactory().openSession();
        try (session) {
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
        session.close();
        return null;
    }

    public synchronized List<Appointment> getAll() {
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                String entityName = Person.class.getName();
                List<Appointment> appointmentList = session.createQuery(" from " + entityName + " C", Appointment.class).list();
                tx.commit();
                return appointmentList;
            } catch (RuntimeException ex) {
                System.err.println("Eroare la select " + ex);
                if (tx != null)
                    tx.rollback();
                return null;
            }
        }
    }

}
