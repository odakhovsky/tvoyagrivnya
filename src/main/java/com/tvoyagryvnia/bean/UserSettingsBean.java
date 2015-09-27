package com.tvoyagryvnia.bean;


import com.tvoyagryvnia.model.UserSettingsEntity;

public class UserSettingsBean {

    private Integer userId;

    public UserSettingsBean() { }

    public UserSettingsBean(UserSettingsEntity userSettingsEntity) {
        userId = userSettingsEntity.getUser().getId();
    }

    public UserSettingsBean(Integer userId){
        this.userId = userId;

    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}
