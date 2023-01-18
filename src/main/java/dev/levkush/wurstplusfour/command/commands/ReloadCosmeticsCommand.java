package dev.levkush.wurstplusfour.command.commands;

import dev.levkush.wurstplusfour.WurstplusFour;
import dev.levkush.wurstplusfour.command.Command;
import dev.levkush.wurstplusfour.util.ClientMessage;

/**
 * @author Madmegsox1
 * @since 01/05/2021
 */

public class ReloadCosmeticsCommand extends Command {
    public ReloadCosmeticsCommand(){
        super("ReloadCosmetics", "ReloadCosmetic");
    }

    @Override
    public void execute(String[] message) {
        WurstplusFour.COSMETIC_MANAGER.reload();
        ClientMessage.sendMessage("Reloaded Cosmetics!");
    }
}
