package com.tvoyagryvnia.bean.statistic.simple;

public class SimpleStatisticChartLine {

    private String label;
    private float value;

    public SimpleStatisticChartLine(String label,float value){
        this.label = label;
        this.value = value;
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
        this.value = value;
    }
}
