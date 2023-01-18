package dev.levkush.wurstplusfour.command.commands;

import dev.levkush.wurstplusfour.WurstplusFour;
import dev.levkush.wurstplusfour.command.Command;
import dev.levkush.wurstplusfour.util.ClientMessage;

/**
 * @author Madmegsox1
 * @since 01/05/2021
 */

public class ReloadCapesCommand extends Command {

    public ReloadCapesCommand(){
        super("ReloadCapes", "ReloadCape");
    }

    @Override
    public void execute(String[] message) {
        WurstplusFour.CAPE_MANAGER.reload();
        ClientMessage.sendMessage("Reloaded Capes!");
    }
}
