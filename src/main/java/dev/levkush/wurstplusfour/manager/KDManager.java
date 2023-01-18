package dev.levkush.wurstplusfour.manager;

import com.mojang.realmsclient.gui.ChatFormatting;
import dev.levkush.wurstplusfour.WurstplusFour;
import dev.levkush.wurstplusfour.event.events.DeathEvent;
import dev.levkush.wurstplusfour.event.events.PacketEvent;
import dev.levkush.wurstplusfour.event.processor.CommitEvent;
import dev.levkush.wurstplusfour.hack.hacks.chat.AutoEz;
import dev.levkush.wurstplusfour.hack.hacks.chat.TotemPopCounter;
import dev.levkush.wurstplusfour.util.ClientMessage;
import dev.levkush.wurstplusfour.util.Globals;
import dev.levkush.wurstplusfour.util.MathsUtil;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.play.client.CPacketUseEntity;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

public class KDManager implements Globals {
    public final ConcurrentHashMap<String, Integer> targets = new ConcurrentHashMap<>();

    private double totalDeaths;
    private double totalKills;
    private int currentKills;
    private String kdString = ChatFormatting.RED +  "[" + ChatFormatting.GOLD + "KD" + ChatFormatting.RED + "] " + ChatFormatting.RESET;

    public int getCurrentKills() {
        return currentKills;
    }

    public String getKD() {
        if (totalDeaths == 0) {
            return "" + totalKills;
        }
        return "" + MathsUtil.round(totalKills / totalDeaths, 2);
    }

    public String getTotalKills() {
        return "" + totalKills;
    }

    public String getTotalDeaths() {
        return "" + totalDeaths;
    }

    public KDManager() {
        WurstplusFour.EVENT_PROCESSOR.addEventListener(this);
    }

    public void addDeath() {
        totalDeaths++;
        if(TotemPopCounter.INSTANCE.kdMessages.getValue()){
            ClientMessage.sendMessage(kdString + "Your KD is " + getKD() + "!");
        }
        currentKills = 0;
    }

    public void onUpdate() {
        targets.forEach((name, timeout) -> {
            if (timeout <= 0) {
                targets.remove(name);
            } else {
                targets.put(name, timeout - 1);
            }

        });
    }

    @SubscribeEvent
    public void onAttackEntity(AttackEntityEvent event) {
        if (event.getTarget() instanceof EntityPlayer && !WurstplusFour.FRIEND_MANAGER.isFriend(event.getEntityPlayer().getName())) {
            this.targets.put(event.getTarget().getName(), 20);
        }
    }

    @CommitEvent
    public void onSendAttackPacket(PacketEvent.Send event) {
        CPacketUseEntity packet;
        if (event.getPacket() instanceof CPacketUseEntity && (packet = event.getPacket()).getAction() == CPacketUseEntity.Action.ATTACK && packet.getEntityFromWorld(mc.world) instanceof EntityPlayer && !WurstplusFour.FRIEND_MANAGER.isFriend(Objects.requireNonNull(packet.getEntityFromWorld(mc.world)).getName())) {
            this.targets.put(Objects.requireNonNull(packet.getEntityFromWorld(mc.world)).getName(), 20);
        }
    }

    @CommitEvent
    public void onEntityDeath(DeathEvent event) {
        if (this.targets.containsKey(event.player.getName())) {
            totalKills++;
            currentKills++;
            if(TotemPopCounter.INSTANCE.kdMessages.getValue()){
                ClientMessage.sendMessage(kdString + "Your KD is " + getKD() + "!");
            }
            AutoEz.INSTANCE.announceDeath();
            this.targets.remove(event.player.getName());
        }
    }

}
