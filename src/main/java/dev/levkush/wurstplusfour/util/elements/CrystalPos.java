package dev.levkush.wurstplusfour.util.elements;

import net.minecraft.util.math.BlockPos;

public class CrystalPos {

    private final BlockPos pos;
    private final Double damage;

    public CrystalPos(BlockPos pos, Double damage) {
        this.pos = pos;
        this.damage = damage;
    }

    public BlockPos getPos() {
        return pos;
    }

    public Double getDamage() {
        return damage;
    }
}
