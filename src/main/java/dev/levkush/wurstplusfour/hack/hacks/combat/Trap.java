package dev.levkush.wurstplusfour.hack.hacks.combat;

import dev.levkush.wurstplusfour.WurstplusFour;
import dev.levkush.wurstplusfour.setting.type.BooleanSetting;
import dev.levkush.wurstplusfour.setting.type.EnumSetting;
import dev.levkush.wurstplusfour.setting.type.IntSetting;
import dev.levkush.wurstplusfour.util.BlockUtil;
import dev.levkush.wurstplusfour.util.PlayerUtil;
import dev.levkush.wurstplusfour.hack.Hack;
import dev.levkush.wurstplusfour.hack.HackPriority;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Hack.Registration(name = "Trap", description = "traps people", category = Hack.Category.COMBAT, priority = HackPriority.High)
public class Trap extends Hack {

    EnumSetting mode = new EnumSetting("Mode", "Extra", Arrays.asList("Extra", "Face", "Normal", "Feet", "Sand"),this);
    IntSetting blocksPerTick = new IntSetting("Speed", 4, 0, 8, this);
    BooleanSetting rotate = new BooleanSetting("Rotate", true, this);
    EnumSetting swing = new EnumSetting("Swing", "Mainhand", Arrays.asList("Mainhand", "Offhand", "None"), this);


    private final Vec3d[] offsetsDefault = new Vec3d[]{
            new Vec3d(0.0, 0.0, -1.0),
            new Vec3d(1.0, 0.0, 0.0),
            new Vec3d(0.0, 0.0, 1.0),
            new Vec3d(-1.0, 0.0, 0.0),
            new Vec3d(0.0, 1.0, -1.0),
            new Vec3d(1.0, 1.0, 0.0),
            new Vec3d(0.0, 1.0, 1.0),
            new Vec3d(-1.0, 1.0, 0.0),
            new Vec3d(0.0, 2.0, -1.0),
            new Vec3d(1.0, 2.0, 0.0),
            new Vec3d(0.0, 2.0, 1.0),
            new Vec3d(-1.0, 2.0, 0.0),
            new Vec3d(0.0, 3.0, -1.0),
            new Vec3d(0.0, 3.0, 1.0),
            new Vec3d(1.0, 3.0, 0.0),
            new Vec3d(-1.0, 3.0, 0.0),
            new Vec3d(0.0, 3.0, 0.0)
    };

    private final Vec3d[] offsetsFace = new Vec3d[]{
            new Vec3d(0.0, 0.0, -1.0),
            new Vec3d(1.0, 0.0, 0.0),
            new Vec3d(0.0, 0.0, 1.0),
            new Vec3d(-1.0, 0.0, 0.0),
            new Vec3d(0.0, 1.0, -1.0),
            new Vec3d(1.0, 1.0, 0.0),
            new Vec3d(0.0, 1.0, 1.0),
            new Vec3d(-1.0, 1.0, 0.0),
            new Vec3d(0.0, 2.0, -1.0),
            new Vec3d(0.0, 3.0, -1.0),
            new Vec3d(0.0, 3.0, 1.0),
            new Vec3d(1.0, 3.0, 0.0),
            new Vec3d(-1.0, 3.0, 0.0),
            new Vec3d(0.0, 3.0, 0.0)
    };

    private final Vec3d[] offsetsFeet = new Vec3d[]{
            new Vec3d(0.0, 0.0, -1.0),
            new Vec3d(1.0, 0.0, 0.0),
            new Vec3d(0.0, 0.0, 1.0),
            new Vec3d(-1.0, 0.0, 0.0),
            new Vec3d(0.0, 1.0, -1.0),
            new Vec3d(0.0, 2.0, -1.0),
            new Vec3d(1.0, 2.0, 0.0),
            new Vec3d(0.0, 2.0, 1.0),
            new Vec3d(-1.0, 2.0, 0.0),
            new Vec3d(0.0, 3.0, -1.0),
            new Vec3d(0.0, 3.0, 0.0)
    };

