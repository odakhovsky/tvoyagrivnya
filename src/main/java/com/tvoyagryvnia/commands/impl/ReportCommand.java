package com.tvoyagryvnia.commands.impl;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;
import com.tvoyagryvnia.bean.reports.ReportBean;
import com.tvoyagryvnia.bean.reports.ReportEmailTableBean;
import com.tvoyagryvnia.bean.reports.ReportFilter;
import com.tvoyagryvnia.bean.user.UserBean;
import com.tvoyagryvnia.commands.Command;
import com.tvoyagryvnia.commands.EmailMessage;
import com.tvoyagryvnia.commands.help.CommandDateParser;
import com.tvoyagryvnia.service.ISendMailService;
import com.tvoyagryvnia.service.IUserService;
import com.tvoyagryvnia.service.MessageBuilder;
import com.tvoyagryvnia.service.impl.ReportService;
import com.tvoyagryvnia.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component(value = "report")
@Parameters(commandNames = {"report"}, commandDescription = "статистика витрат та доходів")
public class ReportCommand implements Command {

    private CommandDateParser dateParser = new CommandDateParser();
    @Autowired
    private ISendMailService sendMailService;
    @Autowired
    private IUserService userService;
    @Autowired
    private ReportService reportService;

    @Parameter(names = {"-view"}, arity = 1, description = "all - витрати та прибутки, incomes - доходи, spends - витрати, приклад команди report -view all -period today", required = true)
    private String view;

    @Parameter(names = "-period", description = "параметри: today | month | year | 03.12.2015 | 03.12.2015-04.12.2015"
            , required = true)
    private String date;

    public ReportCommand() {
    }

    public void execute(String receiver) {
        try {

            if (!(view.toLowerCase().startsWith("all") || view.toLowerCase().startsWith("incomes") || view.toLowerCase().startsWith("spends"))) {

                throw new IllegalArgumentException("Wrong view parameter, should all,incomes or spends");
            }

            UserBean user = userService.findUserByLogin(receiver);
            whatView();

            ReportFilter filter = fillFilter(user.getId());
            ReportBean report = reportService.getReport(filter);

            MessageBuilder text = new ReportEmailTableBean(report).getTable();
            MessageBuilder subject = getSubjectTitle();
            EmailMessage email = new EmailMessage(subject, text, receiver);
            sendMailService.sendReportingMail(email);

        } catch (IllegalArgumentException ex) {
            throw new IllegalArgumentException(ex);
        }
    }

    private MessageBuilder getSubjectTitle() {
        switch (date.toLowerCase()) {
            case "today":
                return () -> "Звітність за сьогоднішній день!";
            case "month":
                return () -> "Звітність за поточний місяць!";
            case "year":
                return () -> "Звітність за поточний рік!";
            default:
                return () -> "Звітність за " + date;

        }
    }

    private ReportFilter fillFilter(int user) {
        String type = view;

        String from = DateUtil.getFormattedDate(dateParser.parse(date).getFrom(), DateUtil.DF_POINT);
        String to = DateUtil.getFormattedDate(dateParser.parse(date).getTo(), DateUtil.DF_POINT);

        String period = from + " - " + to;
        int category = -1; //all categories @see ReportService
        ReportFilter filter = new ReportFilter();
        filter.setType(type);
        filter.setCategory(category);
        filter.setUser(user);
        filter.setPeriod(period);
        return filter;
    }

    private void whatView() {
        if (view.toLowerCase().equals("all")) {
            view = "1";
        } else if (view.toLowerCase().equals("incomes")) {
            view = "plus";
        } else {
            view = "minus";
        }
    }
}