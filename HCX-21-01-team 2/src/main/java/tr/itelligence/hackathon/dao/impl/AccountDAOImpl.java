package tr.itelligence.hackathon.dao.impl;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import tr.itelligence.hackathon.dao.AccountDAO;
import tr.itelligence.hackathon.entity.Account;

// Transactional for Hibernate
@Transactional
public class AccountDAOImpl implements AccountDAO {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public Account findAccount(String userName ) {
        Session session = sessionFactory.getCurrentSession();
        Criteria crit = session.createCriteria(Account.class);
        crit.add(Restrictions.eq("userName", userName));
        return (Account) crit.uniqueResult();
    }

    @Override
    public void save(Account account) throws Exception {
        Session session = sessionFactory.getCurrentSession();
        if (emailExist(account.getUserName(), session)) {
            throw new Exception("There is an account with that email address: "
                    + account.getUserName());
        }
        session.save(account);
    }

    private boolean emailExist(String userName, Session session) {
        return session.find(Account.class, userName) != null;
    }
}