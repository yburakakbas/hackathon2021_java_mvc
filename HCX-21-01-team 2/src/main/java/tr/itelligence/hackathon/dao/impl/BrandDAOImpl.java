package tr.itelligence.hackathon.dao.impl;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import tr.itelligence.hackathon.dao.BrandDAO;
import tr.itelligence.hackathon.entity.Brand;
import tr.itelligence.hackathon.entity.Category;

import java.util.List;

@Transactional
public class BrandDAOImpl implements BrandDAO {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public void save(Brand brand) {
        this.sessionFactory.getCurrentSession().save(brand);
    }

    @Override
    public List<Brand> findAll() {
        String sql = "Select new " + Brand.class.getName() //
                + "(b.id, b.description) "//
                + " from " + Brand.class.getName() + " b ";

        Session session = this.sessionFactory.getCurrentSession();
        Query query = session.createQuery(sql);
        return query.list();
    }
}
