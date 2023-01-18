package dev.levkush.wurstplusfour.gui.hud.element.elements;

import dev.levkush.wurstplusfour.event.events.Render2DEvent;
import dev.levkush.wurstplusfour.gui.hud.element.HudElement;
import dev.levkush.wurstplusfour.hack.hacks.client.HudEditor;
import dev.levkush.wurstplusfour.util.HudUtil;

@HudElement.Element(name = "TPS", posX = 10, posY = 50)
public class HudTps extends HudElement {

    public String text;

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
        text = "Tps " + HudUtil.getTpsLine();
        HudUtil.drawHudString(text, this.getX(), this.getY(), HudEditor.INSTANCE.fontColor.getValue().hashCode());
    }

}
