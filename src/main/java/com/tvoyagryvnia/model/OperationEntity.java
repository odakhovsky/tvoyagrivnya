package com.tvoyagryvnia.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "OPERATION")
@Inheritance(strategy= InheritanceType.JOINED)
public class OperationEntity {

    @Id @GeneratedValue
    private int id;

    @Column(name = "ACTIVE")
    private boolean active;

    @Column(name = "DATE")
    @Temporal(TemporalType.DATE)
    private Date date;

    @ManyToOne
    @JoinColumn(name = "OWNER")
    private UserEntity owner;

    @ManyToOne
    @JoinColumn(name = "ACCOUNT")
    private AccountEntity account;

    @ManyToOne
    @JoinColumn(name = "CATEGORY")
    private UserCategoryEntity category;

    @ManyToOne
    @JoinColumn(name = "CURRENCY")
    private UserCurrencyEntity currency;

    @ManyToOne
    @JoinColumn(name = "CURRENCY_CROSS_RATE")
    private RateEntity crossRate;

    @Column(name = "MONEY")
    private float money;

    @Column(name = "NOTE")
    private String note;

    public OperationEntity(){}

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

    public UserEntity getOwner() {
        return owner;
    }

    public void setOwner(UserEntity owner) {
        this.owner = owner;
    }

    public AccountEntity getAccount() {
        return account;
    }

    public void setAccount(AccountEntity account) {
        this.account = account;
    }

    public UserCategoryEntity getCategory() {
        return category;
    }

    public void setCategory(UserCategoryEntity category) {
        this.category = category;
    }

    public UserCurrencyEntity getCurrency() {
        return currency;
    }

    public void setCurrency(UserCurrencyEntity currency) {
        this.currency = currency;
    }

    public RateEntity getCrossRate() {
        return crossRate;
    }

    public void setCrossRate(RateEntity crossRate) {
        this.crossRate = crossRate;
    }

    public float getMoney() {
        return money;
    }

    public void setMoney(float money) {
        this.money = money;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
