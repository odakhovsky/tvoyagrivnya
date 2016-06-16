package com.tvoyagryvnia.service.impl;

import com.tvoyagryvnia.bean.budget.BudgetLineBean;
import com.tvoyagryvnia.bean.budget.FullBudgetBean;
import com.tvoyagryvnia.bean.budget.FullBudgetLineBean;
import com.tvoyagryvnia.bean.budget.SimpleBudgetBean;
import com.tvoyagryvnia.dao.IBudgetDao;
import com.tvoyagryvnia.dao.IUserCategoryDao;
import com.tvoyagryvnia.dao.IUserDao;
import com.tvoyagryvnia.model.BudgetEntity;
import com.tvoyagryvnia.model.BudgetLineEntity;
import com.tvoyagryvnia.model.UserCategoryEntity;
import com.tvoyagryvnia.model.enums.OperationType;
import com.tvoyagryvnia.service.IBudgetService;
import com.tvoyagryvnia.service.IUserCategoryService;
import com.tvoyagryvnia.service.IUserCurrencyService;
import com.tvoyagryvnia.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class BudgetServiceImpl implements IBudgetService {

    @Autowired
    private IBudgetDao budgetDao;
    @Autowired
    private IUserDao userDao;
    @Autowired
    private IUserCategoryDao userCategoryDao;
    @Autowired
    private IUserCategoryService userCategoryService;
    @Autowired
    private IUserCurrencyService userCurrencyService;

    @Override
    public int create(String date, Integer ownerid) {
        Date from = DateUtil.parseDate(date.split(" - ")[0], DateUtil.DF_POINT.toPattern());
        Date to = DateUtil.parseDate(date.split(" - ")[1], DateUtil.DF_POINT.toPattern());
        boolean isPresent = isPresentBudgetForDateRange(from, to);
        if (isPresent) return 0;

        BudgetEntity budgetEntity = new BudgetEntity();
        String name;
        if (from.equals(to)) {
            name = DateUtil.getFormattedDate(from);
        } else {
            name = date;
        }
        budgetEntity.setFrom(from);
        budgetEntity.setTo(to);
        budgetEntity.setOwner(userDao.getUserById(ownerid));
        budgetEntity.setName(name);
        budgetEntity.setActive(true);
        budgetDao.save(budgetEntity);
        return budgetEntity.getId();
    }

    @Override
    public void save(SimpleBudgetBean budget) {

    }

    @Override
    public void update(SimpleBudgetBean budget) {

    }

    @Override
    public List<SimpleBudgetBean> getAllOfUser(int user, boolean active) {

        return budgetDao.getAllOfUser(user, active).stream().map(SimpleBudgetBean::new)
                .collect(Collectors.toList());
    }

    @Override
    public SimpleBudgetBean getById(int id) {
        return new SimpleBudgetBean(budgetDao.getById(id));
    }

    @Override
    public void deactivate(int budgetId) {
        BudgetEntity budgetEntity = budgetDao.getById(budgetId);
        budgetEntity.setActive(false);
        budgetDao.update(budgetEntity);
    }

    @Override
    public void addLineToBudget(BudgetLineBean line, int budget) {
        BudgetLineEntity bl = budgetDao.getByBudgetAndCategory(line.getCategoryBean().getId(), budget);
        if (null != bl) {
            bl.setMoney(line.getMoney());
            budgetDao.addLineToBudget(bl);
        } else {
            bl = new BudgetLineEntity();
            bl.setMoney(line.getMoney());
            bl.setActive(true);
            bl.setBudget(budgetDao.getById(budget));
            bl.setCategory(userCategoryDao.getById(line.getCategoryBean().getId()));
            budgetDao.addLineToBudget(bl);
        }
    }

    @Override
    public void removeLine(int lineId) {
        budgetDao.removeLine(lineId);
    }

    @Override
    public List<BudgetLineBean> getAllForBudget(int budget, boolean active) {
        return budgetDao.getAllForBudget(budget, true)
                .stream().map(BudgetLineBean::new).collect(Collectors.toList());
    }

    @Override
    public FullBudgetBean getFullBudget(int budget) {
        FullBudgetBean fullBudgetBean = new FullBudgetBean();
        BudgetEntity bud = budgetDao.getById(budget);
        Date from = bud.getFrom();
        Date to = bud.getTo();
        fullBudgetBean.setOwner(bud.getOwner().getId());
        fullBudgetBean.setId(bud.getId());
        fullBudgetBean.setName(bud.getName());
        fullBudgetBean.setActive(bud.isActive());
        for (BudgetLineEntity line : bud.getCategories()) {
            FullBudgetLineBean bean = new FullBudgetLineBean();
            UserCategoryEntity category = userCategoryDao.getById(line.getCategory().getId());
            bean.setFactMoney(userCategoryService
                    .getSumOfCategoryAndSubCategoriesIfPresent(category.getId(), from, to));
            bean.setLine(new BudgetLineBean(line));
            if (category.getOperation().equals(OperationType.plus)) {
                bean.setDiff(bean.getFactMoney() - bean.getLine().getMoney());
            } else {
                bean.setDiff(bean.getLine().getMoney() - bean.getFactMoney());
            }
            bean.setCategory(line.getCategory().getName());
            if (line.getCategory().getOperation().equals(OperationType.plus)) {
                fullBudgetBean.getIncomes().add(bean);
            } else if (line.getCategory().getOperation().equals(OperationType.minus)) {
                fullBudgetBean.getSpending().add(bean);
            }
        }
        String currency = userCurrencyService.getDefaultCurrencyOfUser(bud.getOwner().getId()).getShortName();
        fullBudgetBean.setCurrency(currency);
        return fullBudgetBean;
    }

    @Override
    public boolean isPresentBudgetForDateRange(Date from, Date to) {
        return (null != budgetDao.getBudgetWithRange(from, to));
    }

    @Override
    public void addLineToBudget(Integer budgetId, Integer category, Float money) {
        BudgetLineEntity bl = budgetDao.getByBudgetAndCategory(category, budgetId);
        if (null != bl) {
            bl.setMoney(money);
            bl.setActive(true);
            budgetDao.addLineToBudget(bl);
        } else {
            bl = new BudgetLineEntity();
            bl.setMoney(money);
            bl.setActive(true);
            bl.setBudget(budgetDao.getById(budgetId));
            bl.setCategory(userCategoryDao.getById(category));
            budgetDao.addLineToBudget(bl);
        }
    }
}
