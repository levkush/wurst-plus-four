package dev.levkush.wurstplusfour.hack.hacks.render;

import dev.levkush.wurstplusfour.setting.type.IntSetting;
import dev.levkush.wurstplusfour.hack.Hack;

@Hack.Registration(name = "Fov", description = "tabbott mode", category = Hack.Category.RENDER, isListening = false)
public class Fov extends Hack {

    IntSetting fov = new IntSetting("Fov", 130, 90, 179, this);

    float fovOld;

    @Override
    public void onEnable(){
        fovOld = mc.gameSettings.fovSetting;
    }

    @Override
    public void onUpdate() {
        mc.gameSettings.fovSetting = (float) fov.getValue();
    }

    @Override
    public void onDisable(){
        mc.gameSettings.fovSetting = fovOld;
    }
}
