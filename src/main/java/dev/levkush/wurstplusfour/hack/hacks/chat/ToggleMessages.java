package dev.levkush.wurstplusfour.hack.hacks.chat;

import dev.levkush.wurstplusfour.setting.type.BooleanSetting;
import dev.levkush.wurstplusfour.hack.Hack;
import dev.levkush.wurstplusfour.hack.HackPriority;

@Hack.Registration(name = "Toggle Msgs", description = "Says in chat when you toggle something", category = Hack.Category.CHAT, priority = HackPriority.Lowest)
public class ToggleMessages extends Hack {

    public static ToggleMessages INSTANCE;

    public ToggleMessages() {
        INSTANCE = this;
    }

    public BooleanSetting compact = new BooleanSetting("Compact", true, this);
}
