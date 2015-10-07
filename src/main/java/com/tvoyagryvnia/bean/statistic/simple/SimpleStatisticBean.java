package com.tvoyagryvnia.bean.statistic.simple;


import com.tvoyagryvnia.bean.operation.OperationBean;
import com.tvoyagryvnia.model.enums.OperationType;
import com.tvoyagryvnia.util.NumberFormatter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class SimpleStatisticBean {

    private List<SimpleStatisticChartLine> incoming;
    private List<SimpleStatisticChartLine> spending;
    private String currency;

    public SimpleStatisticBean(List<OperationBean> operations) {
        spending = new ArrayList<>();
        incoming = new ArrayList<>();
        parseList(operations, OperationType.plus);
        parseList(operations, OperationType.minus);
        if (operations.size() > 0) {
            currency = operations.stream().filter(o -> o.getCurrency().isDef()).map(o -> o.getCurrency().getShortName()).findFirst().get();

        }
    }

    private void parseList(List<OperationBean> operations, OperationType type) {
        Map<String, List<OperationBean>> incomingMap = operations.stream()
                .filter(operation -> operation.getType().equals(type.name()))
                .collect(Collectors.groupingBy(OperationBean::getCategory));

        fillList(incomingMap, type);
    }

    private void fillList(Map<String, List<OperationBean>> incomingMap, OperationType type) {
        for (String key : incomingMap.keySet()) {
            float sum = 0.0f;
            for (OperationBean op : incomingMap.get(key)) {
                sum += op.getMoney() * op.getCurrency().getCrossRate();
            }

            if (0 == NumberFormatter.cutFloat(sum, 2)) {
                sum = NumberFormatter.cutFloat(sum, 3);
            } else {
                sum = NumberFormatter.cutFloat(sum, 2);
            }

            SimpleStatisticChartLine line = new SimpleStatisticChartLine(key, sum);
            switch (type) {
                case plus:
                    incoming.add(line);
                    break;
                case minus:
                    spending.add(line);
                    break;
            }
        }
    }

    public List<SimpleStatisticChartLine> getIncoming() {
        return incoming;
    }

    public void setIncoming(List<SimpleStatisticChartLine> incoming) {
        this.incoming = incoming;
    }

    public List<SimpleStatisticChartLine> getSpending() {
        return spending;
    }

    public void setSpending(List<SimpleStatisticChartLine> spending) {
        this.spending = spending;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }
}
