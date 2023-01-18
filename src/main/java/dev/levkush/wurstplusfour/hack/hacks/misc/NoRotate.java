package dev.levkush.wurstplusfour.hack.hacks.misc;

import dev.levkush.wurstplusfour.hack.Hack;
import dev.levkush.wurstplusfour.hack.HackPriority;

@Hack.Registration(name = "NoRotate", description = "Could cause desync", category = Hack.Category.MISC, priority = HackPriority.Lowest)
public class NoRotate extends Hack{
    public static NoRotate INSTANCE;

    public NoRotate(){
        INSTANCE = this;
    }
}
