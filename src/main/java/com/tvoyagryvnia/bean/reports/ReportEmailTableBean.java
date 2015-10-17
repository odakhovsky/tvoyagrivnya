package com.tvoyagryvnia.bean.reports;


import com.tvoyagryvnia.bean.operation.OperationBean;
import com.tvoyagryvnia.service.MessageBuilder;
import com.tvoyagryvnia.util.DateUtil;

public class ReportEmailTableBean {

    private ReportBean report;

    public ReportEmailTableBean(ReportBean report) {
        this.report = report;
    }

    public MessageBuilder getTable() {
        StringBuilder resultTable = new StringBuilder();
        resultTable.append(head());
        resultTable.append("<style>" +
                "td { padding: 6px; border: 1px solid #ccc; text-align: left; }" +
                "th { background: #333; color: white; font-weight: bold; padding: 6px; border: 1px solid #ccc; text-align: left;}" +
                "</style>");


        StringBuilder incomesTable = new StringBuilder();
        incomesTable.append("</table>");
        if (report.getIncomings().size() != 0) {
            incomesTable.append("<table class='table' style='margin-top:25px'>");
            incomesTable.append("<caption>Прибуток</caption>");
            float sum = 0.0f;
            for (ReportLineBean line : report.getIncomings()) {
                incomesTable.append("<tr>");
                appendDataCell(incomesTable, line.getLabel());
                appendDataCell(incomesTable, String.valueOf(line.getValue()) + " " + report.getCurrency());
                incomesTable.append("</tr>");
                sum += line.getValue();
            }
            incomesTable.append("<tr>");
            appendDataCell(incomesTable, "Загалом");
            appendDataCell(incomesTable, String.valueOf(sum) + " " + report.getCurrency());
            incomesTable.append("</tr>");
        }
        resultTable.append(incomesTable.toString());


        StringBuilder spendsTable = new StringBuilder();
        if (report.getSpendings().size() != 0) {
            spendsTable.append("<table class='table' style='margin-top:25px'>");
            spendsTable.append("<caption>Витрати</caption>");
            float sum = 0.0f;
            for (ReportLineBean line : report.getSpendings()) {
                spendsTable.append("<tr>");
                appendDataCell(spendsTable, line.getLabel());
                appendDataCell(spendsTable, String.valueOf(line.getValue()) + " " + report.getCurrency());
                spendsTable.append("</tr>");
                sum += line.getValue();
            }
            spendsTable.append("<tr>");
            appendDataCell(spendsTable, "Загалом");
            appendDataCell(spendsTable, String.valueOf(sum) + " " + report.getCurrency());
            spendsTable.append("</tr>");
            spendsTable.append("</table>");
        }
        resultTable.append(spendsTable.toString());

        StringBuilder operations = new StringBuilder();
        operations.append("<table class='table'>");
        operations.append("<caption>Операції</caption>");
        operations.append("<tr>");
        appendHeaderCell(operations, "Дата");
        appendHeaderCell(operations, "Рахунок");
        appendHeaderCell(operations, "Категорія");
        appendHeaderCell(operations, "Сума");
        appendHeaderCell(operations, "Валюта");
        operations.append("</tr>");
        for (OperationBean line : report.getOperations()) {
            operations.append("<tr>");
            appendDataCell(operations, DateUtil.getFormattedDate(line.getDate(), DateUtil.DF_POINT));
            appendDataCell(operations, line.getAccount().getName());
            appendDataCell(operations, line.getCategory());
            appendDataCell(operations, String.valueOf(line.getMoney()));
            appendDataCell(operations, line.getCurrency().getShortName());
            operations.append("</tr>");
        }
        operations.append("</table>");

        resultTable.append(operations.toString());
        resultTable.append(footer());
        return resultTable::toString;

    }


    void appendTag(StringBuilder sb, String tag, String contents) {
        sb.append('<').append(tag).append('>');
        sb.append(contents);
        sb.append("</").append(tag).append('>');
    }

    void appendDataCell(StringBuilder sb, String contents) {
        appendTag(sb, "td", contents);
    }

    void appendHeaderCell(StringBuilder sb, String contents) {
        appendTag(sb, "th", contents);
    }

    private String head() {
        String bootstrap = "" +
                "<link rel=\"stylesheet\" href=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap-theme.min.css\">\n" +
                "\n" +
                "<script src=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js\"></script>";
        return "<html><body><head>" + bootstrap + "</head>";
    }

    private String footer() {
        return "</body></html>";
    }
}
