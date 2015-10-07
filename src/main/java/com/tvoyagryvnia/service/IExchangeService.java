package com.tvoyagryvnia.service;


import com.tvoyagryvnia.bean.operation.ExchangeBean;
import com.tvoyagryvnia.model.ExchangeEntity;

import java.util.List;

public interface IExchangeService {

    ExchangeBean getById(int id);
    List<ExchangeBean> getAllByUser(int user);
    List<ExchangeBean> getAllFromAccount(int account);
    List<ExchangeBean> getAllToAccount(int account);
    void create(String date, String description, float money, int account, int currency, int user,
                int accountTo, int currencyTo, float moneyTo
    );

}
