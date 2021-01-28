package tr.itelligence.hackathon.dao.impl;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import tr.itelligence.hackathon.dao.FavoriteDAO;
import tr.itelligence.hackathon.entity.Category;
import tr.itelligence.hackathon.entity.Favorite;
import tr.itelligence.hackathon.model.FavoriteDTO;
import tr.itelligence.hackathon.model.OrderInfo;
import tr.itelligence.hackathon.model.PaginationResult;

import java.util.Collections;
import java.util.List;

public class FavoriteDAOImpl implements FavoriteDAO {

    @Autowired
    private SessionFactory sessionFactory;

    @Transactional
    @Override
    public void save(Favorite favorite) {
        Session session = null;

        try {
            session = sessionFactory.getCurrentSession();
            session.save(favorite);
        } catch (Exception e) {
            session.close();
            e.printStackTrace();
        }
    }

    @Override
    public Favorite getByUserNameAndProductCode(String userName, String productCode) {
        Session session = null;

        try {
            session = sessionFactory.getCurrentSession();
            Criteria criteria = session.createCriteria(Favorite.class, "favorite");
            criteria.createAlias("favorite.account","account");
            criteria.createAlias("favorite.product","product");
            criteria.add(Restrictions.eq("account.userName", userName));
            criteria.add(Restrictions.eq("product.code", productCode));

            return (Favorite) criteria.uniqueResult();
        } catch (Exception e) {
            e.printStackTrace();
            session.close();
            return null;
        }
    }

    @Override
    public PaginationResult<FavoriteDTO> getAllByUserName(String userName, int page, int maxResult, int maxNavigationPage) {
        Session session = null;

        try {
            String hql = "Select new " + FavoriteDTO.class.getName() //
                    + "(f.id, f.account.userName, f.product.code, f.product.price) "//
                    + " from " + Favorite.class.getName() + " f " + "where f.account.userName = :userName";


            session = this.sessionFactory.getCurrentSession();
            Query query = session.createQuery(hql);
            query.setParameter("userName", userName);

            return new PaginationResult<FavoriteDTO>(query, page, maxResult, maxNavigationPage);
        } catch (Exception e) {
            e.printStackTrace();
            session.close();
            return null;
        }
    }

}
