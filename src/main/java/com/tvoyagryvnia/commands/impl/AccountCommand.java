package com.tvoyagryvnia.commands.impl;


import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;
import com.beust.jcommander.converters.BooleanConverter;
import com.tvoyagryvnia.bean.account.AccountEmailBean;
import com.tvoyagryvnia.commands.Command;
import com.tvoyagryvnia.dao.IAccountDao;
import com.tvoyagryvnia.dao.IUserDao;
import com.tvoyagryvnia.model.AccountEntity;
import com.tvoyagryvnia.model.UserEntity;
import com.tvoyagryvnia.service.ISendMailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Component(value = "account")
@Parameters(commandNames = {"account"}, commandDescription = "інформація по рахункам")
public class AccountCommand implements Command {

    @Autowired
    private IAccountDao accountDao;
    @Autowired
    private IUserDao userDao;

    @Autowired
    private ISendMailService sendMailService;

    @Parameter(names = "-view", required = true)
    private String show;

    @Override
    public void execute(String receiver) {
        switch (show.toLowerCase()) {
            case "all":
                try {
                    UserEntity user = userDao.findUserByEmail(receiver);
                    if (null != user) {
                        List<AccountEntity> accs = accountDao.getAllOfUserActive(user.getId(), true);
                        String emailBody = AccountEmailBean.buildTable(accs);
                        sendMailService.sendBalanceReportMessage(receiver, emailBody);
                    } else {
                        throw new Exception("User not found");
                    }
                } catch (Exception ex) {
                    throw new IllegalArgumentException("User not found exception");
                }
                break;
            default:
                try {
                    Integer id = Integer.valueOf(show);
                    AccountEntity acc = accountDao.getById(id);
                    if (acc.getOwner().getEmail().equals(receiver)) {
                        String emailBody = AccountEmailBean.buildTable(Collections.singletonList(acc));
                        sendMailService.sendBalanceReportMessage(receiver, emailBody);
                    } else {
                        throw new IllegalArgumentException("Wrong account id");
                    }

                } catch (Exception ex) {
                    sendMailService.sendWrongIdOrPammetersOfBalance(receiver);
                }
                break;
        }

    }
}
