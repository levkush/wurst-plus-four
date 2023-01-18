package dev.levkush.wurstplusfour.hack.hacks.player;

import dev.levkush.wurstplusfour.hack.Hack;
@Hack.Registration(name = "Multi Task", description = "eat n shit", category = Hack.Category.PLAYER, isListening = false)
public class MultiTask extends Hack {

    public static MultiTask INSTANCE;

    public MultiTask() {
        INSTANCE = this;
    }

}
