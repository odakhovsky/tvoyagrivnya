package com.tvoyagryvnia.service;


import com.tvoyagryvnia.bean.account.AccountBean;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

public interface IAccountService {
    void create(int owner, String name, String description);
    void update(AccountBean account);
    void takeFromAccount(int account, int currency, float value);
    void putIntoAccount(int account,int currency, float value);
    AccountBean getById(int id);
    List<AccountBean> getAllOfUserActive(int user, boolean active);
    List<AccountBean> getAllOfUserEnabled(int user, boolean active, boolean enabled);
    boolean isAccountNameIsFree(int user, String accName);
    public void updateSingleField(int userId, String fieldName, String fielValue)
            throws Exception;
}
