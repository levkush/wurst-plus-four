package dev.levkush.wurstplusfour.hack.hacks.render;

import dev.levkush.wurstplusfour.hack.Hack;

@Hack.Registration(name = "Small Shield", description = "trvsf moment", category = Hack.Category.RENDER, isListening = false)
public class SmallShield extends Hack {

    @Override
    public void onUpdate() {
        mc.entityRenderer.itemRenderer.equippedProgressOffHand = -1;
    }

}
