package dev.levkush.wurstplusfour.hack.hacks.render;

import dev.levkush.wurstplusfour.setting.type.DoubleSetting;
import dev.levkush.wurstplusfour.hack.Hack;

/**
 * @author Madmegsox1
 * @since 03/05/2021
 */
@Hack.Registration(name = "Item Physics", description = "Apply physics to items", category = Hack.Category.RENDER, isListening = false)
public class ItemPhysics extends Hack{
    public static ItemPhysics INSTANCE;

    public ItemPhysics(){
        INSTANCE = this;
    }

    public DoubleSetting Scaling = new DoubleSetting("Scaling", 0.5, 0.0, 10.0, this);
}
