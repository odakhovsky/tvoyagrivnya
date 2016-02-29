package com.tvoyagryvnia.bean.currency;


import com.tvoyagryvnia.model.CurrencyEntity;

public class CurrencyBean {

    protected int id;
    private String name;
    private String currency;
    private String shortName;
    protected float crossRate;

    public CurrencyBean(CurrencyEntity currencyEntity){
        this.id = currencyEntity.getId();
        this.name = currencyEntity.getName();
        this.currency = currencyEntity.getCurrency();
        this.shortName = currencyEntity.getShortName();
        this.crossRate = currencyEntity.getCrossRate();
    }

    public CurrencyBean(){}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public float getCrossRate() {
        return crossRate;
    }

    public void setCrossRate(float crossRate) {
        this.crossRate = crossRate;
    }
}
