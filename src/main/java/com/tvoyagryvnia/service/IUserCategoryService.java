package com.tvoyagryvnia.service;


import com.tvoyagryvnia.bean.category.CategoryBean;
import com.tvoyagryvnia.bean.user.UserBean;
import com.tvoyagryvnia.model.UserCategoryEntity;
import com.tvoyagryvnia.model.enums.OperationType;

import java.util.List;

public interface IUserCategoryService {
    List<CategoryBean> getAll();
    List<CategoryBean> getAll(boolean active);
    List<CategoryBean> getAllByType(OperationType operationType);
    List<CategoryBean> getAll(int user,boolean active);

    List<CategoryBean> getAllByType(int user,OperationType operationType);

    CategoryBean getById(int id);

    void create(UserBean owner, CategoryBean CategoryBean);

    void update(CategoryBean CategoryBean);

    void delete(int id);

    int isCategoryPresent(int user,String name, Integer parent, OperationType operationType);

}
