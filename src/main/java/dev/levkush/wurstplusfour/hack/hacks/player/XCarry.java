package dev.levkush.wurstplusfour.hack.hacks.player;

import dev.levkush.wurstplusfour.event.events.PacketEvent;
import dev.levkush.wurstplusfour.event.processor.CommitEvent;
import dev.levkush.wurstplusfour.event.processor.EventPriority;
import dev.levkush.wurstplusfour.hack.Hack;
import net.minecraft.network.play.client.CPacketCloseWindow;

@Hack.Registration(name = "XCarry", description = "carrys stuff", category = Hack.Category.PLAYER)
public class XCarry extends Hack {

    @CommitEvent(priority = EventPriority.LOW)
    public void onCloseGuiScreen(PacketEvent.Send event) {
        if (event.getPacket() instanceof CPacketCloseWindow) {
            CPacketCloseWindow packet = event.getPacket();
            if (packet.windowId == XCarry.mc.player.inventoryContainer.windowId) {
                event.setCancelled(true);
            }
        }
    }

}
