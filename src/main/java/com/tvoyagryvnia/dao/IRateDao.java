package com.tvoyagryvnia.dao;


import com.tvoyagryvnia.model.RateEntity;
import com.tvoyagryvnia.model.UserCurrencyEntity;

import java.util.List;

public interface IRateDao {

    void save(RateEntity rate);
    RateEntity getById(int id);
    List<RateEntity> getAllOfUserCurrency(int userCurrency);
    void update(RateEntity rateEntity);
    List<RateEntity> getAllOfUser(int owner);
}
