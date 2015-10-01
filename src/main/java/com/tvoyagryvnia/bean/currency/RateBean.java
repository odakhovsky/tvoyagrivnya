package com.tvoyagryvnia.bean.currency;


import com.tvoyagryvnia.model.RateEntity;

import java.util.Date;

public class RateBean {

    private int id;
    private int currency;
    private Date date;
    private Float rate;

    public RateBean(RateEntity rate) {
        id = rate.getId();
        currency = rate.getCurrency().getId();
        date = rate.getDate();
        this.rate = rate.getRate();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCurrency() {
        return currency;
    }

    public void setCurrency(int currency) {
        this.currency = currency;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Float getRate() {
        return rate;
    }

    public void setRate(Float rate) {
        this.rate = rate;
    }
}
