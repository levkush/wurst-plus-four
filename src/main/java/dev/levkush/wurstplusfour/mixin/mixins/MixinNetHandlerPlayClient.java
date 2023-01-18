package dev.levkush.wurstplusfour.mixin.mixins;

import dev.levkush.wurstplusfour.WurstplusFour;
import dev.levkush.wurstplusfour.event.events.DeathEvent;
import dev.levkush.wurstplusfour.util.Globals;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.play.server.SPacketEntityMetadata;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value={NetHandlerPlayClient.class})
public class MixinNetHandlerPlayClient {
    @Inject(method={"handleEntityMetadata"}, at={@At(value="RETURN")}, cancellable=true)
    private void handleEntityMetadataHook(SPacketEntityMetadata packetIn, CallbackInfo info) {
        EntityPlayer player;
        Entity entity;
        if (Globals.mc.world != null && (entity = Globals.mc.world.getEntityByID(packetIn.getEntityId())) instanceof EntityPlayer
                && (player = (EntityPlayer)entity).getHealth() <= 0.0f) {
            WurstplusFour.EVENT_PROCESSOR.postEvent(new DeathEvent(player));
            WurstplusFour.POP_MANAGER.onDeath(player);
            if (player.getName().equalsIgnoreCase(Globals.mc.player.getName())) {
                WurstplusFour.KD_MANAGER.addDeath();
            }
        }
    }
}

