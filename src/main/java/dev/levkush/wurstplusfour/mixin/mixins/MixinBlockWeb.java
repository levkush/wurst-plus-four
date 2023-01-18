package dev.levkush.wurstplusfour.mixin.mixins;

import dev.levkush.wurstplusfour.WurstplusFour;
import dev.levkush.wurstplusfour.event.events.BlockCollisionBoundingBoxEvent;
import net.minecraft.block.BlockWeb;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * @author Madmegsox1
 * @since 04/05/2021
 */

@Mixin(BlockWeb.class)
public class MixinBlockWeb {
    @Inject(method = "getCollisionBoundingBox", at = @At("HEAD"), cancellable = true)
    public void getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos, final CallbackInfoReturnable<AxisAlignedBB> callbackInfoReturnable)
    {
        BlockCollisionBoundingBoxEvent bb = new BlockCollisionBoundingBoxEvent(pos);
        WurstplusFour.EVENT_PROCESSOR.postEvent(bb);
        if(bb.isCancelled()){
            callbackInfoReturnable.setReturnValue(bb.getBoundingBox());
            callbackInfoReturnable.cancel();
        }
    }
}
