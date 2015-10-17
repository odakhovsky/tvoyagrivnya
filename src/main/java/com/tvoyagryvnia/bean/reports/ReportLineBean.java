package com.tvoyagryvnia.bean.reports;


import com.tvoyagryvnia.util.NumberFormatter;

public class ReportLineBean {

    private String label;
    private float value;

    public ReportLineBean(String lab, float val) {
        this.value = NumberFormatter.cutFloat(val,4);
        this.label = lab;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = NumberFormatter.cutFloat(value,4);
    }
}
