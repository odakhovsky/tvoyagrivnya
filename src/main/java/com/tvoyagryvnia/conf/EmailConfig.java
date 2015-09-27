package com.tvoyagryvnia.conf;

import com.tvoyagryvnia.util.email.IEmailSender;
import com.tvoyagryvnia.util.email.SmtpEmailSenderImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

import javax.mail.Authenticator;
import javax.mail.Session;
import java.util.Properties;

@Configuration
@PropertySource({"classpath:email.properties"})
public class EmailConfig {

    @Autowired
    private Environment env;

    @Bean
    public Session getSession() {
        return Session.getInstance(sendEmailProperties(), authenticate(
                env.getProperty("email.account"), env.getProperty("email.password")));
    }

    @Bean
    public IEmailSender prodEmailSender() {
        return new SmtpEmailSenderImpl();
    }


    final Properties sendEmailProperties() {
        return new Properties() {
            {

                setProperty("mail.smtp.starttls.enable", env.getProperty("enableTLS"));
                setProperty("mail.smtp.host", env.getProperty("host"));
                setProperty("mail.smtp.user", env.getProperty("email.account"));
                setProperty("mail.smtp.password", env.getProperty("email.password"));
                setProperty("mail.smtp.port", "587");
                setProperty("mail.smtp.auth", "true");
                setProperty("mail.debug", "true");
            }
        };
    }

    private Authenticator authenticate(final String userName, final String userPassword) {

        return new GMailAuthenticator(userName,userPassword);
    }
}
