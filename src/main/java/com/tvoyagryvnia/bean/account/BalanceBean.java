package com.tvoyagryvnia.bean.account;

import com.tvoyagryvnia.model.BalanceEntity;
import com.tvoyagryvnia.util.NumberFormatter;

public class BalanceBean {

    private int id;
    private int currencyId;
    private String currName;
    private String currFull;
    private String currShort;
    private float balance;
    private float rate;
    private boolean isDefCurrency;

    public BalanceBean(BalanceEntity balanceEntity) {
        this.id = balanceEntity.getId();
        this.currencyId = balanceEntity.getCurrency().getId();
        this.currName = balanceEntity.getCurrency().getCurrency().getCurrency();
        this.currFull = balanceEntity.getCurrency().getCurrency().getName();
        this.currShort = balanceEntity.getCurrency().getCurrency().getShortName();
        this.balance = NumberFormatter.cutFloat(balanceEntity.getBalance(), 2);
        this.rate = balanceEntity.getCurrency().getCrossRate();
        this.isDefCurrency = balanceEntity.getCurrency().isDef();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCurrencyId() {
        return currencyId;
    }

    public void setCurrencyId(int currencyId) {
        this.currencyId = currencyId;
    }

    public String getCurrName() {
        return currName;
    }

    public void setCurrName(String currName) {
        this.currName = currName;
    }

    public String getCurrFull() {
        return currFull;
    }

    public void setCurrFull(String currFull) {
        this.currFull = currFull;
    }

    public String getCurrShort() {
        return currShort;
    }

    public void setCurrShort(String currShort) {
        this.currShort = currShort;
    }

    public float getBalance() {
        return balance;
    }

    public void setBalance(float balance) {
        this.balance = balance;
    }

    public float getRate() {
        return rate;
    }

    public void setRate(float rate) {
        this.rate = rate;
    }

    public boolean isDefCurrency() {
        return isDefCurrency;
    }

    public void setDefCurrency(boolean defCurrency) {
        isDefCurrency = defCurrency;
    }
}
