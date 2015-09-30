package com.tvoyagryvnia.service;


import com.tvoyagryvnia.bean.category.CategoryBean;
import com.tvoyagryvnia.model.enums.OperationType;

import java.util.List;

public interface ICategoryService {
    List<CategoryBean> getAll();
    List<CategoryBean> getAll(boolean active);
    CategoryBean getByName(String name);
    List<CategoryBean> getAllByType(OperationType operationType);
    CategoryBean getById(int id);

    void create(CategoryBean CategoryBean);

    void update(CategoryBean CategoryBean);

    void delete(int id);
}
