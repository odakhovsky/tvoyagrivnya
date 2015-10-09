package com.tvoyagryvnia.dao;


import com.tvoyagryvnia.model.BudgetEntity;
import com.tvoyagryvnia.model.BudgetLineEntity;

import java.util.Date;
import java.util.List;

public interface IBudgetDao {

    void save(BudgetEntity budgetEntity);
    void update(BudgetEntity budgetEntity);
    List<BudgetEntity> getAllOfUser(int user, boolean active);
    BudgetEntity getById(int id);
    void deactivate(BudgetEntity budgetEntity);

    void addLineToBudget(BudgetLineEntity lineEntity);
    void removeLine(BudgetLineEntity lineEntity);

    List<BudgetLineEntity> getAllForBudget(int budget, boolean active);
    BudgetLineEntity getByBudgetAndCategory(int category, int budget);

    void removeLine(int lineId);
    BudgetEntity getBudgetWithRange(Date from, Date to);
}

