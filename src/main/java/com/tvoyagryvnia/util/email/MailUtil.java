package com.tvoyagryvnia.util.email;

import com.sun.mail.imap.protocol.FLAGS;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMultipart;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;


public class MailUtil {

    public List<Email> receiveAndDeleteEmail(String host,
                                             String storeType,
                                             String user,
                                             String password) {
        List<Email> emails = new ArrayList<>();

        try {
            //1) get the session object
            Properties properties = new Properties();
            properties.put("mail.store.protocol", storeType);
            properties.put("mail.pop3s.host", host);
            properties.put("mail.pop3s.port", "995");
            properties.put("mail.pop3.starttls.enable", "true");
            Session emailSession = Session.getDefaultInstance(properties);

            //2) create the POP3 store object and connect with the pop server
            Store store = emailSession.getStore("pop3s");
            store.connect(host, user, password);

            //3) create the folder object and open it
            Folder emailFolder = store.getFolder("INBOX");
            emailFolder.open(Folder.READ_WRITE);

            //4) retrieve the messages from the folder in an array and print it
            Message[] messages = emailFolder.getMessages();
            for (Message message : messages) {
                Object content = message.getContent();
                String singleSender = ((InternetAddress) message.getFrom()[0]).getAddress();
                String parsedContent = parseContent(content);
                String trimmedContent = trimContent(parsedContent);

                Email email = new Email(
                        singleSender,
                        message.getSubject(),
                        trimmedContent
                );

                emails.add(email);
                message.setFlag(FLAGS.Flag.DELETED, true);
            }

            //5) close the store and folder objects
            emailFolder.close(true);
            store.close();

        } catch (MessagingException | IOException e) {
            return new ArrayList<>();
        }

        return emails;
    }

    public String trimContent(String content) {
        String result = content.trim();
        result = result.replaceAll("[ ]+", " ");
        result = result.replaceAll("[\n]+", "\n");
        return result;
    }

    private String parseContent(Object content) throws MessagingException, IOException {
        if (content instanceof String) {
            return (String) content;
        }
        if (content instanceof MimeMultipart) {
            String result = "";
            for (int i = 0; i < ((MimeMultipart) content).getCount(); i++) {
                result += parseContent(((MimeMultipart) content).getBodyPart(i));
            }
            return result;
        }
        if (content instanceof Part) {
            Part part = (Part) content;
            if (part.isMimeType("text/plain")) {
                return parseContent(part.getContent());
            }
        }
        return "";
    }
}
