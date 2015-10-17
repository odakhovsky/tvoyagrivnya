package com.tvoyagryvnia.service;


import com.tvoyagryvnia.bean.budget.BudgetLineBean;
import com.tvoyagryvnia.bean.budget.FullBudgetBean;
import com.tvoyagryvnia.bean.budget.SimpleBudgetBean;
import com.tvoyagryvnia.model.BudgetEntity;
import com.tvoyagryvnia.model.BudgetLineEntity;

import java.util.Date;
import java.util.List;

public interface IBudgetService {

    int create(String date, Integer id);
    void save(SimpleBudgetBean budget);
    void update(SimpleBudgetBean budget);
    List<SimpleBudgetBean> getAllOfUser(int user, boolean active);
    SimpleBudgetBean getById(int id);
    void deactivate(int budgetId);

    void addLineToBudget(BudgetLineBean line,int budget);
    void removeLine(int lineId);

    List<BudgetLineBean> getAllForBudget(int budget, boolean active);

    FullBudgetBean getFullBudget(int budget);

    boolean isPresentBudgetForDateRange(Date from, Date to);

    void addLineToBudget(Integer budgetId, Integer category, Float money);

}
