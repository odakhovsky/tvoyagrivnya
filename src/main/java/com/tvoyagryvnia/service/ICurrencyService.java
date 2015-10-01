package com.tvoyagryvnia.service;


import com.tvoyagryvnia.bean.currency.CurrencyBean;

import java.util.List;

public interface ICurrencyService {

    void create(CurrencyBean bean);
    void create(String name, String currency, String shortName, float rate);
    CurrencyBean getById(int id);
    CurrencyBean getByShortName(String name);
    List<CurrencyBean> getAll();
    void update(CurrencyBean currencyEntity);
}
