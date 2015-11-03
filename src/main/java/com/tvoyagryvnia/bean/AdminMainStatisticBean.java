package com.tvoyagryvnia.bean;

import com.tvoyagryvnia.util.NumberFormatter;

import java.util.Map;

public class AdminMainStatisticBean {

    private int users;
    private int accounts;
    private Map<String, Float> middleMoney;
    private float middleMoneyToMonth;
    private float totalSpendMoney;
    private float totalIncomingMoney;

    public AdminMainStatisticBean() {
    }

    public AdminMainStatisticBean(int users, int accounts, Map<String, Float> middleMoney, float middleMoneyToMonth, float totalSpendMoney, float totalIncomingMoney) {
        this.users = users;
        this.accounts = accounts;
        this.middleMoney = middleMoney;
        this.middleMoneyToMonth = middleMoneyToMonth;
        this.totalSpendMoney = totalSpendMoney;
        this.totalIncomingMoney = totalIncomingMoney;
    }

    public int getUsers() {
        return users;
    }

    public void setUsers(int users) {
        this.users = users;
    }

    public int getAccounts() {
        return accounts;
    }

    public void setAccounts(int accounts) {
        this.accounts = accounts;
    }

    public Map<String, Float> getMiddleMoney() {
        return middleMoney;
    }

    public void setMiddleMoney(Map<String, Float> middleMoney) {
        this.middleMoney = middleMoney;
    }

    public float getMiddleMoneyToMonth() {
        return middleMoneyToMonth;
    }

    public void setMiddleMoneyToMonth(float middleMoneyToMonth) {
        this.middleMoneyToMonth = middleMoneyToMonth;
    }

    public float getTotalSpendMoney() {
        return totalSpendMoney;
    }

    public void setTotalSpendMoney(float totalSpendMoney) {
        this.totalSpendMoney = totalSpendMoney;
    }

    public float getTotalIncomingMoney() {
        return totalIncomingMoney;
    }

    public void setTotalIncomingMoney(float totalIncomingMoney) {
        this.totalIncomingMoney = totalIncomingMoney;
    }
}
