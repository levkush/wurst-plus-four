package dev.levkush.wurstplusfour.command.commands;

import com.mojang.realmsclient.gui.ChatFormatting;
import dev.levkush.wurstplusfour.WurstplusFour;
import dev.levkush.wurstplusfour.command.Command;
import dev.levkush.wurstplusfour.util.ClientMessage;

public class FontCommand extends Command {

    public FontCommand() {
        super("Font");
    }

    @Override
    public void execute(String[] message) {
        if (message[0].equalsIgnoreCase("reset")) {
            WurstplusFour.GUI_FONT_MANAGER.reset();
            ClientMessage.sendMessage("Reset font");
            return;
        }
        if (message.length < 2) {
            ClientMessage.sendErrorMessage(ChatFormatting.BOLD + "Set" + ChatFormatting.RESET + ChatFormatting.RED + " to set font or" +
                    ChatFormatting.BOLD + " Size" + ChatFormatting.RESET + ChatFormatting.RED + " to set font size");
            return;
        }
        String mode = message[0];
        String name = message[1].replaceAll("_", " ");
        if (mode.equalsIgnoreCase("set")) {
            if (name.equalsIgnoreCase("random")) {
                ClientMessage.sendMessage("Set font to " + WurstplusFour.GUI_FONT_MANAGER.setRandomFont());
                return;
            }
            if (WurstplusFour.GUI_FONT_MANAGER.setFont(name)) {
                ClientMessage.sendMessage("Set font to " + ChatFormatting.BOLD + name);
            } else {
                ClientMessage.sendErrorMessage("Cannot find font " + ChatFormatting.BOLD + name);
            }
            return;
        }
        if (mode.equalsIgnoreCase("size")) {
            int size;
            try {
                size = Integer.parseInt(name);
            } catch (Exception ignored) {
                ClientMessage.sendErrorMessage("Size not valid");
                return;
            }
            WurstplusFour.GUI_FONT_MANAGER.setFontSize(size);
            ClientMessage.sendMessage("Font size set to " + size);
            return;
        }
        ClientMessage.sendErrorMessage(ChatFormatting.BOLD + "Set" + ChatFormatting.RESET + " to set font or" +
                ChatFormatting.BOLD + " Size" + ChatFormatting.RESET + " to set font size");
    }
}
