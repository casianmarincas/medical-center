package med.persistence.repository;


import med.model.TreatmentLocation;
import med.persistence.hibernate.HibernateUtils;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class TreatmentLocationRepo {

    public TreatmentLocation add(TreatmentLocation treatment_location) {
        Session session = HibernateUtils.getSessionFactory().openSession();
        try (session) {
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                session.save(treatment_location);
                tx.commit();
                return treatment_location;
            } catch (RuntimeException ex) {
                System.err.println("Error on saving " + ex);
                if (tx != null)
                    tx.rollback();
            }
        }
        session.close();
        return null;
    }

    public List<TreatmentLocation> getAll() {
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                String entityName = TreatmentLocation.class.getName();
                List<TreatmentLocation> treatmentLocationList = session.createQuery(" from " + entityName + " C", TreatmentLocation.class).list();
                tx.commit();
                return treatmentLocationList;
            } catch (RuntimeException ex) {
                System.err.println("Eroare la select " + ex);
                if (tx != null)
                    tx.rollback();
                return null;
            }
        }
    }

}
