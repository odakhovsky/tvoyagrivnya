package com.tvoyagryvnia.bean.budget;


import com.tvoyagryvnia.model.BudgetEntity;

import java.util.ArrayList;
import java.util.List;

public class FullBudgetBean {

    private int id;
    private int owner;
    private List<FullBudgetLineBean> incomes;
    private List<FullBudgetLineBean> spending;
    private String name;
    private boolean active;
    private String currency;

    public FullBudgetResultLine getGrandTotal() {
        FullBudgetResultLine result = new FullBudgetResultLine();
        result.setFact(getIncomesTotal().getFact() - getSpendingTotal().getFact());
        result.setBudget(getIncomesTotal().getBudget() - getSpendingTotal().getBudget());
        result.setDiff(getIncomesTotal().getDiff() - getSpendingTotal().getDiff());
        return result;
    }

    public FullBudgetResultLine getIncomesTotal() {
        FullBudgetResultLine result = new FullBudgetResultLine();
        for (FullBudgetLineBean line : incomes) {
            result.setBudget(result.getBudget() + line.getLine().getMoney());
            result.setFact(result.getFact() + line.getFactMoney());
            result.setDiff( result.getFact() - result.getBudget());
        }
        return result;
    }
    public FullBudgetResultLine getSpendingTotal() {
        FullBudgetResultLine result = new FullBudgetResultLine();
        for (FullBudgetLineBean line : spending) {
            result.setBudget(result.getBudget() + line.getLine().getMoney());
            result.setFact(result.getFact() + line.getFactMoney());
            result.setDiff(result.getBudget() - result.getFact());
        }
        return result;
    }


    public FullBudgetBean() {
        incomes = new ArrayList<>();
        spending = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getOwner() {
        return owner;
    }

    public void setOwner(int owner) {
        this.owner = owner;
    }

    public List<FullBudgetLineBean> getIncomes() {
        return incomes;
    }

    public void setIncomes(List<FullBudgetLineBean> incomes) {
        this.incomes = incomes;
    }

    public List<FullBudgetLineBean> getSpending() {
        return spending;
    }

    public void setSpending(List<FullBudgetLineBean> spending) {
        this.spending = spending;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }
}
