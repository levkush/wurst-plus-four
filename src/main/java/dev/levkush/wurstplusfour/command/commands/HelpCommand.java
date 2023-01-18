package dev.levkush.wurstplusfour.command.commands;

import com.mojang.realmsclient.gui.ChatFormatting;
import dev.levkush.wurstplusfour.WurstplusFour;
import dev.levkush.wurstplusfour.command.Command;
import dev.levkush.wurstplusfour.hack.hacks.client.Gui;
import dev.levkush.wurstplusfour.util.ClientMessage;

public class HelpCommand extends Command {

    public HelpCommand() {
        super("Help", "H");
    }

    @Override
    public void execute(String[] message) {
        ClientMessage.sendMessage(ChatFormatting.BOLD + "Command list");
        ClientMessage.sendMessage("Your gui is currently bound to " + ChatFormatting.BOLD + Gui.INSTANCE.getBindName());
        for (Command command : WurstplusFour.COMMANDS.getCommands()) {
            ClientMessage.sendMessage(command.getNames().get(0));
        }
    }

}
