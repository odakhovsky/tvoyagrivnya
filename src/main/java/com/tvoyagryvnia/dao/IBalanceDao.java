package com.tvoyagryvnia.dao;


import com.tvoyagryvnia.model.BalanceEntity;

import java.util.List;

public interface IBalanceDao {

    BalanceEntity getById(int id);
    List<BalanceEntity> getAllOfAccount(int account);
    List<BalanceEntity> getAllOfUser(int user);
    void update(BalanceEntity balanceEntity);
    void save(BalanceEntity balanceEntity);
    BalanceEntity getByAccAndCurrency(int account, int currecy);
    List<BalanceEntity> getAllActive();
}
