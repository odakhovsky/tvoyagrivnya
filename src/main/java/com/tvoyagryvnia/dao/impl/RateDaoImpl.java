package com.tvoyagryvnia.dao.impl;

import com.tvoyagryvnia.dao.IRateDao;
import com.tvoyagryvnia.dao.IUserCurrencyDao;
import com.tvoyagryvnia.model.RateEntity;
import com.tvoyagryvnia.model.UserCurrencyEntity;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public class RateDaoImpl implements IRateDao {

    @Autowired private SessionFactory sessionFactory;
    @Autowired private IUserCurrencyDao userCurrencyDao;

    @Override
    public void save(RateEntity rate) {
        sessionFactory.getCurrentSession().save(rate);
    }

    @Override
    public RateEntity getById(int id) {
        return (RateEntity) sessionFactory.getCurrentSession().get(RateEntity.class, id);
    }

    @Override
    public List<RateEntity> getAllOfUserCurrency(int userCurrency) {
        return sessionFactory.getCurrentSession().createCriteria(RateEntity.class,"r")
                .createAlias("r.currency", "c")
                .add(Restrictions.eq("c.id", userCurrency))
                .list();
    }

    @Override
    public void update(RateEntity rateEntity) {
        sessionFactory.getCurrentSession().update(rateEntity);
    }

    @Override
    public List<RateEntity> getAllOfUser(int owner) {
        return sessionFactory.getCurrentSession().createCriteria(RateEntity.class,"r")
                .createAlias("r.currency","c")
                .createAlias("c.owner","o")
                .add(Restrictions.eq("o.id", owner))
                .list();
    }

}
