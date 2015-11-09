package com.tvoyagryvnia.bean.analysis;


import com.tvoyagryvnia.bean.Pair;

import java.util.Map;

public class ExtendedAnalysisBean {

    private String range;
    private AnalysisBean first;
    private AnalysisBean second;
    private String curr;

    public ExtendedAnalysisBean() {
    }

    public String getRange() {
        return range;
    }

    public void setRange(String range) {
        this.range = range;
    }

    public AnalysisBean getFirst() {
        return first;
    }

    public void setFirst(AnalysisBean first) {
        this.first = first;
    }

    public AnalysisBean getSecond() {
        return second;
    }

    public void setSecond(AnalysisBean second) {
        this.second = second;
    }

    public String getCurr() {
        return curr;
    }

    public void setCurr(String curr) {
        this.curr = curr;
    }
}
