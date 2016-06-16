package com.tvoyagryvnia.service.impl;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.ParameterException;
import com.tvoyagryvnia.bean.user.UserBean;
import com.tvoyagryvnia.commands.Command;
import com.tvoyagryvnia.service.ISendMailService;
import com.tvoyagryvnia.service.IUserService;
import com.tvoyagryvnia.util.email.Email;
import com.tvoyagryvnia.util.email.MailUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
@PropertySource({"classpath:email.properties"})
public class ReceivingMailService {

    @Autowired
    private IUserService userService;
    @Autowired
    private ISendMailService sendMailService;
    @Qualifier(value = "help")
    @Autowired
    private Command help;
    @Qualifier(value = "report")
    @Autowired
    private Command report;
    @Qualifier(value = "account")
    @Autowired
    private Command account;
    @Autowired
    private Environment env;

    private Map<String, Command> commands;

    @Scheduled(fixedRate = 10000)
    public void checkNewMail() {
        List<Email> emails = getAllNewMail();
        System.out.println("Count of incoming new messages " + emails.size());
        for (Email email : emails) {
            UserBean user = userService.getUserByEmail(email.getFrom());
            if (null != user) {
                executeCommand(email);
            }
        }
    }

    private void executeCommand(Email email) {
        JCommander jc = new JCommander();
        try {
            fillCommander(jc);
            String[] args = email.getText().split(" ");
            String[] commandList = new String[args.length];
            String text;
            for (int i = 0; i < args.length; i++) {
                if (!args[i].contains("-")) {
                    text = args[i].toLowerCase();
                    String replace = args[i].replace(args[i], translate(text));
                    commandList[i] = replace;
                } else {
                    text = args[i].substring(1, args[i].length());
                    String replace  = args[i].replace(text, translate(text.toLowerCase()));
                    commandList[i] = replace;
                }
            }
            jc.parse(commandList);
            String name = jc.getParsedCommand();
            commands.get(name).execute(email.getFrom());
        } catch (ParameterException | IllegalArgumentException ex) {
            sendMailService.sendHelpCommandMessage(email.getFrom());
        }
    }

    private void fillCommander(JCommander js) {
        commands.put("help", help);
        commands.put("report", report);
        commands.put("account", account);
        for (Map.Entry<String, Command> entry : commands.entrySet()) {
            js.addCommand(entry.getKey(), entry.getValue());
        }
    }

    private List<Email> getAllNewMail() {
        MailUtil mailUtil = new MailUtil();
        return mailUtil.receiveAndDeleteEmail("smtp.gmail.com", "pop3s", env.getProperty("email.account"), env.getProperty("email.password"));
    }

    public ReceivingMailService() {
        commands = new HashMap<>();
    }


    private static Map<String, String> translateCommand;

    static {
        translateCommand = new HashMap<>();
        translateCommand.put("допомога", "help");
        translateCommand.put("звіт", "report");
        translateCommand.put("показати", "view");
        translateCommand.put("період", "period");
        translateCommand.put("все", "all");
        translateCommand.put("прибуток", "incomes");
        translateCommand.put("витрати", "spends");
        translateCommand.put("сьогодні", "today");
        translateCommand.put("місяць", "month");
        translateCommand.put("рік", "year");
        translateCommand.put("рахунок", "account");
    }

    private static String translate(String key) {
        return translateCommand.getOrDefault(key, "");
    }
}
