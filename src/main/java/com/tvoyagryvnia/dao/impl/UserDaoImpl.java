package com.tvoyagryvnia.dao.impl;

import com.tvoyagryvnia.dao.IRoleDao;
import com.tvoyagryvnia.dao.IUserDao;
import com.tvoyagryvnia.model.RoleEntity;
import com.tvoyagryvnia.model.UserEntity;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by root on 02.09.2015.
 */
@Repository
public class UserDaoImpl implements IUserDao {

    @Autowired private SessionFactory sessionFactory;
    @Autowired private IRoleDao roleDao;

    private Session getSession() {
        return sessionFactory.getCurrentSession();
    }


    @Override
    public UserEntity findUserByEmail(String email) {
        return (UserEntity) getSession().createCriteria(UserEntity.class)
                .add(Restrictions.eq("email", email))
                .add(Restrictions.eq("active", true))
                .uniqueResult();
    }

    @Override
    public int saveUser(UserEntity user) {
        return (int) getSession().save(user);
    }


    @Override
    public void updateUser(UserEntity user) {
        getSession().update(user);
    }

    @Override
    public void deleteUser(UserEntity user) {
        getSession().delete(user);
    }

    @Override
    public List<UserEntity> getAllUsers(boolean activeStatus) {
        return getSession().createCriteria(UserEntity.class)
                .add(Restrictions.eq("active", activeStatus))
                .addOrder(Order.asc("name"))
                .list();
    }

    @Override
    public List<UserEntity> getAllUsers() {
        return getAllUsers(true);
    }

    @Override
    public UserEntity getUserById(int id) {
        return (UserEntity) getSession().createCriteria(UserEntity.class)
                .add(Restrictions.eq("id", id))
                .uniqueResult();
    }

    @Override
    public boolean isUserLoginFree(String userLogin) {
        boolean loginFree = true;
        UserEntity userEntity = (UserEntity) getSession().createCriteria(UserEntity.class)
                .add(Restrictions.eq("email", userLogin))
                .uniqueResult();

        if (userEntity != null) {
            loginFree = false;
        }
        return loginFree;
    }

    @Override
    public UserEntity findUserByToken(String token) {
        return (UserEntity) getSession().createCriteria(UserEntity.class)
                .add(Restrictions.eq("token", token))
                .uniqueResult();
    }

    @Override
    public UserEntity findUserByLogin(String login) {
        return (UserEntity) getSession().createCriteria(UserEntity.class)
                .add(Restrictions.eq("email", login))
                .uniqueResult();
    }

    @Override
    public void addRole(UserEntity user, RoleEntity.Name roleName) {
        RoleEntity roleEntity = roleDao.getRoleByName(roleName);

        if (!user.getRoles().contains(roleEntity)) {
            user.getRoles().add(roleEntity);
            updateUser(user);
        }
    }

    @Override
    public void removeRole(UserEntity user, RoleEntity.Name roleName) {
        RoleEntity roleEntity = roleDao.getRoleByName(roleName);

        user.getRoles().remove(roleEntity);
        updateUser(user);
    }
}