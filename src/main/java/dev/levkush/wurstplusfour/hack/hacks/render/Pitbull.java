package dev.levkush.wurstplusfour.hack.hacks.render;

import dev.levkush.wurstplusfour.setting.type.ColourSetting;
import dev.levkush.wurstplusfour.util.elements.Colour;
import dev.levkush.wurstplusfour.hack.Hack;

/**
 * @author Madmegsox1
 * @since 28/04/2021
 */

@Hack.Registration(name = "Pitbull Esp", description = "makes everyones skin pitbull", category = Hack.Category.RENDER, isListening = false)
public class Pitbull extends Hack {

    public static Pitbull INSTANCE;

    public Pitbull(){
        INSTANCE = this;
    }

    public ColourSetting texture = new ColourSetting("Texture",new Colour(255,255,255, 255), this);

}
