package dev.levkush.wurstplusfour.hack.hacks.player;

import dev.levkush.wurstplusfour.event.Events;
import dev.levkush.wurstplusfour.event.events.PacketEvent;
import dev.levkush.wurstplusfour.event.processor.CommitEvent;
import dev.levkush.wurstplusfour.util.ClientMessage;
import dev.levkush.wurstplusfour.util.MathsUtil;
import dev.levkush.wurstplusfour.setting.type.BooleanSetting;
import dev.levkush.wurstplusfour.util.EntityUtil;
import dev.levkush.wurstplusfour.util.elements.Timer;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.item.EntityFireworkRocket;
import net.minecraft.init.Items;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.CPacketEntityAction;
import net.minecraft.network.play.client.CPacketHeldItemChange;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.network.play.client.CPacketPlayerTryUseItem;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.MathHelper;
import dev.levkush.wurstplusfour.setting.type.DoubleSetting;
import dev.levkush.wurstplusfour.setting.type.EnumSetting;
import dev.levkush.wurstplusfour.hack.Hack;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.util.Arrays;
import java.util.EventListener;

@Hack.Registration(name = "ElytraFly", description = "makes u flight", category = Hack.Category.PLAYER)
public class ElytraFly extends Hack {

    EnumSetting mode = new EnumSetting("Mode", "Creative", Arrays.asList("Creative", "Packet", "Strict"), this);

    EnumSetting deployMode = new EnumSetting("DeploymentMode", "NotOnGround", Arrays.asList("NotOnGround", "VerticalVelocity", "ElytraFlying"), this);
    
    BooleanSetting autoBoost = new BooleanSetting("AutoBoost", false, this);
    DoubleSetting velocitySpeed = new DoubleSetting("Speed", 1.8, 0.0, 1.0, this);
    BooleanSetting useTimer = new BooleanSetting("TimerTakeoff", false, this);
    DoubleSetting timerMultiplier = new DoubleSetting("TimerMultiplier", 1.8, 0.0, 1.0, this);
    boolean isHackFlying = false;
    int tickspassed = 0;
    int entryID = -1;

    private final Timer timer = new Timer();

    @Override
    public void onEnable() {
        if(mc.player==null)return;
        if (mode.getValue() == "Creative") {
            mc.player.capabilities.setFlySpeed(velocitySpeed.getValue().floatValue()/35);
        }
        EntityUtil.setTimer(2);
        super.onEnable();
    }

    @Override
    public void onDisable() {
        if (mc.player == null) return;
        isHackFlying = false;
        mc.player.capabilities.isFlying = false;
        ElytraFly.mc.player.capabilities.setFlySpeed(0.05f);
        if (mode.getValue() == "Packet") {
            ElytraFly.mc.player.capabilities.isFlying = false;
            ElytraFly.mc.player.capabilities.setFlySpeed(0.05f);
            if (!ElytraFly.mc.player.capabilities.isCreativeMode) {
                ElytraFly.mc.player.capabilities.allowFlying = false;
            }
        }
        EntityUtil.resetTimer();
        this.timer.reset();
        super.onDisable();
    }

