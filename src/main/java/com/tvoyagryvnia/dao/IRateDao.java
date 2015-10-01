package com.tvoyagryvnia.dao;


import com.tvoyagryvnia.model.RateEntity;

import java.util.List;

public interface IRateDao {

    void save(RateEntity rate);
    RateEntity getById(int id);
    List<RateEntity> getAllOfUserCurrency(int userCurrency);
    void update(RateEntity rateEntity);

}
