package com.tvoyagryvnia.util.email;



import com.tvoyagryvnia.service.MessageBuilder;

import javax.mail.MessagingException;

public interface IEmailSender {

    public void sendMessage(String recipient, String subject, String content) throws MessagingException;
    public void sendMessage(String recipient, MessageBuilder subject, MessageBuilder content);
    public String getDomainName();

}
