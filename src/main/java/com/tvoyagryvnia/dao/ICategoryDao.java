package com.tvoyagryvnia.dao;


import com.tvoyagryvnia.model.CategoryEntity;
import com.tvoyagryvnia.model.enums.OperationType;

import java.util.List;
import java.util.Set;

public interface ICategoryDao {

    List<CategoryEntity> getAll();
    List<CategoryEntity> getAll(boolean active);
    List<CategoryEntity> getAllByType(OperationType operationType);
    CategoryEntity getById(int id);
    List<CategoryEntity> getAllBySet(Set<Integer> ids);

    void save(CategoryEntity categoryEntity);

    void update(CategoryEntity categoryEntity);

    void delete(int id);

}
