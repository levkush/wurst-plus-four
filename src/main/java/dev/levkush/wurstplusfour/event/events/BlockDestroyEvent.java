package dev.levkush.wurstplusfour.event.events;

import dev.levkush.wurstplusfour.event.processor.Event;
import net.minecraft.util.math.BlockPos;

public class BlockDestroyEvent extends Event {
    private BlockPos blockPos;

    public BlockDestroyEvent(BlockPos blockPos) {
        super();
        this.blockPos = blockPos;
    }

    public void setBlockPos(BlockPos blockPos) {
        this.blockPos = blockPos;
    }

    public BlockPos getBlockPos() {
        return this.blockPos;
    }

}
