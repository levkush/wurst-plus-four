package dev.levkush.wurstplusfour.command.commands;

import com.mojang.realmsclient.gui.ChatFormatting;
import dev.levkush.wurstplusfour.WurstplusFour;
import dev.levkush.wurstplusfour.command.Command;
import dev.levkush.wurstplusfour.hack.Hack;
import dev.levkush.wurstplusfour.util.ClientMessage;

public class DrawnCommand extends Command {

    public DrawnCommand() {
        super("Drawn");
    }

    @Override
    public void execute(String[] message) {
        Hack hack = WurstplusFour.HACKS.getHackByName(message[0].replaceAll("_", " "));
        if (hack == null) {
            ClientMessage.sendErrorMessage("Cannot find hack by name " + ChatFormatting.BOLD + message[0]);
            return;
        }
        if (WurstplusFour.HACKS.isDrawHack(hack)) {
            WurstplusFour.HACKS.removeDrawnHack(hack);
            ClientMessage.sendMessage("Removed " + ChatFormatting.BOLD + hack.getName() + ChatFormatting.RESET + " from drawn list");
        } else {
            WurstplusFour.HACKS.addDrawHack(hack);
            ClientMessage.sendMessage("Added " + ChatFormatting.BOLD + hack.getName() + ChatFormatting.RESET + " to drawn list");
        }
    }
}
