package com.tvoyagryvnia.dao;


import com.tvoyagryvnia.model.CurrencyEntity;

import java.util.List;

public interface ICurrencyDao {

    void save(CurrencyEntity currencyEntity);
    CurrencyEntity getById(int id);
    CurrencyEntity getByShortName(String name);
    List<CurrencyEntity> getAll();
    void update(CurrencyEntity currencyEntity);
}
