package com.tvoyagryvnia.bean;


import com.tvoyagryvnia.model.UserCategoryEntity;

public class ChangeNoteSelectBean {

    private int value;
    private String text;

    public ChangeNoteSelectBean(UserCategoryEntity userCategoryEntity) {
        this.value = userCategoryEntity.getId();
        this.text = userCategoryEntity.getName();
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
