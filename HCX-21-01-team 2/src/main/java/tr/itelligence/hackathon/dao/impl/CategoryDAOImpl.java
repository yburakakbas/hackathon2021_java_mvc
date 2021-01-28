package tr.itelligence.hackathon.dao.impl;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import tr.itelligence.hackathon.dao.CategoryDAO;
import tr.itelligence.hackathon.entity.Category;

import java.util.List;

@Transactional
public class CategoryDAOImpl implements CategoryDAO {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public void save(Category category) {
        this.sessionFactory.getCurrentSession().save(category);
    }

    @Override
    public List<Category> findAll() {
        String sql = "Select new " + Category.class.getName() //
                + "(c.id, c.description) "//
                + " from " + Category.class.getName() + " c ";

        Session session = this.sessionFactory.getCurrentSession();
        Query query = session.createQuery(sql);
        return query.list();
    }
}
