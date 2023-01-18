package dev.levkush.wurstplusfour.hack.hacks.misc;

import dev.levkush.wurstplusfour.WurstplusFour;
import dev.levkush.wurstplusfour.hack.Hack;
import dev.levkush.wurstplusfour.hack.HackPriority;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.RayTraceResult;
import org.lwjgl.input.Mouse;

@Hack.Registration(name = "MCF", description = "middleclick adds friend", category = Hack.Category.MISC, priority = HackPriority.Lowest)
public class MCF extends Hack {

    private boolean isButtonDown = false;

    @Override
    public void onUpdate() {
        if (Mouse.isButtonDown(2)) {
            if (!this.isButtonDown && mc.currentScreen == null) {
                this.onClick();
            }
            this.isButtonDown = true;
        } else {
            this.isButtonDown = false;
        }
    }

    private void onClick() {
        Entity entity;
        RayTraceResult result = MCF.mc.objectMouseOver;
        if (result != null && result.typeOfHit == RayTraceResult.Type.ENTITY && (entity = result.entityHit) instanceof EntityPlayer) {
            WurstplusFour.FRIEND_MANAGER.toggleFriend(entity.getName());
        }
        this.isButtonDown = true;
    }

}
