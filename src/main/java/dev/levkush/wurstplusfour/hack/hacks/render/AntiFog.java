package dev.levkush.wurstplusfour.hack.hacks.render;

import dev.levkush.wurstplusfour.setting.type.BooleanSetting;
import dev.levkush.wurstplusfour.setting.type.ColourSetting;
import dev.levkush.wurstplusfour.util.elements.Colour;
import dev.levkush.wurstplusfour.hack.Hack;
import net.minecraftforge.client.event.EntityViewRenderEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Hack.Registration(name = "Anti Fog", description = "removes fog", category = Hack.Category.RENDER, isListening = false)
public class AntiFog extends Hack {

    public BooleanSetting clear = new BooleanSetting("RemoveFog", true, this);
    public BooleanSetting colour = new BooleanSetting("ColourFog", true, this);
    public ColourSetting overworldColour = new ColourSetting("Overworld", new Colour(255, 255, 255, 255), this, s -> colour.getValue());
    public ColourSetting netherColour = new ColourSetting("Nether", new Colour(255, 255, 255, 255), this, s -> colour.getValue());
    public ColourSetting endColour = new ColourSetting("End", new Colour(255, 255, 255, 255), this, s -> colour.getValue());

    @SubscribeEvent
    public void fogDensity(EntityViewRenderEvent.FogDensity event) {
        if (clear.getValue()) {
            event.setDensity(0);
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public void fogColour(EntityViewRenderEvent.FogColors event) {
        if (colour.getValue()) {
            switch (mc.player.dimension) {
                case 0:
                    event.setRed(overworldColour.getValue().getRed() / 255f);
                    event.setGreen(overworldColour.getValue().getGreen() / 255f);
                    event.setBlue(overworldColour.getValue().getBlue() / 255f);
                case 1:
                    event.setRed(endColour.getValue().getRed() / 255f);
                    event.setGreen(endColour.getValue().getGreen() / 255f);
                    event.setBlue(endColour.getValue().getBlue() / 255f);
                case -1:
                    event.setRed(netherColour.getValue().getRed() / 255f);
                    event.setGreen(netherColour.getValue().getGreen() / 255f);
                    event.setBlue(netherColour.getValue().getBlue() / 255f);
            }
        }
    }

}
