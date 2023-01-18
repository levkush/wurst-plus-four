package dev.levkush.wurstplusfour.command.commands;

import com.mojang.realmsclient.gui.ChatFormatting;
import dev.levkush.wurstplusfour.WurstplusFour;
import dev.levkush.wurstplusfour.command.Command;
import dev.levkush.wurstplusfour.hack.Hack;
import dev.levkush.wurstplusfour.util.ClientMessage;

public class ToggleCommand extends Command {

    public ToggleCommand() {
        super("Toggle", "T");
    }

    @Override
    public void execute(String[] message) {
        String name = message[0].replaceAll("_", " ");
        Hack hack = WurstplusFour.HACKS.getHackByName(name);
        if (hack != null) {
            hack.toggle();
        } else {
            ClientMessage.sendErrorMessage("Cannot find hack by name " + ChatFormatting.BOLD + name);
        }
    }
}
