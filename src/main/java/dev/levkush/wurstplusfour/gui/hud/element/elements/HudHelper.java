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

import java.util.ArrayList;
import java.util.List;

@HudElement.Element(name = "Helper", posX = 100, posY = 100)
public class HudHelper extends HudElement {

    List<String> displayStrings = new ArrayList<>();
    int width = 0;
    int currentlyDrawn = 0;

    @Override
    public int getHeight(){
        return currentlyDrawn;
    }

    @Override
    public int getWidth(){
        return HudUtil.getHudStringWidth("AA");
    }

    @Override
    public void onRender2D(Render2DEvent event){
        displayStrings.clear();

        int padding = 5;
        Colour fill = new Colour(77, 77, 77, 200);
        Colour outline = new Colour(127, 127, 127, 255);
        RenderUtil2D.drawBorderedRect(this.getX() - padding, this.getY() - padding,
                this.getX() + padding + this.getWidth(), this.getY() + padding + this.getHeight() - 1, 1, fill.hashCode(), outline.hashCode(), false);
        RenderUtil2D.drawHLineG(this.getX() - padding, this.getY() - padding,
                (this.getX() + padding + this.getWidth()) - (this.getX() - padding), Rainbow.getColour().hashCode(), Rainbow.getFurtherColour(HudEditor.INSTANCE.welcomerOffset.getValue()).hashCode());

        String ca = (WurstplusFour.HACKS.ishackEnabled("Crystal Aura") ? ChatFormatting.GREEN + "CA" : (HudEditor.INSTANCE.showOff.getValue() ? ChatFormatting.RED + "CA" : "")) + ChatFormatting.RESET;
        displayStrings.add(ca);
        String hf = (WurstplusFour.HACKS.ishackEnabled("Hole Fill") ? ChatFormatting.GREEN + "HF" : (HudEditor.INSTANCE.showOff.getValue() ? ChatFormatting.RED + "HF" : "")) + ChatFormatting.RESET;
        displayStrings.add(hf);
        String tr = (WurstplusFour.HACKS.ishackEnabled("Trap") ? ChatFormatting.GREEN + "TR" : (HudEditor.INSTANCE.showOff.getValue() ? ChatFormatting.RED + "TR" : "")) + ChatFormatting.RESET;
        displayStrings.add(tr);
        String sr = (WurstplusFour.HACKS.ishackEnabled("Surround") ? ChatFormatting.GREEN + "SR" : (HudEditor.INSTANCE.showOff.getValue() ? ChatFormatting.RED + "SR" : "")) + ChatFormatting.RESET;
        displayStrings.add(sr);
        String ka = (WurstplusFour.HACKS.ishackEnabled("Kill Aura") ? ChatFormatting.GREEN + "KA" : (HudEditor.INSTANCE.showOff.getValue() ? ChatFormatting.RED + "KA" : "")) + ChatFormatting.RESET;
        displayStrings.add(ka);

        displayStrings.removeIf(string -> string.equalsIgnoreCase("" + ChatFormatting.RESET));

        int i = 0;
        int vOffset = HudUtil.getHudStringHeight() + 4;
        int biggestWidth = 0;
        for (String string : displayStrings) {
            HudUtil.drawHudString(string, getX(), getY() + (i * vOffset) + 2, HudEditor.INSTANCE.fontColor.getValue().hashCode());

            int w = HudUtil.getHudStringWidth(string);
            if (w > biggestWidth) {
                biggestWidth = w;
                width = w;
            }
            i++;
        }
        currentlyDrawn = (i * vOffset);

    }
}
