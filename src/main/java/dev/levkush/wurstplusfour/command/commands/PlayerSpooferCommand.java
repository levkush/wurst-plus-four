package dev.levkush.wurstplusfour.command.commands;

import com.mojang.realmsclient.gui.ChatFormatting;
import dev.levkush.wurstplusfour.command.Command;
import dev.levkush.wurstplusfour.hack.hacks.player.PlayerSpoofer;
import dev.levkush.wurstplusfour.util.ClientMessage;

/**
 * @author Madmegsox1
 * @since 28/04/2021
 * FIXED UR SHIT ¬ travis
 */

public class PlayerSpooferCommand extends Command {

    public PlayerSpooferCommand(){
        super("PlayerSpoof", "PlayerSpoofer", "PS");
    }

    @Override
    public void execute(String[] message) {
        if (message.length == 0){
            ClientMessage.sendErrorMessage("Enter a name dumbass!");
            return;
        }
        if (message.length == 1){
            if (message[0].isEmpty()){
                ClientMessage.sendErrorMessage("Enter a name dumbass!");
                return;
            }
            String name = message[0];
            PlayerSpoofer.INSTANCE.name = name;
            // reset skin
            PlayerSpoofer.INSTANCE.disable();
            PlayerSpoofer.INSTANCE.enable();
            // goods
            ClientMessage.sendMessage("Set skin to " + ChatFormatting.BOLD + name);
        }
    }
}
