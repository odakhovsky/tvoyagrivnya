package com.tvoyagryvnia.dao.impl;


import com.tvoyagryvnia.dao.IAccountDao;
import com.tvoyagryvnia.model.AccountEntity;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class AccountDaoImpl implements IAccountDao {

    @Autowired
    private SessionFactory sessionFactory;

    private Session getSession() {
        return sessionFactory.getCurrentSession();
    }

    @Override
    public void save(AccountEntity account) {
        getSession().save(account);
    }

    @Override
    public void update(AccountEntity account) {
        getSession().update(account);
    }

    @Override
    public AccountEntity getById(int id) {
        return (AccountEntity) getSession().get(AccountEntity.class, id);
    }

    @Override
    public List<AccountEntity> getAllOfUserActive(int user, boolean active) {
        return getSession().createCriteria(AccountEntity.class, "acc")
                .createAlias("acc.owner", "o")
                .add(Restrictions.eq("o.id", user))
                .add(Restrictions.eq("active", true))
                .list();

    }

    @Override
    public List<AccountEntity> getAllOfUserEnabled(int user, boolean active, boolean enabled) {
        return getSession().createCriteria(AccountEntity.class, "acc")
                .createAlias("acc.owner", "o")
                .add(Restrictions.eq("o.id", user))
                .add(Restrictions.eq("active", true))
                .add(Restrictions.eq("enabled", enabled))
                .list();
    }

    @Override
    public AccountEntity findAccountByUserAndName(int user, String name) {
        return (AccountEntity) getSession().createCriteria(AccountEntity.class, "acc")
                .createAlias("acc.owner", "o")
                .add(Restrictions.eq("o.id", user))
                .add(Restrictions.eq("name", name))
                .uniqueResult();
    }

    @Override
    public Long getTotalCount() {
        return (Long) getSession().createQuery("select count(*) from AccountEntity e where e.active = true").uniqueResult();
    }
}
