package com.tvoyagryvnia.model;

import javax.persistence.*;

@Entity
@Table(name = "EXCHANGE")
@PrimaryKeyJoinColumn(name = "OPERATION_ID", referencedColumnName = "ID")
public class ExchangeEntity  extends OperationEntity
{

    @ManyToOne
    @JoinColumn(name = "ACCOUNT_TO")
    private AccountEntity accountTo;

    @ManyToOne
    @JoinColumn(name = "CURRENCY_TO")
    private UserCurrencyEntity currencyTo;

    @ManyToOne
    @JoinColumn(name = "CURRENCY_CROSS_RATE_TO")
    private RateEntity crossRateTo;

    @Column(name = "MONEY_TO")
    private float moneyTo;

    public ExchangeEntity(){}

    public AccountEntity getAccountTo() {
        return accountTo;
    }

    public void setAccountTo(AccountEntity accountTo) {
        this.accountTo = accountTo;
    }

    public UserCurrencyEntity getCurrencyTo() {
        return currencyTo;
    }

    public void setCurrencyTo(UserCurrencyEntity currencyTo) {
        this.currencyTo = currencyTo;
    }

    public RateEntity getCrossRateTo() {
        return crossRateTo;
    }

    public void setCrossRateTo(RateEntity crossRateTo) {
        this.crossRateTo = crossRateTo;
    }

    public float getMoneyTo() {
        return moneyTo;
    }

    public void setMoneyTo(float moneyTo) {
        this.moneyTo = moneyTo;
    }

}
