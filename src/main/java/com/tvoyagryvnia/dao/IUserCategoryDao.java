package com.tvoyagryvnia.dao;


import com.tvoyagryvnia.model.UserCategoryEntity;
import com.tvoyagryvnia.model.enums.OperationType;

import java.util.List;

public interface IUserCategoryDao {

    List<UserCategoryEntity> getAll();

    List<UserCategoryEntity> getAll(boolean active);

    List<UserCategoryEntity> getAllByType(OperationType operationType);

    List<UserCategoryEntity> getAll(int user,boolean active);

    List<UserCategoryEntity> getAllByType(int user,OperationType operationType);

    UserCategoryEntity getParentByMain(int user,int main);

    int isCategoryPresent(int user,String name, Integer parent, OperationType type);


    UserCategoryEntity getById(int id);

    void save(UserCategoryEntity UserCategoryEntity);

    void update(UserCategoryEntity UserCategoryEntity);

    void delete(int id);

}
