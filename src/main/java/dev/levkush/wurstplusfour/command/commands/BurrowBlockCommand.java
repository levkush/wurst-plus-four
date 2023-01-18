package dev.levkush.wurstplusfour.command.commands;

import dev.levkush.wurstplusfour.WurstplusFour;
import dev.levkush.wurstplusfour.command.Command;
import dev.levkush.wurstplusfour.hack.hacks.combat.Burrow;
import dev.levkush.wurstplusfour.util.ClientMessage;
import dev.levkush.wurstplusfour.util.WhitelistUtil;
import net.minecraft.block.Block;

import java.io.IOException;

/**
 * @author Madmegsox1
 * @since 08/05/2021
 */

public class BurrowBlockCommand extends Command {
    public BurrowBlockCommand(){
        super("BurrowBlock", "bb");
    }
    String bBlock = "";

    @Override
    public void execute(String[] message) {
        Burrow bClass = (Burrow) WurstplusFour.HACKS.getHackByName("Burrow");
        Block b = WhitelistUtil.findBlock(message[0]);

        if(b.equals(null)){
            ClientMessage.sendMessage("Cannot set Block to " + message[0]);
            return;
        }
        bClass.setBlock(b);
        ClientMessage.sendMessage("Set Block to " + message[0]);
        bBlock = message[0];
        try {
            WurstplusFour.CONFIG_MANAGER.saveBurrowBlock();
        }catch (IOException e){
        }
    }

    public String getBBlock(){
        return bBlock;
    }

    public void setBBlock(String b){
        bBlock = b;
    }
}
