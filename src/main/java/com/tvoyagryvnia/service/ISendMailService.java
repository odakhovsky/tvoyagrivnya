package com.tvoyagryvnia.service;


import com.tvoyagryvnia.commands.EmailMessage;

public interface ISendMailService {

    public void sendRegistrationInformation(String name, String email, String password);
    public void sendInviteInformation(String inviterName,String name, String email, String password);
    public void sendPasswordUpdateNotification(String email, String password);
    public void sendReportingMail(EmailMessage email);
    public void sendHelpCommandMessage(String email);
    public void sendBalanceReportMessage(String email, String body);
    public void sendWrongIdOrPammetersOfBalance(String email);
}
