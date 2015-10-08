package com.tvoyagryvnia.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "BUDGET_LINE")
public class BudgetLineEntity {

    @Column(name = "ID")
    @Id @GeneratedValue
    private int id;

    @Column(name = "ACTIVE")
    private boolean active;

    @ManyToOne
    @JoinColumn(name = "CATEGORY")
    private UserCategoryEntity category;

    @ManyToOne
    @JoinColumn(name = "BUDGET")
    private BudgetEntity budget;

    @Column(name = "MONEY")
    private float money;

    public BudgetLineEntity(){}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public UserCategoryEntity getCategory() {
        return category;
    }

    public void setCategory(UserCategoryEntity category) {
        this.category = category;
    }

    public BudgetEntity getBudget() {
        return budget;
    }

    public void setBudget(BudgetEntity budget) {
        this.budget = budget;
    }

    public float getMoney() {
        return money;
    }

    public void setMoney(float money) {
        this.money = money;
    }
}
