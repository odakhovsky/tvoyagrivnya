package com.tvoyagryvnia.bean.budget;


import com.tvoyagryvnia.util.NumberFormatter;

public class FullBudgetResultLine {

    private float fact;
    private float budget;
    private float diff;

    public FullBudgetResultLine() {
    }

    public float getFact() {
        return fact;
    }

    public void setFact(float fact) {
        this.fact = NumberFormatter.cutFloat(fact, 2);
    }

    public float getBudget() {
        return budget;
    }

    public void setBudget(float budget) {
        this.budget = NumberFormatter.cutFloat(budget, 2);
    }

    public float getDiff() {
        return diff;
    }

    public void setDiff(float diff) {
        this.diff = NumberFormatter.cutFloat(diff, 2);
    }

}

