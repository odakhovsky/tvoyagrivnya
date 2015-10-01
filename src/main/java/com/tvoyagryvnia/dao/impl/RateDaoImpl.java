package com.tvoyagryvnia.dao.impl;

import com.tvoyagryvnia.dao.IRateDao;
import com.tvoyagryvnia.model.RateEntity;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class RateDaoImpl implements IRateDao {

    @Autowired private SessionFactory sessionFactory;

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
}
