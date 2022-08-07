package data.dao.hibernate;

import data.dao.DAO;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.sql.SQLException;

public abstract class HibernateDAO<T> implements DAO<T> {

    protected final SessionFactory sf;

    public HibernateDAO(SessionFactory sf) {
        this.sf = sf;
    }

    @Override
    public void save(T object) throws SQLException {
        try (Session session = sf.openSession()) {
            Transaction transaction = session.getTransaction();
            transaction.begin();
            session.save(object);
            transaction.commit();
        }
    }

    @Override
    public void clear() throws SQLException {
        try (Session session = sf.openSession()) {
            Transaction transaction = session.getTransaction();
            transaction.begin();
            for (T object : getAll()) {
                session.delete(object);
            }
            transaction.commit();
        }
    }
}
