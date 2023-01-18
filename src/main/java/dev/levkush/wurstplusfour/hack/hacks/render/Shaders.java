package dev.levkush.wurstplusfour.hack.hacks.render;

import dev.levkush.wurstplusfour.setting.type.BooleanSetting;
import dev.levkush.wurstplusfour.setting.type.EnumSetting;
import dev.levkush.wurstplusfour.hack.Hack;
import net.minecraft.util.ResourceLocation;
import java.util.Arrays;


import java.util.Locale;

@Hack.Registration(name = "Shaders", description = "huge module", category = Hack.Category.RENDER)
public class Shaders extends Hack {
    //testeststests
    public EnumSetting type = new EnumSetting("Mode", "Flip", Arrays.asList("AntiAlias", "Art", "Bits", "Blobs", "Blobs2", "Blur", "Bumpy", "Color_Convolve", "Creeper", "Deconverge", "Desaturate", "flip", "fxaa", "Green", "Invert", "Notch", "ntsc", "Outline", "Pencil", "Phosphor", "Scan_Pincushion", "Sobel", "Spider", "Wobble"), this);
    public BooleanSetting onlyInGui = new BooleanSetting("OnlyInGui", false, this);
    private boolean loaded = false;

    @Override
    public void onUpdate() {
        if (onlyInGui.getValue()) {
            if (mc.currentScreen != null && !loaded) {
                mc.entityRenderer.loadShader(new ResourceLocation("shaders/post/" + (type.getValue().toLowerCase(Locale.ROOT) + ".json")));
                loaded = true;
            } else if (mc.entityRenderer.isShaderActive() && mc.currentScreen == null) {
                loaded = false;
                mc.entityRenderer.getShaderGroup().deleteShaderGroup();
            }
        } else if (!mc.entityRenderer.isShaderActive()){
            mc.entityRenderer.loadShader(new ResourceLocation("shaders/post/" + (type.getValue().toLowerCase(Locale.ROOT) + ".json")));
        }
    }



    @Override
    public void onSettingChange() {
        if (mc.entityRenderer.isShaderActive())
            mc.entityRenderer.getShaderGroup().deleteShaderGroup();
        mc.entityRenderer.loadShader(new ResourceLocation("shaders/post/" + (type.getValue().toLowerCase(Locale.ROOT) + ".json")));
    }

    @Override
    public void onDisable() {
        if (mc.entityRenderer.isShaderActive()) {
            mc.entityRenderer.getShaderGroup().deleteShaderGroup();
        }
    }
}
