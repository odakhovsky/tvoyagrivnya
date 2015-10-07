package com.tvoyagryvnia.dao.impl;

import com.tvoyagryvnia.dao.IUserCurrencyDao;
import com.tvoyagryvnia.model.UserCurrencyEntity;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserCurrencyDaoImpl implements IUserCurrencyDao {

    @Autowired
    private SessionFactory sessionFactory;

    private Session session() {
        return sessionFactory.getCurrentSession();
    }

    @Override
    public void save(UserCurrencyEntity entity) {
        session().save(entity);
    }

    @Override
    public UserCurrencyEntity getById(int id) {
        return (UserCurrencyEntity) session().get(UserCurrencyEntity.class, id);
    }

    @Override
    public UserCurrencyEntity getByShortNameForUser(int user, String name) {
        return (UserCurrencyEntity) session().createCriteria(UserCurrencyEntity.class, "uc")
                .createAlias("uc.owner", "o")
                .add(Restrictions.and(
                        Restrictions.eq("o.id", user),
                        Restrictions.eq("shortName", name)
                ))
                .uniqueResult();
    }

    @Override
    public List<UserCurrencyEntity> getAllOfUser(int user) {
        return session().createCriteria(UserCurrencyEntity.class,"uc")
                .createAlias("uc.owner","o")
                .add(Restrictions.eq("o.id", user))
                .list();
    }

    @Override
    public void update(UserCurrencyEntity currencyEntity) {
        session().update(currencyEntity);
    }

    @Override
    public UserCurrencyEntity getDefCurrency(int user) {
        return (UserCurrencyEntity) session().createCriteria(UserCurrencyEntity.class,"c")
                .createAlias("c.owner","o")
                .add(Restrictions.eq("o.id", user))
                .add(Restrictions.eq("def", true))
                .uniqueResult();
    }

    @Override
    public UserCurrencyEntity getDefaultCurrencyOfUser(int user) {
        return (UserCurrencyEntity) session().createCriteria(UserCurrencyEntity.class,"uc")
                .createAlias("uc.owner","o")
                .add(Restrictions.eq("o.id", user))
                .add(Restrictions.eq("def", true))
                .uniqueResult();
    }
}
