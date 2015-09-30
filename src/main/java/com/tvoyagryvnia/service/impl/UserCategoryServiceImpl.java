package com.tvoyagryvnia.service.impl;

import com.tvoyagryvnia.bean.category.CategoryBean;
import com.tvoyagryvnia.bean.user.UserBean;
import com.tvoyagryvnia.dao.ICategoryDao;
import com.tvoyagryvnia.dao.IUserCategoryDao;
import com.tvoyagryvnia.dao.IUserDao;
import com.tvoyagryvnia.model.CategoryEntity;
import com.tvoyagryvnia.model.UserCategoryEntity;
import com.tvoyagryvnia.model.enums.OperationType;
import com.tvoyagryvnia.service.ICategoryService;
import com.tvoyagryvnia.service.IUserCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserCategoryServiceImpl implements IUserCategoryService {

    @Autowired
    private IUserCategoryDao categoryDao;
    @Autowired
    private ICategoryDao baseCategoryDao;
    @Autowired
    private IUserDao userDao;

    @Override
    public List<CategoryBean> getAll() {
        return categoryDao.getAll().stream().map(CategoryBean::new).collect(Collectors.toList());
    }

    @Override
    public List<CategoryBean> getAll(boolean active) {
        return categoryDao.getAll(active).stream().map(CategoryBean::new).collect(Collectors.toList());
    }

    @Override
    public List<CategoryBean> getAllByType(OperationType operationType) {
        return categoryDao.getAllByType(operationType).stream().map(CategoryBean::new).collect(Collectors.toList());
    }

    @Override
    public List<CategoryBean> getAll(int user, boolean active) {
        return categoryDao.getAll(user, true).stream().map(CategoryBean::new).collect(Collectors.toList());
    }

    @Override
    public List<CategoryBean> getAllByType(int user, OperationType operationType) {
        return categoryDao.getAllByType(user, operationType).stream().map(CategoryBean::new).collect(Collectors.toList());
    }

    @Override
    public CategoryBean getById(int id) {
        return new CategoryBean(categoryDao.getById(id));
    }

    @Override
    public void create(UserBean owner, CategoryBean categoryBean) {
        UserCategoryEntity categoryEntity = new UserCategoryEntity();
        UserCategoryEntity base = (Objects.isNull(categoryBean.getParentName())) ? null : categoryDao.getById(categoryBean.getParent());
        categoryEntity.setName(categoryBean.getName());
        categoryEntity.setActive(true);
        categoryEntity.setOperation(OperationType.valueOf(categoryBean.getOperation()));
        categoryEntity.setParent(base);
        categoryEntity.setOwner(userDao.getUserById(owner.getId()));
        if (categoryBean.getMainCategory() > 0) {
            CategoryEntity entity = baseCategoryDao.getById(categoryBean.getMainCategory());
            categoryEntity.setMain(entity);
        }
        categoryDao.save(categoryEntity);
    }

    @Override
    public void update(CategoryBean CategoryBean) {
        UserCategoryEntity categoryEntity = categoryDao.getById(CategoryBean.getId());
        UserCategoryEntity base = (0 == CategoryBean.getParent()) ? null : categoryDao.getById(CategoryBean.getParent());
        categoryEntity.setName(CategoryBean.getName());
        categoryEntity.setOperation(OperationType.valueOf(CategoryBean.getOperation()));
        categoryEntity.setParent(base);
        categoryEntity.setActive(CategoryBean.getActive());
        categoryDao.update(categoryEntity);
    }

    @Override
    public void delete(int id) {
        UserCategoryEntity category = categoryDao.getById(id);
        if (null != category) {
            category.setActive(false);
            categoryDao.update(category);
        }

    }

    @Override
    public int isCategoryPresent(int user, String name, Integer parent, OperationType type) {
        return categoryDao.isCategoryPresent(user, name, parent, type);
    }
}
