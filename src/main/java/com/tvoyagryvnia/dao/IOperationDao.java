package com.tvoyagryvnia.dao;


import com.tvoyagryvnia.model.OperationEntity;
import com.tvoyagryvnia.model.enums.OperationType;

import java.util.List;

public interface IOperationDao {

    OperationEntity getById(int id);
    List<OperationEntity> getAllOfUser(int user, boolean active);
    List<OperationEntity> getAllOfUserByType(int user, OperationType type, boolean  active);
    List<OperationEntity> getAllOfUserByCategory(int user, int userCategory, boolean active);
    List<OperationEntity> getAllOfUserByCategoryWithSubCats(int user, int mainUserCategory, boolean active);

    List<OperationEntity> getAllByAccount(int account);
    List<OperationEntity> getAllByAccountAndCurrency(int account, int currency);
    List<OperationEntity> getAllOfUserByCurrency(int user, int currency);
    List<OperationEntity> getAllLast30OperationsOfAcc(int account);

    void save(OperationEntity operation);
    void update(OperationEntity operation);
    void delete(OperationEntity operation);

}
