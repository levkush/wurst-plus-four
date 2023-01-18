package dev.levkush.wurstplusfour.setting.type;

import dev.levkush.wurstplusfour.hack.Hack;
import dev.levkush.wurstplusfour.setting.Setting;

public class ParentSetting extends Setting<Boolean> {

    public ParentSetting(String name, Hack parent) {
        super(name, false, parent);
    }

    public void toggle() {
        value = !value;
    }

    public boolean isShown(){
        return true;
    }

    public Boolean getValue() {
        return value;
    }

    @Override
    public String getType() {
        return "boolean";
    }
}
