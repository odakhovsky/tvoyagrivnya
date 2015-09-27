package com.tvoyagryvnia.bean;


import com.tvoyagryvnia.model.FeedBackEntity;

import java.util.Date;

public class FeedbackBean {
    private int id;
    private Date date;
    private String name;
    private String email;
    private String text;

    public FeedbackBean(FeedBackEntity feedBack) {
        id = feedBack.getId();
        date = feedBack.getDate();
        email = feedBack.getEmail();
        text = feedBack.getText();
        name = feedBack.getName();
    }

    public FeedbackBean(){}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
