package com.tvoyagryvnia.bean.operation;


public class NewOperation {

    private String operationtype;
    private String date;
    private String description;
    private float money;
    private int accounts;
    private int currency;
    private int category;

    public NewOperation(){}

    public String getOperationtype() {
        return operationtype;
    }

    public void setOperationtype(String operationtype) {
        this.operationtype = operationtype;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public float getMoney() {
        return money;
    }

    public void setMoney(float money) {
        this.money = money;
    }

    public int getAccounts() {
        return accounts;
    }

    public void setAccounts(int accounts) {
        this.accounts = accounts;
    }

    public int getCurrency() {
        return currency;
    }

    public void setCurrency(int currency) {
        this.currency = currency;
    }

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return "NewOperation{" +
                "operationtype='" + operationtype + '\'' +
                ", date='" + date + '\'' +
                ", description='" + description + '\'' +
                ", money=" + money +
                ", accounts=" + accounts +
                ", currency=" + currency +
                ", category=" + category +
                '}';
    }
}
