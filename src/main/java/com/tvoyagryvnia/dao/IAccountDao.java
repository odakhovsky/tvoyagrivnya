package com.tvoyagryvnia.dao;


import com.tvoyagryvnia.model.AccountEntity;

import java.util.List;

public interface IAccountDao {

    void save(AccountEntity account);
    void update(AccountEntity account);

    AccountEntity getById(int id);
    List<AccountEntity> getAllOfUserActive(int user, boolean active);
    List<AccountEntity> getAllOfUserEnabled(int user, boolean active, boolean enabled);
    AccountEntity findAccountByUserAndName(int user, String name);
}
