package dev.levkush.wurstplusfour.hack.hacks.misc;

import dev.levkush.wurstplusfour.WurstplusFour;
import dev.levkush.wurstplusfour.event.events.BlockCollisionBoundingBoxEvent;
import dev.levkush.wurstplusfour.event.processor.CommitEvent;
import dev.levkush.wurstplusfour.event.processor.EventPriority;
import dev.levkush.wurstplusfour.setting.type.BooleanSetting;
import dev.levkush.wurstplusfour.setting.type.DoubleSetting;
import dev.levkush.wurstplusfour.hack.Hack;
import dev.levkush.wurstplusfour.hack.HackPriority;
import net.minecraft.block.Block;
import net.minecraft.block.BlockWeb;
import org.lwjgl.input.Keyboard;

/**
 * @author Madmegsox1
 * @since 04/05/2021
 */

@Hack.Registration(name = "Anti Web", description = "stops faggots from trapping you in webs", category = Hack.Category.MISC, priority = HackPriority.High)
public class AntiWeb extends Hack {

    BooleanSetting disableBB = new BooleanSetting("AddBB", true, this);
    DoubleSetting bbOffset = new DoubleSetting("BBOffset", 0.0, -2.0, 2.0,this, s -> disableBB.getValue());
    BooleanSetting onGround = new BooleanSetting("OnGround", true, this);
    DoubleSetting motionY = new DoubleSetting("SetMotionY", 1.0, 0.0, 20.0, this);
    DoubleSetting motionX = new DoubleSetting("SetMotionX", 0.84, -1.0, 5.0, this);

    @CommitEvent(priority = EventPriority.LOW)
    public void bbEvent(BlockCollisionBoundingBoxEvent event){
        if(nullCheck())return;
        if(mc.world.getBlockState(event.getPos()).getBlock() instanceof BlockWeb){
            if(disableBB.getValue()) {
                event.setCancelled(true);
                event.setBoundingBox(Block.FULL_BLOCK_AABB.contract(0, bbOffset.getValue(), 0));
            }
        }
    }


    @Override
    public void onUpdate(){
        if(nullCheck())return;
        if(mc.player.isInWeb && !WurstplusFour.HACKS.ishackEnabled("Step")){
            if(Keyboard.isKeyDown(mc.gameSettings.keyBindSneak.keyCode)){
                mc.player.isInWeb = true;
                mc.player.motionY *= motionY.getValue();
            }
            else if(onGround.getValue()){
                mc.player.onGround = false;
            }
            if(Keyboard.isKeyDown(mc.gameSettings.keyBindForward.keyCode) ||  Keyboard.isKeyDown(mc.gameSettings.keyBindBack.keyCode) || Keyboard.isKeyDown(mc.gameSettings.keyBindLeft.keyCode)
                    || Keyboard.isKeyDown(mc.gameSettings.keyBindRight.keyCode)) {
                mc.player.isInWeb = false;
                mc.player.motionX *= motionX.getValue();
                mc.player.motionZ *= motionX.getValue();
            }

        }
    }
}
