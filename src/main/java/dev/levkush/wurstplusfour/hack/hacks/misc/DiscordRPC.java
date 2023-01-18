package dev.levkush.wurstplusfour.hack.hacks.misc;

import dev.levkush.wurstplusfour.RPC;
import dev.levkush.wurstplusfour.hack.Hack;
import dev.levkush.wurstplusfour.hack.HackPriority;


@Hack.Registration(name = "Discord RPC", description = "It is discordrpc !", category = Hack.Category.MISC, priority = HackPriority.Lowest)
public class DiscordRPC extends Hack {


    public void onEnable() {
        RPC.startRPC();
    }

    public void onDisable() {
        RPC.stopRPC();
    }
}
