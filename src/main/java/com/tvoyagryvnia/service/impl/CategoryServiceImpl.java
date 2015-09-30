package com.tvoyagryvnia.service.impl;

import com.tvoyagryvnia.bean.category.CategoryBean;
import com.tvoyagryvnia.dao.ICategoryDao;
import com.tvoyagryvnia.model.CategoryEntity;
import com.tvoyagryvnia.model.enums.OperationType;
import com.tvoyagryvnia.service.ICategoryService;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@Transactional
public class CategoryServiceImpl implements ICategoryService {

    @Autowired private ICategoryDao categoryDao;

    @Override
    public List<CategoryBean> getAll() {
        return categoryDao.getAll().stream().map(CategoryBean::new).collect(Collectors.toList());
    }

    @Override
    public List<CategoryBean> getAll(boolean active) {
        return categoryDao.getAll(active).stream().map(CategoryBean::new).collect(Collectors.toList());
    }

    @Override
    public CategoryBean getByName(String name) {
        return new CategoryBean(categoryDao.getByName(name));
    }

    @Override
    public List<CategoryBean> getAllByType(OperationType operationType) {
        return categoryDao.getAllByType(operationType).stream().map(CategoryBean::new).collect(Collectors.toList());
    }

    @Override
    public CategoryBean getById(int id) {
        return new CategoryBean(categoryDao.getById(id));
    }

    @Override
    public void create(CategoryBean categoryBean) {
        CategoryEntity categoryEntity = new CategoryEntity();
        CategoryEntity base = (Objects.isNull(categoryBean.getParentName())) ? null : categoryDao.getById(categoryBean.getParent());
        categoryEntity.setName(categoryBean.getName());
        categoryEntity.setActive(true);
        categoryEntity.setOperation(OperationType.valueOf(categoryBean.getOperation()));
        categoryEntity.setParent(base);
        categoryDao.save(categoryEntity);
    }

    @Override
    public void update(CategoryBean CategoryBean) {
        CategoryEntity categoryEntity = categoryDao.getById(CategoryBean.getId());
        CategoryEntity base = (0 == CategoryBean.getParent()) ? null : categoryDao.getById(CategoryBean.getParent());
        categoryEntity.setName(CategoryBean.getName());
        categoryEntity.setOperation(OperationType.valueOf(CategoryBean.getOperation()));
        categoryEntity.setParent(base);
        categoryDao.update(categoryEntity);
    }

    @Override
    public void delete(int id) {

    }
}
