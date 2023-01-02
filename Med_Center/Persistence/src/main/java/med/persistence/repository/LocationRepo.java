package med.persistence.repository;

import med.model.Location;
import med.persistence.hibernate.HibernateUtils;

import org.hibernate.Session;
import org.hibernate.Transaction;

public class LocationRepo {

    public Location add(Location location) {
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                session.save(location);
                tx.commit();
                return location;
            } catch (RuntimeException ex) {
                System.err.println("Error on saving " + ex);
                if (tx != null)
                    tx.rollback();
            }
        }
        return null;
    }

}
