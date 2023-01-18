package dev.levkush.wurstplusfour.command.commands;

import com.mojang.realmsclient.gui.ChatFormatting;
import dev.levkush.wurstplusfour.WurstplusFour;
import dev.levkush.wurstplusfour.command.Command;
import dev.levkush.wurstplusfour.util.ClientMessage;

public class ConfigCommand extends Command {

    public ConfigCommand() {
        super("Config", "C");
    }

    @Override
    public void execute(String[] message) {
        String folder = message[0].replaceAll("_", " ") + "/";
        if (folder.equalsIgnoreCase("/")) {
            ClientMessage.sendMessage("Current config is " + WurstplusFour.CONFIG_MANAGER.configName);
            return;
        }
        WurstplusFour.CONFIG_MANAGER.setActiveConfigFolder(folder);
        ClientMessage.sendMessage("Set config folder to " + ChatFormatting.BOLD + folder.substring(0, folder.length() - 1));
    }

}
