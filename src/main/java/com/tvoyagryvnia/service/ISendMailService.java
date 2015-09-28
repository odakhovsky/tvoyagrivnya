package com.tvoyagryvnia.service;


public interface ISendMailService {

    public void sendRegistrationInformation(String name, String email, String password);
    public void sendInviteInformation(String inviterName,String name, String email, String password);
    public void sendPasswordUpdateNotification(String email, String password);

}
