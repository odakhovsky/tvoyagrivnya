package com.tvoyagryvnia.dao.impl;

import com.tvoyagryvnia.dao.IExchangeDao;
import com.tvoyagryvnia.model.ExchangeEntity;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ExchangeDaoImpl implements IExchangeDao {

    @Autowired
    private SessionFactory sessionFactory;

    private Session session(){return sessionFactory.getCurrentSession();}

    @Override
    public ExchangeEntity getById(int id) {
        return (ExchangeEntity) session().get(ExchangeEntity.class, id);
    }

    @Override
    public List<ExchangeEntity> getAllByUser(int user) {
        return session().createCriteria(ExchangeEntity.class,"e")
                .createAlias("e.owner","o")
                .add(Restrictions.and(
                        Restrictions.eq("o.id", user),
                        Restrictions.eq("e.active", true)
                )).list();
    }

    @Override
    public List<ExchangeEntity> getAllFromAccount(int account) {
        return session().createCriteria(ExchangeEntity.class,"e")
                .createAlias("e.account","acc")
                .add(Restrictions.and(
                        Restrictions.eq("acc.id", account),
                        Restrictions.eq("e.active", true)
                )).list();
    }

    @Override
    public List<ExchangeEntity> getAllToAccount(int account) {
        return session().createCriteria(ExchangeEntity.class,"e")
                .createAlias("e.accountTo","acc")
                .add(Restrictions.and(
                        Restrictions.eq("acc.id", account),
                        Restrictions.eq("e.active", true)
                )).list();
    }

    @Override
    public void save(ExchangeEntity exchangeEntity) {
        session().save(exchangeEntity);
    }

    @Override
    public void update(ExchangeEntity exchangeEntity) {
        session().update(exchangeEntity);
    }
}
