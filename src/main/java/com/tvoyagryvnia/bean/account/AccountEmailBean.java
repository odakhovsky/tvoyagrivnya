package com.tvoyagryvnia.bean.account;


import com.tvoyagryvnia.model.AccountEntity;
import com.tvoyagryvnia.model.BalanceEntity;
import com.tvoyagryvnia.model.OperationEntity;
import com.tvoyagryvnia.util.DateUtil;

import java.util.List;

public class AccountEmailBean {

    public static String buildTable(List<AccountEntity> accountEntity) {

        StringBuilder builder = new StringBuilder();
        builder.append(head());
        for (AccountEntity acc : accountEntity) {
            builder.append("<p>");
            builder.append("Номер #")
                    .append(String.valueOf(acc.getId())).append(" ")
                    .append("<b>").append(acc.getName()).append("</b>").append(" ")
                    .append("Опис ").append(acc.getDescription())
                    .append("</p>");
        }

        if (accountEntity.size() == 1) {
            builder.append("<p>");
            for (BalanceEntity bal : accountEntity.get(0).getBalances()) {
                builder.append(bal.getBalance())
                        .append(" ")
                        .append(bal.getCurrency().getCurrency().getShortName()).append(",");
            }
            builder.append("</p>");
        }

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
        for (AccountEntity acc : accountEntity) {
            for (OperationEntity line : acc.getOperations()) {
                operations.append("<tr>");
                appendDataCell(operations, DateUtil.getFormattedDate(line.getDate(), DateUtil.DF_POINT));
                appendDataCell(operations, line.getAccount().getName());
                appendDataCell(operations, line.getCategory().getName());
                appendDataCell(operations, String.valueOf(line.getMoney()));
                appendDataCell(operations, line.getCurrency().getCurrency().getShortName());
                operations.append("</tr>");
            }
        }
        builder.append(operations.toString());

        builder.append("</table>");
        builder.append(footer());
        return builder.toString();
    }


    static void appendTag(StringBuilder sb, String tag, String contents) {
        sb.append('<').append(tag).append('>');
        sb.append(contents);
        sb.append("</").append(tag).append('>');
    }

    static void appendDataCell(StringBuilder sb, String contents) {
        appendTag(sb, "td", contents);
    }

    static void appendHeaderCell(StringBuilder sb, String contents) {
        appendTag(sb, "th", contents);
    }

    static private String head() {
        return "<html><body>";
    }

    static private String footer() {
        return "</body></html>";
    }

}
