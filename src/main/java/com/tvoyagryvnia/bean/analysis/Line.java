package com.tvoyagryvnia.bean.analysis;

import java.util.ArrayList;
import java.util.List;

public class Line {
    private int id;
    private String name;
    private int value;
    private float percent;
    private float money;
    private Float diff;
    private String curr;
    private List<Line> sublines;

    public Line() {
        sublines = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public float getPercent() {
        return percent;
    }

    public void setPercent(float percent) {
        this.percent = percent;
    }

    public float getMoney() {
        return money;
    }

    public void setMoney(float money) {
        this.money = money;
    }

    public String getCurr() {
        return curr;
    }

    public void setCurr(String curr) {
        this.curr = curr;
    }

    public List<Line> getSublines() {
        return sublines;
    }

    public void setSublines(List<Line> sublines) {
        this.sublines = sublines;
    }

    public Float getDiff() {
        return diff;
    }

    public void setDiff(Float diff) {
        this.diff = diff;
    }
}
