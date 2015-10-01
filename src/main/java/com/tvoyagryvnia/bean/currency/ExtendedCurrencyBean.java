package com.tvoyagryvnia.bean.currency;

import com.tvoyagryvnia.model.UserCurrencyEntity;


public class ExtendedCurrencyBean extends CurrencyBean {

    protected int id;
    protected float crossRate;
    private int owner;
    private boolean def;

    public ExtendedCurrencyBean(UserCurrencyEntity currencyEntity) {
        super(currencyEntity.getCurrency());
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

    public int getOwner() {
        return owner;
    }

    public void setOwner(int owner) {
        this.owner = owner;
    }

    public boolean isDef() {
        return def;
    }

    public void setDef(boolean def) {
        this.def = def;
    }
}
