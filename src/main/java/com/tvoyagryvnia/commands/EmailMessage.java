package com.tvoyagryvnia.commands;


import com.tvoyagryvnia.service.MessageBuilder;

public class EmailMessage {
    private MessageBuilder subject;
    private MessageBuilder text;
    private String to;

    public EmailMessage(MessageBuilder subject, MessageBuilder text, String to) {
        this.subject = subject;
        this.text = text;
        this.to = to;
    }

    public MessageBuilder getSubject() {
        return subject;
    }

    public void setSubject(MessageBuilder subject) {
        this.subject = subject;
    }

    public MessageBuilder getText() {
        return text;
    }

    public void setText(MessageBuilder text) {
        this.text = text;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }
}
