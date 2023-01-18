package dev.levkush.wurstplusfour.gui.hud.element.elements;

import dev.levkush.wurstplusfour.event.events.Render2DEvent;
import dev.levkush.wurstplusfour.gui.hud.element.HudElement;
import dev.levkush.wurstplusfour.hack.hacks.client.HudEditor;
import dev.levkush.wurstplusfour.util.HudUtil;

/**
 * @author Madmegsox1
 * @since 22/06/2021
 */

@HudElement.Element(name = "Welcomer", posX = 100, posY = 100)
public class HudWelcomer extends HudElement {

    String text = "";

    @Override
    public int getHeight() {
        return HudUtil.getHudStringHeight();
    }

    @Override
    public int getWidth(){
        return HudUtil.getHudStringWidth(text);
    }

    @Override
    public void onRender2D(Render2DEvent event){
        text = HudUtil.getWelcomerLine();
        HudUtil.drawHudString(text, getX(), getY(), HudEditor.INSTANCE.fontColor.getValue().hashCode());
    }

}
