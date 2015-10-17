package com.tvoyagryvnia.dao.impl;

import com.tvoyagryvnia.dao.IBudgetDao;
import com.tvoyagryvnia.model.BudgetEntity;
import com.tvoyagryvnia.model.BudgetLineEntity;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public class BudgetDaoImpl implements IBudgetDao {

    @Autowired private SessionFactory sessionFactory;

    private Session getSession(){return  sessionFactory.getCurrentSession();}

    @Override
    public void save(BudgetEntity budgetEntity) {
        getSession().save(budgetEntity);
    }

    @Override
    public void update(BudgetEntity budgetEntity) {
        getSession().update(budgetEntity);
    }

    @Override
    public List<BudgetEntity> getAllOfUser(int user, boolean active) {
        return getSession()
                .createCriteria(BudgetEntity.class,"b")
                .createAlias("b.owner","o")
                .add(Restrictions.and(
                        Restrictions.eq("o.id", user),
                        Restrictions.eq("b.active", active)
                )).list();
    }

    @Override
    public BudgetEntity getById(int id) {
        return (BudgetEntity) getSession().get(BudgetEntity.class, id);
    }

    @Override
    public void deactivate(BudgetEntity budgetEntity) {
        BudgetEntity b = getById(budgetEntity.getId());
        b.setActive(false);
        update(b);
    }

    @Override
    public void addLineToBudget(BudgetLineEntity lineEntity) {
        getSession().saveOrUpdate(lineEntity);
    }

    @Override
    public void removeLine(BudgetLineEntity lineEntity) {
        BudgetLineEntity line = (BudgetLineEntity) getSession().get(BudgetLineEntity.class, lineEntity.getId());
        line.setActive(false);
        getSession().update(line);
    }

    @Override
    public List<BudgetLineEntity> getAllForBudget(int budget, boolean active) {
        return getSession()
                .createCriteria(BudgetLineEntity.class,"bl")
                .createAlias("bl.budget","b")
                .add(Restrictions.and(
                        Restrictions.eq("b.id", budget),
                        Restrictions.eq("bl.active", active)
                )).list();
    }

    @Override
    public BudgetLineEntity getByBudgetAndCategory(int category, int budget) {
        return (BudgetLineEntity) getSession().createCriteria(BudgetLineEntity.class, "bl")
                .createAlias("bl.budget", "bu")
                .createAlias("bl.category", "cat")
                .add(Restrictions.and(
                        Restrictions.eq("cat.id", category),
                        Restrictions.eq("bu.id", budget),
                        Restrictions.eq("bu.active", true)
                )).uniqueResult();
    }

    @Override
    public void removeLine(int lineId) {
        BudgetLineEntity line = (BudgetLineEntity) getSession().get(BudgetLineEntity.class, lineId);
        if (null != line) {
            line.setActive(false);
            getSession().update(line);
        }
    }

    @Override
    public BudgetEntity getBudgetWithRange(Date from, Date to) {
        return (BudgetEntity) getSession().createCriteria(BudgetEntity.class)
                .add(Restrictions.eq("from", from))
                .add(Restrictions.eq("to", to))
                .uniqueResult();
    }
}
