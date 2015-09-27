package com.tvoyagryvnia.dao.impl;

import com.tvoyagryvnia.dao.IRoleDao;
import com.tvoyagryvnia.model.RoleEntity;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Repository
@Transactional
public class RoleDaoImpl implements IRoleDao {

    @Autowired
    SessionFactory sessionFactory;

    private Session getSession() {
        return sessionFactory.getCurrentSession();
    }

    @Override
    public RoleEntity getRoleById(int id) {
        return (RoleEntity) getSession().get(RoleEntity.class, id);
    }

    @Override
    public RoleEntity getRoleByName(RoleEntity.Name nameEntity) {
        String hql = "FROM RoleEntity WHERE name = :name";
        Query query = getSession().createQuery(hql);
        query.setParameter("name", nameEntity);

        return (RoleEntity) query.uniqueResult();
    }

    @Override
    public List<RoleEntity> getRolesByIds(Set<Integer> roleIds) {
        String hql = "FROM RoleEntity where id IN (:ids)";
        Query query = getSession().createQuery(hql);
        query.setParameterList("ids", roleIds);

        return query.list();
    }

    @Override
    public int saveRole(RoleEntity roleEntity) {
        return (int) getSession().save(roleEntity);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<RoleEntity> getAllRoles() {
        return getSession().createCriteria(RoleEntity.class).list();
    }

    @Override
    public void updateRole(RoleEntity roleEntity) {
        getSession().update(roleEntity);
    }

    @Override
    public void deleteRole(RoleEntity roleEntity) {
        getSession().delete(roleEntity);
    }
}
