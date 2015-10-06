package com.tvoyagryvnia.dao;


import com.tvoyagryvnia.model.ExchangeEntity;

import java.util.List;

public interface IExchangeDao {

    ExchangeEntity getById(int id);
    List<ExchangeEntity> getAllByUser(int user);
    List<ExchangeEntity> getAllFromAccount(int account);
    List<ExchangeEntity> getAllToAccount(int account);
    void save(ExchangeEntity exchangeEntity);
    void update(ExchangeEntity exchangeEntity);

}
