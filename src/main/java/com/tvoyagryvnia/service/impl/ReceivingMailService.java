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

import javax.mail.MessagingException;
import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
@PropertySource({"classpath:email.properties"})
public class ReceivingMailService {

    @Autowired private IUserService userService;
    @Autowired private ISendMailService sendMailService;
    @Qualifier(value = "help") @Autowired private Command help;
    @Qualifier(value = "report") @Autowired private Command report;
    @Autowired private Environment env;

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
            jc.parse(args);
            String name = jc.getParsedCommand();
            commands.get(name).execute(email.getFrom());
        } catch (ParameterException | IllegalArgumentException ex) {
            //if wrong parameters, send message with help
            StringBuilder sb = new StringBuilder();
            for (String cmd : commands.keySet()){
                jc.usage(cmd, sb,"<br>");
            }
            sendMailService.sendHelpCommandMessage(sb.toString(), email.getFrom());
        }
    }

    private void fillCommander(JCommander js) {
        commands.put("help", help);
        commands.put("report", report);
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


}
