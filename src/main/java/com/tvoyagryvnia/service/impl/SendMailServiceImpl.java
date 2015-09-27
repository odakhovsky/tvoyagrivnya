package com.tvoyagryvnia.service.impl;

import com.tvoyagryvnia.service.ISendMailService;
import com.tvoyagryvnia.service.IUserService;
import com.tvoyagryvnia.service.MessageBuilder;
import com.tvoyagryvnia.util.Messages;
import com.tvoyagryvnia.util.email.IEmailSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

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
}
