package com.tvoyagryvnia.commands.impl;

import com.beust.jcommander.Parameters;
import com.tvoyagryvnia.commands.Command;
import org.springframework.stereotype.Component;

@Component(value = "help")
@Parameters(commandNames = {"help"}, commandDescription = " відображає структуру команд")
public class HelpCommand implements Command {

    @Override
    public void execute(String receiver) {
        throw new IllegalArgumentException("Invoke help sending");
    }
}
