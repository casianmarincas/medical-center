package med.persistence.repository;

import med.model.TreatmentLocation;
import med.persistence.hibernate.HibernateUtils;
import med.model.Treatment;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class TreatmentRepo {

    public  synchronized Treatment add(Treatment treatment) {
        Session session = HibernateUtils.getSessionFactory().openSession();
        try (session) {
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
        session.close();
        return null;
    }

    public List<Treatment> getAll() {
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                String entityName = Treatment.class.getName();
                List<Treatment> treatmentList = session.createQuery(" from " + entityName + " C", Treatment.class).list();
                tx.commit();
                return treatmentList;
            } catch (RuntimeException ex) {
                System.err.println("Eroare la select " + ex);
                if (tx != null)
                    tx.rollback();
                return null;
            }
        }
    }
}
