package dev.levkush.wurstplusfour.hack.hacks.misc;

import dev.levkush.wurstplusfour.util.PlayerUtil;
import io.netty.util.internal.ConcurrentSet;
import dev.levkush.wurstplusfour.hack.Hack;
import dev.levkush.wurstplusfour.hack.HackPriority;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;

import java.util.List;

import static dev.levkush.wurstplusfour.util.PlayerUtil.getPlayerPos;

/**
 * @author Madmegsox1
 * @since 28/04/2021
 *  - not china code i promise!
 */
@Hack.Registration(name = "Anti Void", description = "stops dumb ppl (you) falling into the void", category = Hack.Category.MISC, priority = HackPriority.High)
public class AntiVoid extends Hack {

    private ConcurrentSet<BlockPos> voidHoles;

    public void onUpdate(){
        if(nullCheck())return;
        if (mc.player.getPosition().getY() > 1) {
            return;
        }
        if (voidHoles == null) {
            voidHoles = new ConcurrentSet<>();
        }
        else {
            voidHoles.clear();
        }

        List<BlockPos> blockPosList = PlayerUtil.getSphere(PlayerUtil.getPlayerPos(), 10, 6, false, true, 0);

        for (BlockPos blockPos : blockPosList) {
            if (mc.world.getBlockState(blockPos).getBlock().equals(Blocks.BEDROCK) || mc.world.getBlockState(blockPos).getBlock().equals(Blocks.OBSIDIAN)) {
                continue;
            }
            if (isAnyBedrock(blockPos, Offsets.center)) {
                continue;
            }
            voidHoles.add(blockPos);
        }


        if(voidHoles.contains(new BlockPos(PlayerUtil.getPlayerPos().getX(), PlayerUtil.getPlayerPos().getY() - 2, PlayerUtil.getPlayerPos().getZ()))){
            mc.player.motionY = 0.1;
        }
    }

    private boolean isAnyBedrock(BlockPos origin, BlockPos[] offset) {
        for (BlockPos pos : offset) {
            if (mc.world.getBlockState(origin.add(pos)).getBlock().equals(Blocks.BEDROCK) || mc.world.getBlockState(origin.add(pos)).getBlock().equals(Blocks.OBSIDIAN)) {
                return true;
            }
        } return false;
    }

    private static class Offsets {
        static final BlockPos[] center = {
                new BlockPos(0, 0, 0),
                new BlockPos(0, 1, 0),
                new BlockPos(0, 2, 0)
        };
    }
}
