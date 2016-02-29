package com.tvoyagryvnia.bean;


import com.tvoyagryvnia.model.NoteEntity;

import java.util.Date;

public class NoteBean {

    private int id;
    private String category;
    private int categoryId;
    private String text;
    private Date date;
    private int owner;
    private int currencyId;
    private String currency;
    private float sum;

    public NoteBean(NoteEntity note) {
        this.id = note.getId();
        this.category = (null != note.getCategory()) ? note.getCategory().getName() : "Без категорії";
        this.categoryId = (null != note.getCategory()) ? note.getCategory().getId() : 0;
        this.currencyId = (null != note.getCurrency()) ? note.getCurrency().getId() : 0;
        if (currencyId != 0){
            currency = note.getCurrency().getCurrency().getShortName();
        }
        this.sum = note.getSum();
        this.date = note.getDate();
        this.text = note.getText();
        this.owner = note.getOwner().getId();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getOwner() {
        return owner;
    }

    public void setOwner(int owner) {
        this.owner = owner;
    }

    public int getCurrencyId() {
        return currencyId;
    }

    public void setCurrencyId(int currencyId) {
        this.currencyId = currencyId;
    }

    public float getSum() {
        return sum;
    }

    public void setSum(float sum) {
        this.sum = sum;
    }


    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }
}


