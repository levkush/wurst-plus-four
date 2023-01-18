package dev.levkush.wurstplusfour.hack.hacks.render;

import dev.levkush.wurstplusfour.setting.type.ColourSetting;
import dev.levkush.wurstplusfour.util.elements.Colour;
import dev.levkush.wurstplusfour.hack.Hack;

@Hack.Registration(name = "Hand Colour", description = "colours hands (only 9 months late jumpy)", category = Hack.Category.RENDER, isListening = false)
public class HandColour extends Hack {

    public static HandColour INSTANCE;

    public HandColour() {
        INSTANCE = this;
    }

    public ColourSetting colour = new ColourSetting("Colour", new Colour(255, 255, 255, 150), this);

}