    int i = 0;
    @Mod.EventHandler
    private void EventListener(TickEvent.ClientTickEvent event) {
        if (mc.player == null) return;

        final ItemStack chestplateSlot = mc.player.getItemStackFromSlot(EntityEquipmentSlot.CHEST);
        if (!(chestplateSlot.getItem() == Items.ELYTRA)) return;


        if (mode.getValue() == "Creative") {
            mc.player.capabilities.setFlySpeed(velocitySpeed.getValue().floatValue()/35);
        }


        if (mc.player.onGround) isHackFlying = false;
        if(!mc.player.onGround && useTimer.getValue()) {
            EntityUtil.setTimer(timerMultiplier.getValue().floatValue());
        }
        if((isHackFlying || mc.player.onGround) && useTimer.getValue()){
            EntityUtil.resetTimer();
        }


        //CHECK IF THE CLIENT SHOULD START HACK FLYING
        if ((deployMode.getValue() == "NotOnGround") && !isHackFlying) {
            if (!mc.player.onGround) {
                isHackFlying = true;
                mc.player.connection.sendPacket(new CPacketEntityAction(mc.player, CPacketEntityAction.Action.START_FALL_FLYING));
                mc.player.capabilities.isFlying = true;
            }
        } else if ((mc.player.motionY < -0.15 && deployMode.getValue() =="VerticalVelocity") && !isHackFlying && !mc.player.isElytraFlying()) {
            isHackFlying = true;
            mc.player.setVelocity(mc.player.motionX, 0, mc.player.motionZ);
            mc.player.connection.sendPacket(new CPacketEntityAction(mc.player, CPacketEntityAction.Action.START_FALL_FLYING));
            mc.player.capabilities.isFlying = true;

        } else if (mc.player.isElytraFlying() && !isHackFlying) {
            isHackFlying = true;
            mc.player.capabilities.isFlying = true;

        }


        //SPAWN ROCKETS IF NON ARE PRESENT
        if (autoBoost.getValue().booleanValue() && isHackFlying) {
            //if (mc.world.loadedEntityList.stream().filter(n -> n instanceof EntityFireworkRocket).noneMatch(n -> ((EntityFireworkRocket) n).boostedEntity == mc.player)) {
            tickspassed++;
            if (tickspassed > 5) {
                tickspassed = 0;
                int oldslot = mc.player.inventory.currentItem;
                int newslot = getSlotWithRockets();
                if (newslot != -1) {
                    mc.player.inventory.currentItem = newslot;
                    mc.player.connection.sendPacket(new CPacketHeldItemChange(newslot));
                    mc.player.connection.sendPacket(new CPacketPlayerTryUseItem(EnumHand.MAIN_HAND));
                } else {
                    ClientMessage.sendErrorMessage("No rockets found in hotbar, disabling AutoBoost");
                    autoBoost.setValue(false);
                }
                mc.player.inventory.currentItem = oldslot;
                mc.player.connection.sendPacket(new CPacketHeldItemChange(oldslot));

            }
        }


        if ((mode.getValue() == "Packet") && isHackFlying) {
            if (ElytraFly.mc.player.capabilities.isFlying || ElytraFly.mc.player.isElytraFlying()) {
                ElytraFly.mc.player.setSprinting(false);
            }

            if (ElytraFly.mc.player.capabilities.isFlying) {
                ElytraFly.mc.player.setVelocity(0.0, 0.0, 0.0);
                ElytraFly.mc.player.setPosition(ElytraFly.mc.player.posX, ElytraFly.mc.player.posY - 5.0000002374872565E-5, ElytraFly.mc.player.posZ);
                ElytraFly.mc.player.capabilities.setFlySpeed(this.velocitySpeed.getValue().floatValue());
                ElytraFly.mc.player.setSprinting(false);
            }
            if (ElytraFly.mc.player.onGround) {
                ElytraFly.mc.player.capabilities.allowFlying = false;
            }
            if (ElytraFly.mc.player.isElytraFlying()) {
                ElytraFly.mc.player.capabilities.setFlySpeed(0.915f);
                ElytraFly.mc.player.capabilities.isFlying = true;
                if (!ElytraFly.mc.player.capabilities.isCreativeMode) {
                    ElytraFly.mc.player.capabilities.allowFlying = true;
                }
            }
            if (mc.gameSettings.keyBindJump.isKeyDown() || mc.gameSettings.keyBindSneak.isKeyDown()) {
                ElytraFly.mc.player.capabilities.setFlySpeed(0.05f);
            }

        }

        if ((mode.getValue() =="Strict") && isHackFlying) {
            final double deltaX = Minecraft.getMinecraft().player.posX - Minecraft.getMinecraft().player.prevPosX;
            final double deltaZ = Minecraft.getMinecraft().player.posZ - Minecraft.getMinecraft().player.prevPosZ;
            final double tickRate = (Minecraft.getMinecraft().timer.tickLength / 1000.0f);
            final double bps = (MathHelper.sqrt(deltaX * deltaX + deltaZ * deltaZ) / tickRate);
            mc.player.capabilities.isFlying = false;
            if (mc.gameSettings.keyBindJump.isKeyDown()) {
                if (i > 50) {
                    i = 0;
                }
                if (bps > 12) {
                    i--;
                    if (i < -30) {
                        i = -30;
                    }
                    mc.player.rotationPitch = (float) i;

                } else {
                    i++;
                    if (i > 20) {
                        i = 20;
                    }
                    mc.player.rotationPitch = (float) i;
                }
                mc.player.movementInput.forwardKeyDown = true;

            } else if (mc.gameSettings.keyBindSneak.isKeyDown()) {
                i = i + 2;
                if (i > 80) {
                    i = 80;
                }
                mc.player.rotationPitch = (float) i;

                /*mc.player.rotationPitch = (float) -1;
                mc.player.connection.sendPacket(new CPacketPlayer.Rotation(mc.player.getPitchYaw().y, -1, false));*/
            }
            if (mc.player.rotationPitch > 0) {
                mc.player.motionX = 0.0;
                mc.player.motionZ = 0.0;
                mc.player.motionY = 0.0f;

                if (mc.player.movementInput.sneak) {
                    mc.player.motionY = -0.8f;
                    mc.player.movementInput.forwardKeyDown = true;
                }

                if (mc.player.movementInput.forwardKeyDown && !mc.gameSettings.keyBindForward.isKeyDown()) {
                    mc.player.movementInput.forwardKeyDown = false;
                }

                final double[] directionSpeedPacket = MathsUtil.directionSpeed(velocitySpeed.getValue().floatValue());
                if (mc.player.movementInput.moveStrafe != 0.0f || mc.player.movementInput.moveForward != 0.0f) {
                    mc.player.motionX = directionSpeedPacket[0];
                    mc.player.motionZ = directionSpeedPacket[1];
                }
            }

        }
    };

    @Mod.EventHandler
    private void EventListener(PacketEvent.Receive event) {
        if (mc.player == null) return;
        if ((mode.getValue() =="Packet") && isHackFlying)
            if (event.getPacket() instanceof CPacketPlayer.PositionRotation) {
                CPacketPlayer.PositionRotation packet = (CPacketPlayer.PositionRotation) event.getPacket();
                mc.player.connection.sendPacket(new CPacketPlayer.Position(packet.x, packet.y, packet.z, packet.onGround));
                mc.player.connection.sendPacket(new CPacketPlayer.Rotation(packet.yaw, 0, packet.onGround));
                event.setCancelled(true);

            }
        };

    private void runNoKick() {
        if (!mc.player.isElytraFlying() && mc.player.ticksExisted % 4 == 0) {
            mc.player.motionY = -0.03999999910593033;
        }
    }

    private int getSlotWithRockets() {
        for (int i = 0; i < 9; i++) {
            if (mc.player.inventory.getStackInSlot(i).getItem() == Items.FIREWORKS) {
                return i;
            }
        }
        return -1;
    }

}