package com.tvoyagryvnia.bean.operation;


import com.tvoyagryvnia.bean.account.AccountBean;
import com.tvoyagryvnia.bean.currency.CurrencyBean;
import com.tvoyagryvnia.bean.currency.ExtendedCurrencyBean;
import com.tvoyagryvnia.bean.currency.RateBean;
import com.tvoyagryvnia.model.OperationEntity;

import java.util.Date;

public class OperationBean {

    private int id;
    private boolean active;
    private int owner;
    private AccountBean account;
    private ExtendedCurrencyBean currency;
    private RateBean rate;
    private Date date;
    private String note;
    private float money;
    private String type;
    private String category;

    public OperationBean(OperationEntity operationEntity){
        this.id = operationEntity.getId();
        this.active = operationEntity.isActive();
        this.owner = operationEntity.getOwner().getId();
        this.account = new AccountBean(operationEntity.getAccount());
        this.currency = new ExtendedCurrencyBean(operationEntity.getCurrency());
        this.rate = new RateBean(operationEntity.getCrossRate());
        this.date = operationEntity.getDate();
        this.note = operationEntity.getNote();
        this.money = operationEntity.getMoney();
        this.type = operationEntity.getCategory().getOperation().name();
        this.category = operationEntity.getCategory().getName();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public int getOwner() {
        return owner;
    }

    public void setOwner(int owner) {
        this.owner = owner;
    }

    public AccountBean getAccount() {
        return account;
    }

    public void setAccount(AccountBean account) {
        this.account = account;
    }

    public ExtendedCurrencyBean getCurrency() {
        return currency;
    }

    public void setCurrency(ExtendedCurrencyBean currency) {
        this.currency = currency;
    }

    public RateBean getRate() {
        return rate;
    }

    public void setRate(RateBean rate) {
        this.rate = rate;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public float getMoney() {
        return money;
    }

    public void setMoney(float money) {
        this.money = money;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }


}
