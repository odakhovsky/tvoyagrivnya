package com.tvoyagryvnia.bean.budget;

import com.tvoyagryvnia.util.NumberFormatter;

public class FullBudgetLineBean {

    public float factMoney;
    public float diff;
    private BudgetLineBean line;
    private String category;

    public FullBudgetLineBean(float fact, BudgetLineBean budgetLineBean) {
        this.factMoney = fact;
        this.line = budgetLineBean;
    }

    public FullBudgetLineBean() {
    }

    public float getFactMoney() {
        return factMoney;
    }

    public void setFactMoney(float factMoney) {
        this.factMoney = NumberFormatter.cutFloat(factMoney, 2);
    }

    public float getDiff() {
        return diff;
    }

    public void setDiff(float diff) {
        this.diff = NumberFormatter.cutFloat(diff, 2);
    }

    public BudgetLineBean getLine() {
        return line;
    }

    public void setLine(BudgetLineBean line) {
        this.line = line;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
