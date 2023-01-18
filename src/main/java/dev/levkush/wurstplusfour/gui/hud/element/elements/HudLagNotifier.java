package dev.levkush.wurstplusfour.gui.hud.element.elements;

import com.mojang.realmsclient.gui.ChatFormatting;
import dev.levkush.wurstplusfour.WurstplusFour;
import dev.levkush.wurstplusfour.event.events.Render2DEvent;
import dev.levkush.wurstplusfour.gui.hud.element.HudElement;
import dev.levkush.wurstplusfour.hack.hacks.client.HudEditor;
import dev.levkush.wurstplusfour.util.HudUtil;

@HudElement.Element(name = "Lag Notifier", posY = 100, posX = 100)
public class HudLagNotifier extends HudElement {
    String length;

    @Override
    public int getWidth(){
        return HudUtil.getHudStringWidth(length);
    }

    @Override
    public void onRender2D(Render2DEvent event){
        length = ChatFormatting.RED + "Server is not responding " + Math.round(WurstplusFour.SERVER_MANAGER.serverRespondingTime() / 1000.0f);
        if(WurstplusFour.SERVER_MANAGER.isServerNotResponding()){
            HudUtil.drawHudString(length, getX(), getY(), HudEditor.INSTANCE.fontColor.getValue().hashCode());
        }
    }
}
