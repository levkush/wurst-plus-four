package dev.levkush.wurstplusfour.hack.hacks.chat;

import dev.levkush.wurstplusfour.WurstplusFour;
import dev.levkush.wurstplusfour.setting.type.BooleanSetting;
import dev.levkush.wurstplusfour.util.ClientMessage;
import dev.levkush.wurstplusfour.hack.Hack;

@Hack.Registration(name = "Totem Pop Counter", description = "counts totems that people have popped", category = Hack.Category.CHAT)
public class TotemPopCounter extends Hack {
    public static TotemPopCounter INSTANCE;
    public TotemPopCounter(){
        INSTANCE = this;
    }

    public BooleanSetting kdMessages = new BooleanSetting("KD Messages", true, this);

    @Override
    public void onUpdate() {
        if (nullCheck()) return;
        if (!WurstplusFour.POP_MANAGER.toAnnouce.isEmpty()) {
            try {
                for (String string : WurstplusFour.POP_MANAGER.toAnnouce) {
                    ClientMessage.sendOverwriteClientMessage(string);
                }
                WurstplusFour.POP_MANAGER.toAnnouce.clear();
            } catch (Exception e) {
                //empty catchblock goo brrrrrrrrr
            }
        }
    }
}
