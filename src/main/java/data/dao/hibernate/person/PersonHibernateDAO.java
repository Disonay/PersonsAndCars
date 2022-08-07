package data.dao.hibernate.person;

import data.dao.hibernate.HibernateDAO;
import data.entity.PersonEntity;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.sql.SQLException;
import java.util.List;

@Component
public class PersonHibernateDAO extends HibernateDAO<PersonEntity> {

    public PersonHibernateDAO(@Autowired SessionFactory sf) {
        super(sf);
    }

    @Override
    public PersonEntity findById(Long id) throws SQLException {
        try (Session session = sf.openSession()) {
            return session.get(PersonEntity.class, id);
        }
    }

    @Override
    public List<PersonEntity> getAll() throws SQLException {
        try (Session session = sf.openSession()) {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<PersonEntity> criteriaQuery = builder.createQuery(PersonEntity.class);
            criteriaQuery.from(PersonEntity.class);

            return session.createQuery(criteriaQuery).getResultList();
        }
    }
}
