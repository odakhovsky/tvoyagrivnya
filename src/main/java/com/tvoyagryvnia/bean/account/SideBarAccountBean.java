package com.tvoyagryvnia.bean.account;


public class SideBarAccountBean {

    private String name;
    private float total;
    private String currency;
    private int id;

    public SideBarAccountBean(AccountBean accountBean) {
        this.name = accountBean.getName();
        this.total = accountBean.getTotalBalance();
        this.currency = accountBean.getCurrency();
        this.id = accountBean.getId();
    }

    public String getName() {
        return name;
    }

    public float getTotal() {
        return total;
    }

    public String getCurrency() {
        return currency;
    }

    public int getId() {
        return id;
    }
}
