package med.persistence.repository;

import med.model.Location;

import med.persistence.hibernate.HibernateUtils;

import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class LocationRepo {

    public Location add(Location location) {
        Session session = HibernateUtils.getSessionFactory().openSession();
        try (session) {
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
        session.close();
        return null;
    }

    public List<Location> getAll() {
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                String entityName = Location.class.getName();
                List<Location> locationList = session.createQuery(" from " + entityName + " C", Location.class).list();
                tx.commit();
                return locationList;
            } catch (RuntimeException ex) {
                System.err.println("Eroare la select " + ex);
                if (tx != null)
                    tx.rollback();
                return null;
            }
        }
    }

}
