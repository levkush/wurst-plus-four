package dev.levkush.wurstplusfour.mixin;

import dev.levkush.wurstplusfour.WurstplusFour;
import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin;
import org.spongepowered.asm.launch.MixinBootstrap;
import org.spongepowered.asm.mixin.MixinEnvironment;
import org.spongepowered.asm.mixin.Mixins;

import java.util.Map;

public class MixinLoader
implements IFMLLoadingPlugin {
    private static boolean isObfuscatedEnvironment = false;

    public MixinLoader() {
        WurstplusFour.LOGGER.info("mixins initialized");
        MixinBootstrap.init();
        Mixins.addConfiguration("mixins.wurstplusfour.json");
        MixinEnvironment.getDefaultEnvironment().setObfuscationContext("searge");
        WurstplusFour.LOGGER.info(MixinEnvironment.getDefaultEnvironment().getObfuscationContext());
    }

    public String[] getASMTransformerClass() {
        return new String[0];
    }

    public String getModContainerClass() {
        return null;
    }

    public String getSetupClass() {
        return null;
    }

    public void injectData(Map<String, Object> data) {
        isObfuscatedEnvironment = (Boolean)data.get("runtimeDeobfuscationEnabled");
    }

    public String getAccessTransformerClass() {
        return null;
    }
}

