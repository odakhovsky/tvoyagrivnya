package com.tvoyagryvnia.dao.impl;

import com.tvoyagryvnia.dao.ICurrencyDao;
import com.tvoyagryvnia.model.CurrencyEntity;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CurrencyDaoImpl implements ICurrencyDao {

    @Autowired private SessionFactory sessionFactory;

    private Session session(){return  sessionFactory.getCurrentSession();}

    @Override
    public void save(CurrencyEntity currencyEntity) {
        session().save(currencyEntity);
    }

    @Override
    public CurrencyEntity getById(int id) {
        return (CurrencyEntity) session().get(CurrencyEntity.class, id);
    }

    @Override
    public CurrencyEntity getByShortName(String name) {
        return (CurrencyEntity) session().createCriteria(CurrencyEntity.class)
                .add(Restrictions.eq("shortName", name))
                .uniqueResult();
    }

    @Override
    public List<CurrencyEntity> getAll() {
        return session().createCriteria(CurrencyEntity.class).list();
    }

    @Override
    public void update(CurrencyEntity currencyEntity) {
        session().update(currencyEntity);
    }
}
