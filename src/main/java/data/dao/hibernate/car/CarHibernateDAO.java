package data.dao.hibernate.car;

import data.dao.hibernate.HibernateDAO;
import data.entity.CarEntity;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.sql.SQLException;
import java.util.List;

@Component
public class CarHibernateDAO extends HibernateDAO<CarEntity> {
    public CarHibernateDAO(@Autowired SessionFactory sf) {
        super(sf);
    }

    @Override
    public CarEntity findById(Long id) throws SQLException {
        try (Session session = sf.openSession()) {
            return session.get(CarEntity.class, id);
        }
    }

    @Override
    public List<CarEntity> getAll() throws SQLException {
        try (Session session = sf.openSession()) {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<CarEntity> criteriaQuery = builder.createQuery(CarEntity.class);
            criteriaQuery.from(CarEntity.class);

            return session.createQuery(criteriaQuery).getResultList();
        }
    }
}
