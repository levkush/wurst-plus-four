package dev.levkush.wurstplusfour.command.commands;

import dev.levkush.wurstplusfour.command.Command;
import dev.levkush.wurstplusfour.util.ClientMessage;

public class TestCommand extends Command {

    public TestCommand() {
        super("Test");
    }

    @Override
    public void execute(String[] message) {
        if (message.length == 0) {
            ClientMessage.sendErrorMessage("empty message");
            return;
        }
        for (String s : message) {
            ClientMessage.sendMessage(s);
        }
    }

}
