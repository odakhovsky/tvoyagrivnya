package com.tvoyagryvnia.bean.reports;


import com.tvoyagryvnia.bean.operation.OperationBean;

import java.util.ArrayList;
import java.util.List;

public class ReportBean {

    private List<ReportLineBean> incomings;
    private List<ReportLineBean> spendings;
    private List<OperationBean> operations;

    public ReportBean() {
        this.incomings = new ArrayList<>();
        this.spendings = new ArrayList<>();
        this.operations = new ArrayList<>();
    }

    public List<ReportLineBean> getIncomings() {
        return incomings;
    }

    public void setIncomings(List<ReportLineBean> incomings) {
        this.incomings = incomings;
    }

    public List<ReportLineBean> getSpendings() {
        return spendings;
    }

    public void setSpendings(List<ReportLineBean> spendings) {
        this.spendings = spendings;
    }

    public List<OperationBean> getOperations() {
        return operations;
    }

    public void setOperations(List<OperationBean> operations) {
        this.operations = operations;
    }


}