    private final Vec3d[] offsetsExtra = new Vec3d[]{
            new Vec3d(0.0, 0.0, -1.0),
            new Vec3d(1.0, 0.0, 0.0),
            new Vec3d(0.0, 0.0, 1.0),
            new Vec3d(-1.0, 0.0, 0.0),
            new Vec3d(0.0, 1.0, -1.0),
            new Vec3d(1.0, 1.0, 0.0),
            new Vec3d(0.0, 1.0, 1.0),
            new Vec3d(-1.0, 1.0, 0.0),
            new Vec3d(0.0, 2.0, -1.0),
            new Vec3d(1.0, 2.0, 0.0),
            new Vec3d(0.0, 2.0, 1.0),
            new Vec3d(-1.0, 2.0, 0.0),
            new Vec3d(0.0, 3.0, -1.0),
            new Vec3d(0.0, 3.0, 0.0),
            new Vec3d(0.0, 4.0, 0.0)
    };

    private int offsetStep = 0;
    private int yLevel;
    private String lastTickTargetName = "";


    private boolean firstRun = true;

    @Override
    public void onEnable() {
        yLevel = (int) Math.round(mc.player.posY);
        firstRun = true;

        if (PlayerUtil.findObiInHotbar() == -1) {
            this.disable();
        }

        if (PlayerUtil.findSandInHotbar() == -1 && mode.is("Sand")) {
            this.disable();
        }
    }

    @Override
    public void onUpdate() {
        EntityPlayer closest_target = findClosestTarget();

        if (closest_target == null) {
            this.disable();
            return;
        }

        if ((int) Math.round(mc.player.posY) != yLevel) {
            this.disable();
            return;
        }

        if (firstRun) {
            firstRun = false;
            lastTickTargetName = closest_target.getName();
        } else if (!lastTickTargetName.equals(closest_target.getName())) {
            lastTickTargetName = closest_target.getName();
            offsetStep = 0;
        }

        final List<Vec3d> place_targets = new ArrayList<Vec3d>();

        if (mode.is("Normal") || mode.is("Sand")) {
            Collections.addAll(place_targets, offsetsDefault);
        } else if (mode.is("Extra")) {
            Collections.addAll(place_targets, offsetsExtra);
        } else if (mode.is("Feet")) {
            Collections.addAll(place_targets, offsetsFeet);
        } else {
            Collections.addAll(place_targets, offsetsFace);
        }

        int blocks_placed = 0;

        while (blocks_placed < blocksPerTick.getValue()) {
            boolean shouldSandPlace = false;
            if (offsetStep >= place_targets.size()) {
                offsetStep = 0;
                break;
            }
            if (mode.is("Sand")) {
                if (offsetStep == 16) {
                    shouldSandPlace = true;
                }
            }
            final BlockPos offset_pos = new BlockPos(place_targets.get(offsetStep));
            final BlockPos target_pos = new BlockPos(closest_target.getPositionVector()).down().add(offset_pos.getX(), offset_pos.getY(), offset_pos.getZ());
            boolean should_try_place = true;

            if (!mc.world.getBlockState(target_pos).getMaterial().isReplaceable())
                should_try_place = false;

            for (final Entity entity : mc.world.getEntitiesWithinAABBExcludingEntity(null, new AxisAlignedBB(target_pos))) {
                if (!(entity instanceof EntityItem) && !(entity instanceof EntityXPOrb)) {
                    should_try_place = false;
                    break;
                }
            }

            if (should_try_place && BlockUtil.placeBlock(target_pos, shouldSandPlace ? PlayerUtil.findSandInHotbar() : PlayerUtil.findObiInHotbar(), rotate.getValue(), rotate.getValue(), swing)) {
                ++blocks_placed;
            }

            offsetStep++;

        }
    }

    public EntityPlayer findClosestTarget()  {

        if (mc.world.playerEntities.isEmpty())
            return null;

        EntityPlayer closestTarget = null;

        for (final EntityPlayer target : mc.world.playerEntities)
        {
            if (target == mc.player || !target.isEntityAlive())
                continue;

            if (WurstplusFour.FRIEND_MANAGER.isFriend(target.getName()))
                continue;

            if (target.getHealth() <= 0.0f)
                continue;

            if (closestTarget != null)
                if (mc.player.getDistance(target) > mc.player.getDistance(closestTarget))
                    continue;

            closestTarget = target;
        }

        return closestTarget;
    }

}
