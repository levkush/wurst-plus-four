package dev.levkush.wurstplusfour.hack.hacks.player;

import dev.levkush.wurstplusfour.event.events.MoveEvent;
import dev.levkush.wurstplusfour.event.processor.CommitEvent;
import dev.levkush.wurstplusfour.setting.type.EnumSetting;
import dev.levkush.wurstplusfour.hack.Hack;

import java.util.Arrays;

@Hack.Registration(name = "Sprint", description = "sprints automatically", category = Hack.Category.PLAYER)
public class Sprint extends Hack {

    public EnumSetting mode = new EnumSetting("Mode", "Legit", Arrays.asList("legit", "Rage"), this);

    @CommitEvent
    public void onMove(MoveEvent event) {
        if (event.getStage() == 1 && this.mode.is("Rage") && (mc.player.movementInput.moveForward != 0f ||
                mc.player.moveStrafing != 0f)) {
            event.setCancelled(true);
        }
    }

    @Override
    public void onUpdate() {
        if(nullCheck())return;
        if (mode.is("Legit")) {
            if (mc.gameSettings.keyBindForward.isKeyDown()) {
                mc.player.setSprinting(true);
            }
        } else {
            mc.player.setSprinting(true);
        }
    }

    @Override
    public String getDisplayInfo() {
        return mode.getValue();
    }
}
