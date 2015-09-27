package com.tvoyagryvnia.util.email;

import com.tvoyagryvnia.service.MessageBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class SmtpEmailSenderImpl implements IEmailSender {

    //mail session
    @Autowired
    private Session mailSession;

    private Logger log = LoggerFactory.getLogger(this.getClass());


    /*for support old methods*/
    @Override
    public void sendMessage(String recipient, String subject, String content) throws MessagingException {
        MimeMessage mimeMessage = new MimeMessage(mailSession);
        InternetAddress targetAddress = new InternetAddress(recipient);

        mimeMessage.setRecipient(MimeMessage.RecipientType.TO, targetAddress);
        mimeMessage.setSender(targetAddress);
        mimeMessage.setSubject(subject);
        mimeMessage.setContent(content, "text/html; charset=UTF-8");

        Transport.send(mimeMessage);
    }

    @Override
    public void sendMessage(String recipient, MessageBuilder subject, MessageBuilder content) {
        send(recipient, subject, content);
    }


    private void send(String recipient, MessageBuilder subject, MessageBuilder content) {
        try {
            MimeMessage mimeMessage = new MimeMessage(mailSession);
            InternetAddress targetAddress = new InternetAddress(recipient);

            mimeMessage.setRecipient(MimeMessage.RecipientType.TO, targetAddress);
            mimeMessage.setSender(targetAddress);
            mimeMessage.setSubject(subject.create());
            mimeMessage.setContent(content.create(), "text/html; charset=UTF-8");

            synchronized (this) {
                Transport.send(mimeMessage);
            }
        } catch (MessagingException e) {
            log.error(e.getLocalizedMessage(), e);
        }
    }

    @Override
    public String getDomainName() {
        return mailSession.getProperty("domain");
    }

}
