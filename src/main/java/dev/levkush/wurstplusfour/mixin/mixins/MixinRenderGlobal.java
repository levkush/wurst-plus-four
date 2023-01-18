package dev.levkush.wurstplusfour.mixin.mixins;

import dev.levkush.wurstplusfour.WurstplusFour;
import dev.levkush.wurstplusfour.event.events.BlockBreakingEvent;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value={RenderGlobal.class})
public abstract class MixinRenderGlobal {

    @Inject(method={"sendBlockBreakProgress"}, at={@At(value="HEAD")})
    public void sendBlockBreakProgress(int breakerId, BlockPos pos, int progress, CallbackInfo ci) {
        BlockBreakingEvent event = new BlockBreakingEvent(pos, breakerId, progress);
        WurstplusFour.EVENT_PROCESSOR.postEvent(event);
    }

}

