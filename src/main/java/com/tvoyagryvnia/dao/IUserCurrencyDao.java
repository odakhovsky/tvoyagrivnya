package com.tvoyagryvnia.dao;


import com.tvoyagryvnia.model.UserCurrencyEntity;

import java.util.List;

public interface IUserCurrencyDao {
    void save(UserCurrencyEntity entity);
    UserCurrencyEntity getById(int id);
    UserCurrencyEntity getByShortNameForUser(int user, String name);
    List<UserCurrencyEntity> getAllOfUser(int user);
    void update(UserCurrencyEntity currencyEntity);
}
