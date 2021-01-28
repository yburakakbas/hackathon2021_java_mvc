package tr.itelligence.hackathon.dao.impl;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import tr.itelligence.hackathon.dao.ProductDetailsDAO;
import tr.itelligence.hackathon.entity.Brand;
import tr.itelligence.hackathon.entity.Product;
import tr.itelligence.hackathon.entity.ProductDetails;

import javax.persistence.Entity;
import java.util.List;

@Transactional
public class ProductDetailsDAOImpl implements ProductDetailsDAO {
    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public void save(ProductDetails productDetails) {
        this.sessionFactory.getCurrentSession().save(productDetails);
    }

    @Override
    public List<ProductDetails> findAll() {
        String sql = "Select new " + ProductDetails.class.getName() //
                + "(p.id, p.description) "//
                + " from " + ProductDetails.class.getName() + " p ";

        Session session = this.sessionFactory.getCurrentSession();
        Query query = session.createQuery(sql);
        return query.list();
    }

    @Override
    public ProductDetails findProductDetails(Long id) {
        Session session = sessionFactory.getCurrentSession();
        Criteria crit = session.createCriteria(ProductDetails.class);
        crit.add(Restrictions.eq("id", id));
        return (ProductDetails) crit.uniqueResult();
    }
}
