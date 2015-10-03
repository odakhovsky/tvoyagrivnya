package com.tvoyagryvnia.bean.account;

import com.tvoyagryvnia.model.AccountEntity;
import com.tvoyagryvnia.model.BalanceEntity;
import com.tvoyagryvnia.model.UserCurrencyEntity;

import java.util.List;
import java.util.stream.Collectors;


public class AccountBean {

    private int id;
    private String name;
    private String description;
    private List<BalanceBean> balances;
    private float totalBalance;
    private boolean active;
    private boolean enabled;
    private int owner;
    private String currency;

    public AccountBean(AccountEntity accountEntity) {
        this.id = accountEntity.getId();
        this.name = accountEntity.getName();
        this.description = accountEntity.getDescription();
        this.balances = accountEntity.getBalances().stream().map(BalanceBean::new).collect(Collectors.toList());
        this.active = accountEntity.isActive();
        this.enabled = accountEntity.isEnabled();
        this.owner = accountEntity.getOwner().getId();
        this.totalBalance = calcTotalBalance();
        this.currency = accountEntity.getBalances().stream()
                .map(BalanceEntity::getCurrency)
                .filter(UserCurrencyEntity::isDef)
                .map(curr -> curr.getCurrency().getShortName()).findFirst().get();
    }

    private float calcTotalBalance() {
        float total = 0;
        for (BalanceBean b : balances) {
            total += b.getBalance() * b.getRate();
        }
        return total;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<BalanceBean> getBalances() {
        return balances;
    }

    public void setBalances(List<BalanceBean> balances) {
        this.balances = balances;
    }

    public float getTotalBalance() {
        return totalBalance;
    }

    public void setTotalBalance(float totalBalance) {
        this.totalBalance = totalBalance;
    }

    public boolean getActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public boolean isActive() {
        return active;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public int getOwner() {
        return owner;
    }

    public void setOwner(int owner) {
        this.owner = owner;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }
}
