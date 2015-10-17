package com.tvoyagryvnia.service;


import com.tvoyagryvnia.bean.category.CategoryBean;
import com.tvoyagryvnia.bean.user.UserBean;
import com.tvoyagryvnia.model.UserCategoryEntity;
import com.tvoyagryvnia.model.enums.OperationType;

import java.util.List;

public interface ICategoryService {
    List<CategoryBean> getAll();
    List<CategoryBean> getAll(boolean active);
    List<CategoryBean> getAllByType(OperationType operationType);
    CategoryBean getById(int id);

    void create(CategoryBean CategoryBean);

    void update(CategoryBean CategoryBean);

    void delete(int id);
}
