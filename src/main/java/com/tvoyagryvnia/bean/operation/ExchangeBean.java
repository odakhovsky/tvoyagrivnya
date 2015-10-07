package com.tvoyagryvnia.bean.operation;

import com.tvoyagryvnia.bean.account.AccountBean;
import com.tvoyagryvnia.bean.currency.ExtendedCurrencyBean;
import com.tvoyagryvnia.bean.currency.RateBean;
import com.tvoyagryvnia.model.ExchangeEntity;
import com.tvoyagryvnia.model.OperationEntity;

public class ExchangeBean extends OperationBean {

    private AccountBean accountTo;
    private ExtendedCurrencyBean currencyTo;
    private RateBean rateTo;
    private float moneyTo;

    public ExchangeBean(ExchangeEntity exchangeEntity) {
        super(getEntity(exchangeEntity));
        this.accountTo = new AccountBean(exchangeEntity.getAccountTo());
        this.currencyTo = new ExtendedCurrencyBean(exchangeEntity.getCurrencyTo());
        this.rateTo = new RateBean(exchangeEntity.getCrossRateTo());
        this.moneyTo = exchangeEntity.getMoneyTo();
    }

    private static OperationEntity getEntity(ExchangeEntity exchangeEntity) {
        OperationEntity entity = new OperationEntity();
        entity.setCrossRate(exchangeEntity.getCrossRate());
        entity.setMoney(exchangeEntity.getMoney());
        entity.setNote(exchangeEntity.getNote());
        entity.setCurrency(exchangeEntity.getCurrency());
        entity.setAccount(exchangeEntity.getAccount());
        entity.setActive(exchangeEntity.isActive());
        entity.setCategory(exchangeEntity.getCategory());
        entity.setDate(exchangeEntity.getDate());
        entity.setId(exchangeEntity.getId());
        entity.setOwner(exchangeEntity.getOwner());
        return entity;
    }

    public AccountBean getAccountTo() {
        return accountTo;
    }

    public void setAccountTo(AccountBean accountTo) {
        this.accountTo = accountTo;
    }

    public ExtendedCurrencyBean getCurrencyTo() {
        return currencyTo;
    }

    public void setCurrencyTo(ExtendedCurrencyBean currencyTo) {
        this.currencyTo = currencyTo;
    }

    public RateBean getRateTo() {
        return rateTo;
    }

    public void setRateTo(RateBean rateTo) {
        this.rateTo = rateTo;
    }

    public float getMoneyTo() {
        return moneyTo;
    }

    public void setMoneyTo(float moneyTo) {
        this.moneyTo = moneyTo;
    }
}
