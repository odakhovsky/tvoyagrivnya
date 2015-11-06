package com.tvoyagryvnia.bean.budget;

import com.tvoyagryvnia.model.BudgetEntity;
import com.tvoyagryvnia.model.enums.OperationType;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class SimpleBudgetBean {

    private int id;
    private Date from;
    private Date to;
    private String name;
    private int owner;
    private List<BudgetLineBean> incomes;
    private List<BudgetLineBean> spending;
    private boolean active;

    public SimpleBudgetBean(BudgetEntity budgetEntity) {
        this.id = budgetEntity.getId();
        this.from = budgetEntity.getFrom();
        this.to = budgetEntity.getTo();
        this.owner = budgetEntity.getOwner().getId();
        this.name = budgetEntity.getName();
        this.incomes = budgetEntity.getCategories()
                .stream()
                .filter(b -> b.getCategory().getOperation().equals(OperationType.plus))
                .map(BudgetLineBean::new).collect(Collectors.toList());
        this.spending = budgetEntity.getCategories()
                .stream()
                .filter(b -> b.getCategory().getOperation().equals(OperationType.minus))
                .map(BudgetLineBean::new).collect(Collectors.toList());
        this.active = budgetEntity.isActive();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getFrom() {
        return from;
    }

    public void setFrom(Date from) {
        this.from = from;
    }

    public Date getTo() {
        return to;
    }

    public void setTo(Date to) {
        this.to = to;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getOwner() {
        return owner;
    }

    public void setOwner(int owner) {
        this.owner = owner;
    }

    public List<BudgetLineBean> getIncomes() {
        return incomes;
    }

    public void setIncomes(List<BudgetLineBean> incomes) {
        this.incomes = incomes;
    }

    public List<BudgetLineBean> getSpending() {
        return spending;
    }

    public void setSpending(List<BudgetLineBean> spending) {
        this.spending = spending;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
