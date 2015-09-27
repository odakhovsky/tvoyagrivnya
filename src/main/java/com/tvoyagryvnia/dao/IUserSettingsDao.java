package com.tvoyagryvnia.dao;


import com.tvoyagryvnia.model.UserSettingsEntity;

public interface IUserSettingsDao {

    public void saveOrUpdate(UserSettingsEntity userSettingsEntity);

    public UserSettingsEntity getByUserID(Integer userID);

    public UserSettingsEntity getByUserLogin(String login);
}
