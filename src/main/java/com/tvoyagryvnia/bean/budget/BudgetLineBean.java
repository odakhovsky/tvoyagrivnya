package com.tvoyagryvnia.bean.budget;


import com.tvoyagryvnia.bean.category.CategoryBean;
import com.tvoyagryvnia.model.BudgetLineEntity;
import com.tvoyagryvnia.util.NumberFormatter;

public class BudgetLineBean {

    private int id;
    private CategoryBean categoryBean;
    private float money;
    private boolean active;

    public BudgetLineBean(BudgetLineEntity entity) {
        this.id = entity.getId();
        this.categoryBean = new CategoryBean(entity.getCategory());
        this.money = NumberFormatter.cutFloat(entity.getMoney(), 2);
        this.active = entity.isActive();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public CategoryBean getCategoryBean() {
        return categoryBean;
    }

    public void setCategoryBean(CategoryBean categoryBean) {
        this.categoryBean = categoryBean;
    }

    public float getMoney() {
        return money;
    }

    public void setMoney(float money) {
        this.money = NumberFormatter.cutFloat(money,2);
    }

    public boolean getActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
