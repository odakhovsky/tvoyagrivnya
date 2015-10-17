package com.tvoyagryvnia.dao.impl;

import com.tvoyagryvnia.dao.IUserCategoryDao;
import com.tvoyagryvnia.model.CategoryEntity;
import com.tvoyagryvnia.model.UserCategoryEntity;
import com.tvoyagryvnia.model.enums.OperationType;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserCategoryDaoImpl implements IUserCategoryDao {

    @Autowired
    private SessionFactory sessionFactory;

    private Session getSession() {
        return sessionFactory.getCurrentSession();
    }

    @Override
    public List<UserCategoryEntity> getAll() {
        return getSession().createCriteria(UserCategoryEntity.class).list();
    }

    @Override
    public List<UserCategoryEntity> getAll(boolean active) {
        return getSession().createCriteria(UserCategoryEntity.class)
                .add(Restrictions.eq("active", true))
                .list();
    }

    @Override
    public List<UserCategoryEntity> getAllByType(OperationType operationType) {
        return getSession().createCriteria(UserCategoryEntity.class)
                .add(Restrictions.eq("operation", operationType))
                .list();
    }

    @Override
    public List<UserCategoryEntity> getAll(int user, boolean active) {
        return getSession().createCriteria(UserCategoryEntity.class, "c")
                .createAlias("c.owner", "o")
                .add(Restrictions.eq("o.id", user))
                .add(Restrictions.eq("c.active", active))
                .list();
    }

    @Override
    public List<UserCategoryEntity> getAllByType(int user, OperationType operationType) {
        return getSession().createCriteria(UserCategoryEntity.class, "c")
                .createAlias("c.owner", "o")
                .add(Restrictions.eq("o.id", user))
                .add(Restrictions.eq("c.operation", operationType))
                .add(Restrictions.eq("c.active", true))
                .list();
    }

    @Override
    public UserCategoryEntity getParentByMain(int user, int main) {
        return (UserCategoryEntity) getSession().createCriteria(UserCategoryEntity.class, "uc")
                .createAlias("uc.main", "m")
                .add(Restrictions.eq("m.id", main))
                .createAlias("uc.owner", "o")
                .add(Restrictions.eq("o.id", user))
                .uniqueResult();
    }

    @Override
    public int isCategoryPresent(int user, String name, Integer parent,OperationType type) {
        String q = (parent == 0) ? "IS NULL" : "= :parent";
        Query query = getSession()
                .createSQLQuery("SELECT c.* FROM USER_CATEGORY c WHERE c.OWNER = :owner AND c.OPERATION_TYPE =:t AND c.NAME=:name AND c.PARENT_CATEGORY " + q)
                .addEntity(UserCategoryEntity.class)
                .setParameter("owner", user)
                .setParameter("name", name)
                .setParameter("t",type.name());
        if (parent > 0) {
            query.setParameter("parent", parent);
        }
        UserCategoryEntity entity = (UserCategoryEntity) query.uniqueResult();
        return (null == entity) ? 0 : entity.getId();
    }

    @Override
    public UserCategoryEntity getById(int id) {
        return (UserCategoryEntity) getSession().get(UserCategoryEntity.class, id);
    }


    @Override
    public void save(UserCategoryEntity UserCategoryEntity) {
        getSession().save(UserCategoryEntity);
    }

    @Override
    public void update(UserCategoryEntity UserCategoryEntity) {
        getSession().update(UserCategoryEntity);
    }

    @Override
    public void delete(int id) {
        UserCategoryEntity entity = getById(id);
        if (null != entity) {
            entity.setActive(false);
            update(entity);
        }
    }
}
