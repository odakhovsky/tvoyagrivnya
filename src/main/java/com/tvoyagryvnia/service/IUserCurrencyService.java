package com.tvoyagryvnia.service;


import com.tvoyagryvnia.bean.currency.ExtendedCurrencyBean;
import com.tvoyagryvnia.model.UserCurrencyEntity;

import java.util.List;

public interface IUserCurrencyService {

    void addAllForUser(int user);

    ExtendedCurrencyBean getById(int id);

    ExtendedCurrencyBean getByShortNameForUser(int user, String name);

    List<ExtendedCurrencyBean> getAllOfUser(int user);

    void update(ExtendedCurrencyBean bean);

    void setAsDefault(int user, int currency);

    void updateCrossRate(int pk, float rate);

    ExtendedCurrencyBean getDefaultCurrencyOfUser(int user);

}
