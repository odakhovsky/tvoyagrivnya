package com.tvoyagryvnia.commands.impl;

import com.beust.jcommander.Parameters;
import com.tvoyagryvnia.bean.user.UserBean;
import com.tvoyagryvnia.commands.Command;
import com.tvoyagryvnia.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component(value = "help")
@Parameters(commandNames = {"help"}, commandDescription = " відображає структуру команд")
public class HelpCommand implements Command {

    @Override
    public void execute(String receiver) {
        throw new IllegalArgumentException("Invoke help sending");
    }
}
