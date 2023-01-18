package dev.levkush.wurstplusfour.util;

import dev.levkush.wurstplusfour.WurstplusFour;
import dev.levkush.wurstplusfour.hack.Hack;

public class HackUtil implements Globals {

    public static boolean shouldPause(Hack hack) {
        if (WurstplusFour.HACKS.ishackEnabled("Surround") && !hack.getName().equalsIgnoreCase("Surround")) {
            return true;
        }
        return false;
    }

}
