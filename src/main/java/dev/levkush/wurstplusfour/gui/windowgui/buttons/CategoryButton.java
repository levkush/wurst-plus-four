package dev.levkush.wurstplusfour.gui.windowgui.buttons;

import dev.levkush.wurstplusfour.hack.Hack;
import dev.levkush.wurstplusfour.util.Globals;
import dev.levkush.wurstplusfour.gui.component.Component;

import java.util.ArrayList;

/**
 * @author Madmegsox1
 * @since 06/07/2021
 */

public class CategoryButton implements Globals {
    public ArrayList<Component> components;
    public Hack.Category category;


    public CategoryButton(Hack.Category category){
        this.category = category;
        this.components = new ArrayList<>();
    }
}
