package dev.levkush.wurstplusfour.gui.hud.element.elements;

import com.mojang.realmsclient.gui.ChatFormatting;
import dev.levkush.wurstplusfour.WurstplusFour;
import dev.levkush.wurstplusfour.event.events.Render2DEvent;
import dev.levkush.wurstplusfour.gui.hud.element.HudElement;
import dev.levkush.wurstplusfour.hack.hacks.client.HudEditor;
import dev.levkush.wurstplusfour.util.HudUtil;
import dev.levkush.wurstplusfour.util.RenderUtil2D;
import dev.levkush.wurstplusfour.util.elements.Colour;
import dev.levkush.wurstplusfour.util.elements.Rainbow;

@HudElement.Element(name = "CSGO Watermark", posX = 100, posY = 100)
public class HudCsgoWatermark extends HudElement {
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
    public void onRender2D(Render2DEvent event) {
        int padding = 5;
        Colour fill = new Colour(77, 77, 77, 255);
        Colour outline = new Colour(127, 127, 127, 255);
        RenderUtil2D.drawBorderedRect(this.getX() - padding, this.getY() - padding,
                this.getX() + padding + this.getWidth(), this.getY() + padding + this.getHeight() - 1, 1, fill.hashCode(), outline.hashCode(), false);
        RenderUtil2D.drawHLineG(this.getX() - padding, this.getY() - padding,
                (this.getX() + padding + this.getWidth()) - (this.getX() - padding), Rainbow.getColour().hashCode(), Rainbow.getFurtherColour(HudEditor.INSTANCE.welcomerOffset.getValue()).hashCode());
        text = ChatFormatting.GOLD + "Wurst" + ChatFormatting.RESET + "plus";
        if (HudEditor.INSTANCE.welcomerName.getValue()) {
            text += " | " + mc.player.getName();
        }
        if (HudEditor.INSTANCE.welcomerFps.getValue()) {
            text += " |" + HudUtil.getFpsLine() + ChatFormatting.RESET;
        }
        if (HudEditor.INSTANCE.welcomerTps.getValue()) {
            text += " |" + HudUtil.getTpsLine() + ChatFormatting.RESET;
        }
        if (HudEditor.INSTANCE.welcomerPing.getValue()) {
            text += " |" + HudUtil.getPingLine() + ChatFormatting.RESET;
        }
        if (HudEditor.INSTANCE.welcomerTime.getValue()) {
            text += " | " + HudUtil.getAnaTimeLine() + ChatFormatting.RESET;
        }
        if (HudEditor.INSTANCE.customFont.getValue()) {
            WurstplusFour.GUI_FONT_MANAGER.drawStringRainbow(text, getX(), getY(), true);
        }
        HudUtil.drawHudString(text, getX(), getY(), HudEditor.INSTANCE.fontColor.getValue().hashCode());
    }

}
