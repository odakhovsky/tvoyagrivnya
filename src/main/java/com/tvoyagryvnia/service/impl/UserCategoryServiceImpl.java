package com.tvoyagryvnia.service.impl;

import com.tvoyagryvnia.bean.category.CategoryBean;
import com.tvoyagryvnia.bean.operation.OperationBean;
import com.tvoyagryvnia.bean.user.UserBean;
import com.tvoyagryvnia.dao.ICategoryDao;
import com.tvoyagryvnia.dao.IUserCategoryDao;
import com.tvoyagryvnia.dao.IUserDao;
import com.tvoyagryvnia.model.CategoryEntity;
import com.tvoyagryvnia.model.UserCategoryEntity;
import com.tvoyagryvnia.model.enums.OperationType;
import com.tvoyagryvnia.service.IOperationService;
import com.tvoyagryvnia.service.IUserCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Set;
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
    @Autowired
    private IOperationService operationService;

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

    @Override
    public Float getSumOfCategoryAndSubCategoriesIfPresent(int userCategoryId,Date from, Date to) {
        UserCategoryEntity category = categoryDao.getById(userCategoryId);
        float sum = 0.0f;
        if (null == category) return sum;
        List<OperationBean> operations = getOperations(category,from,to);
        sum = calcSum(operations);
        if(category.getChildrens().size() > 0) {
            sum = getSummOffChildrens(category.getChildrens(), sum,from, to);
        }
        return sum;
    }

    private List<OperationBean> getOperations(UserCategoryEntity category,Date from, Date to) {
        return operationService.getAllOfUserByCategory(category.getOwner().getId(), category.getId(), true,from,to);
    }

    private float getSummOffChildrens(Set<UserCategoryEntity> childrens, float sum,Date from,Date to) {
        for (UserCategoryEntity cat : childrens) {
            sum += calcSum(getOperations(cat,from,to));
            if(cat.getChildrens().size() > 0) {
                sum = getSummOffChildrens(cat.getChildrens(), sum,from,to);
            }
        }
        return sum;
    }

    private float calcSum(List<OperationBean> operations) {
        float sum = 0.0f;
        for (OperationBean op : operations) {
            sum += op.getMoney() * op.getCurrency().getCrossRate();
        }
        return sum;
    }
}
