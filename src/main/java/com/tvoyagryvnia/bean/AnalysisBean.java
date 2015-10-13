package com.tvoyagryvnia.bean;


import java.util.List;

public class AnalysisBean {

    private String range;
    private List<Line> categories;

    public AnalysisBean(){

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
