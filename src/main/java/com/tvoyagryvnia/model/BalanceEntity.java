package com.tvoyagryvnia.model;

import javax.persistence.*;

@Entity
@Table(name = "BALANCE")
public class BalanceEntity {

    @Id @GeneratedValue
    private int id;

    @OneToOne
    @JoinColumn(name = "CURRENCY")
    private UserCurrencyEntity currency;

    @ManyToOne
    @JoinColumn(name = "ACCOUNT")
    private AccountEntity account;

    @Column(name = "BALANCE")
    private float balance;

    public BalanceEntity(){}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public UserCurrencyEntity getCurrency() {
        return currency;
    }

    public void setCurrency(UserCurrencyEntity currency) {
        this.currency = currency;
    }

    public AccountEntity getAccount() {
        return account;
    }

    public void setAccount(AccountEntity account) {
        this.account = account;
    }

    public float getBalance() {
        return balance;
    }

    public void setBalance(float balance) {
        this.balance = balance;
    }

    public boolean isEmpty() {
        return (balance == 0.0f);
    }
}
