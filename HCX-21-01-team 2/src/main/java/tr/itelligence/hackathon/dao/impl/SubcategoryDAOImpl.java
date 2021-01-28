package tr.itelligence.hackathon.dao.impl;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import tr.itelligence.hackathon.dao.SubcategoryDAO;
import tr.itelligence.hackathon.entity.Subcategory;

import java.util.List;

@Transactional
public class SubcategoryDAOImpl implements SubcategoryDAO {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public void save(Subcategory subcategory) {
        this.sessionFactory.getCurrentSession().save(subcategory);
    }

    @Override
    public List<Subcategory> findAll() {
        String sql = "Select new " + Subcategory.class.getName() //
                + "(c.id, c.description) "//
                + " from " + Subcategory.class.getName() + " c ";

        Session session = this.sessionFactory.getCurrentSession();
        Query query = session.createQuery(sql);
        return query.list();
    }
}
