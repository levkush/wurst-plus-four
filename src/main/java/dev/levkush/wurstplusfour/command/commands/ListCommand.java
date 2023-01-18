package dev.levkush.wurstplusfour.command.commands;

import com.mojang.realmsclient.gui.ChatFormatting;
import dev.levkush.wurstplusfour.WurstplusFour;
import dev.levkush.wurstplusfour.command.Command;
import dev.levkush.wurstplusfour.hack.Hack;
import dev.levkush.wurstplusfour.util.ClientMessage;

public class ListCommand extends Command {

    public ListCommand() {
        super("List", "L");
    }

    @Override
    public void execute(String[] message) {
        ClientMessage.sendMessage(ChatFormatting.BOLD + "Hack list");
        String cat = "";
        if (message.length >= 1) {
            cat = message[0];
        }
        for (Hack.Category category : Hack.Category.values()) {
            if (!cat.equalsIgnoreCase("") && !cat.equalsIgnoreCase(category.getName())) continue;
            ClientMessage.sendMessage(ChatFormatting.BOLD + category.getName());
            for (Hack hack : WurstplusFour.HACKS.getHacksByCategory(category)) {
                ClientMessage.sendMessage(hack.getName() + " : " + hack.getDescription());
            }
        }
    }
}
