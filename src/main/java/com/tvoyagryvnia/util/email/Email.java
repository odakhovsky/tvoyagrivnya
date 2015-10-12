package com.tvoyagryvnia.util.email;


public class Email {

    private String from;
    private String subject;
    private String body;

    public Email(String from, String subject, String body) {
        this.from = from;
        this.subject = subject;
        this.body = body.trim();
    }

    public String getFrom() {
        return from;
    }

    public String getSubject() {
        return subject;
    }

    public String getText() {
        return body;
    }
}
