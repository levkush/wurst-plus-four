package dev.levkush.wurstplusfour.plugin.api;

import dev.levkush.wurstplusfour.WurstplusFour;

/**
 * @author Madmegsox1
 * @since 21/06/2021
 */

public class PluginLogger {
    public static void print(String msg){
        WurstplusFour.LOGGER.info("Plugin -> " + msg);
    }
}
