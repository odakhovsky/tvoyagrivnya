package com.tvoyagryvnia.service.impl;

import com.tvoyagryvnia.service.ISendMailService;
import com.tvoyagryvnia.service.IUserService;
import com.tvoyagryvnia.service.MessageBuilder;
import com.tvoyagryvnia.util.Messages;
import com.tvoyagryvnia.util.email.IEmailSender;
import com.tvoyagryvnia.commands.EmailMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class SendMailServiceImpl implements ISendMailService {
    @Autowired
    IEmailSender emailSender;

    @Autowired
    IUserService userService;

    @Override
    public void sendRegistrationInformation(String name, String email, String password) {
        MessageBuilder subject = () -> String.format(Messages.REGISTRATION_SUBJECT);
        MessageBuilder content = () -> String.format(Messages.REGISTRATION_BODY,
                name, password);
        emailSender.sendMessage(email, subject, content);
    }

    @Override
    public void sendInviteInformation(String inviterName, String name, String email, String password) {
        MessageBuilder subject = () -> String.format(Messages.INVITATION_BODY);
        MessageBuilder content = () -> String.format(Messages.INVITE_SUBJECT,
                inviterName, name, email, password);
        emailSender.sendMessage(email, subject, content);
    }

    @Override
    public void sendPasswordUpdateNotification(String email, String password) {
        MessageBuilder subject = () -> String.format(Messages.PASSWORD_CHANGE_SUBJECT);
        MessageBuilder content = () -> String.format(Messages.PASSWORD_CHANGE__BODY, password);
        emailSender.sendMessage(email, subject, content);
    }

    @Override
    public void sendReportingMail(EmailMessage email) {
        emailSender.sendMessage(email.getTo(), email.getSubject(), email.getText());
    }

    @Override
    public void sendHelpCommandMessage(String email) {
        MessageBuilder subject = () -> Messages.HELP_COMMANDS_SUBJECT;
        MessageBuilder text = () -> Messages.HELP_COMMANDS_BODY;
        emailSender.sendMessage(email, subject, text);
    }

    @Override
    public void sendBalanceReportMessage(String email, String body) {
        MessageBuilder subject = () -> "Інформація про рахунки";
        MessageBuilder text = () -> body;
        emailSender.sendMessage(email, subject, text);
    }

    @Override
    public void sendWrongIdOrPammetersOfBalance(String email) {
        MessageBuilder subject = () -> "Помилкове введення команди";
        MessageBuilder text = () -> "Будь ласка перевірте правильність введення команди!";
        emailSender.sendMessage(email, subject, text);
    }


}
