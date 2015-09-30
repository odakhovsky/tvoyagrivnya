package com.tvoyagryvnia.dao;


import com.tvoyagryvnia.model.CategoryEntity;
import com.tvoyagryvnia.model.enums.OperationType;

import java.util.List;

public interface ICategoryDao {

    List<CategoryEntity> getAll();
    List<CategoryEntity> getAll(boolean active);
    CategoryEntity getByName(String name);
    List<CategoryEntity> getAllByType(OperationType operationType);
    CategoryEntity getById(int id);

    void save(CategoryEntity categoryEntity);

    void update(CategoryEntity categoryEntity);

    void delete(int id);

}
