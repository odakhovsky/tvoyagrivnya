package com.tvoyagryvnia.bean.analysis;


import java.util.List;

public class AnalysisBean {

    private String range;
    private List<Line> categories;
    private float total;
    private String curr;

    public AnalysisBean() {

    }

    public float getTotal() {
        return total;
    }

    public void setTotal(float total) {
        this.total = total;
    }

    public String getCurr() {
        return curr;
    }

    public void setCurr(String curr) {
        this.curr = curr;
    }

    public String getRange() {
        return range;
    }

    public void setRange(String range) {
        this.range = range;
    }

    public List<Line> getCategories() {
        return categories;
    }

    public void setCategories(List<Line> categories) {
        this.categories = categories;
    }

}
