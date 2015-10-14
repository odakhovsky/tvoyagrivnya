package com.tvoyagryvnia.dao.impl;

import com.tvoyagryvnia.dao.ICategoryDao;
import com.tvoyagryvnia.model.CategoryEntity;
import com.tvoyagryvnia.model.enums.OperationType;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public class CategoryDaoImpl implements ICategoryDao {

    @Autowired private SessionFactory sessionFactory;

    private Session getSession(){return  sessionFactory.getCurrentSession();}

    @Override
    public List<CategoryEntity> getAll() {
        return getSession().createCriteria(CategoryEntity.class).list();
    }

    @Override
    public List<CategoryEntity> getAll(boolean active) {
        return getSession().createCriteria(CategoryEntity.class)
                .add(Restrictions.eq("active", true))
                .list();
    }

    @Override
    public List<CategoryEntity> getAllByType(OperationType operationType) {
        return getSession().createCriteria(CategoryEntity.class)
                .add(Restrictions.eq("operation", operationType))
                .list();
    }

    @Override
    public CategoryEntity getById(int id) {
        return (CategoryEntity) getSession().get(CategoryEntity.class, id);
    }

    @Override
    public List<CategoryEntity> getAllBySet(Set<Integer> ids) {
        return getSession().createCriteria(CategoryEntity.class)
                .add(Restrictions.in("id", ids))
                .list();
    }

    @Override
    public void save(CategoryEntity categoryEntity) {
        getSession().save(categoryEntity);
    }

    @Override
    public void update(CategoryEntity categoryEntity) {
        getSession().update(categoryEntity);
    }

    @Override
    public void delete(int id) {
        CategoryEntity entity = getById(id);
        if (null != entity) {
            entity.setActive(false);
            update(entity);
        }
    }
}
