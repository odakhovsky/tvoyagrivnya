package com.tvoyagryvnia.dao.impl;


import com.tvoyagryvnia.dao.IUserSettingsDao;
import com.tvoyagryvnia.model.UserSettingsEntity;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class UserSettingsDaoImpl implements IUserSettingsDao {

    @Autowired
    private SessionFactory sessionFactory;

    private Session getSession() {
        return sessionFactory.getCurrentSession();
    }

    @Override
    public void saveOrUpdate(UserSettingsEntity userSettingsEntity) {
        getSession().saveOrUpdate(userSettingsEntity);
    }

    @Override
    public UserSettingsEntity getByUserID(Integer userID) {
        Criteria criteria = getSession().createCriteria(UserSettingsEntity.class);
        criteria.createAlias("user", "user");
        criteria.add(Restrictions.eq("user.id", userID));

        return (UserSettingsEntity) criteria.uniqueResult();
    }

    @Override
    public UserSettingsEntity getByUserLogin(String login) {
        Criteria criteria = getSession().createCriteria(UserSettingsEntity.class);
        criteria.createAlias("user", "user");
        criteria.add(Restrictions.eq("user.email", login));

        return (UserSettingsEntity) criteria.uniqueResult();
    }
}
