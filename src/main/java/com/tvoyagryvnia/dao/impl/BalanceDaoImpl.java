package com.tvoyagryvnia.dao.impl;

import com.tvoyagryvnia.dao.IBalanceDao;
import com.tvoyagryvnia.model.BalanceEntity;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public class BalanceDaoImpl implements IBalanceDao {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public BalanceEntity getById(int id) {
        return (BalanceEntity) sessionFactory.getCurrentSession().get(BalanceEntity.class, id);
    }

    @Override
    public List<BalanceEntity> getAllOfAccount(int account) {
        return (List<BalanceEntity>) sessionFactory.getCurrentSession()
                .createCriteria(BalanceEntity.class, "b")
                .createAlias("b.account", "a")
                .add(Restrictions.eq("a.id", account))
                .uniqueResult();
    }

    @Override
    public List<BalanceEntity> getAllOfUser(int user) {
        return (List<BalanceEntity>) sessionFactory.getCurrentSession()
                .createCriteria(BalanceEntity.class, "b")
                .createAlias("b.account", "a")
                .createAlias("a.owner", "o")
                .add(Restrictions.eq("o.id", user))
                .uniqueResult();
    }

    @Override
    public void update(BalanceEntity balanceEntity) {
        sessionFactory.getCurrentSession().update(balanceEntity);
    }

    @Override
    public void save(BalanceEntity balanceEntity) {
        sessionFactory.getCurrentSession().save(balanceEntity);
    }

    @Override
    public BalanceEntity getByAccAndCurrency(int account, int currecy) {
        return (BalanceEntity) sessionFactory.getCurrentSession()
                .createCriteria(BalanceEntity.class, "b")
                .createAlias("b.account", "ac")
                .createAlias("b.currency", "c")
                .add(Restrictions.and(
                        Restrictions.eq("ac.id", account),
                        Restrictions.eq("c.id", currecy)
                ))
                .uniqueResult();
    }
}
