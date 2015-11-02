package com.tvoyagryvnia.bean;


public class FeedbackFilter {

    private String email;
    private String date;

    public FeedbackFilter(String em, String date) {
        email = em;
        this.date = date;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
