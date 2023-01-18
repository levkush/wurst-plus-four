package dev.levkush.wurstplusfour.hack.hacks.render;

import dev.levkush.wurstplusfour.event.events.PacketEvent;
import dev.levkush.wurstplusfour.event.events.RenderEntityModelEvent;
import dev.levkush.wurstplusfour.event.processor.CommitEvent;
import dev.levkush.wurstplusfour.setting.type.BooleanSetting;
import dev.levkush.wurstplusfour.setting.type.ColourSetting;
import dev.levkush.wurstplusfour.setting.type.DoubleSetting;
import dev.levkush.wurstplusfour.util.EntityUtil;
import dev.levkush.wurstplusfour.util.elements.Colour;
import dev.levkush.wurstplusfour.hack.Hack;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.network.play.server.SPacketDestroyEntities;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Hack.Registration(name = "Crystal Render", description = "changes how crystal renders", category = Hack.Category.RENDER, isListening = false)
public class CrystalRender extends Hack {

    public static CrystalRender INSTANCE;

    public CrystalRender() {
        INSTANCE = this;
    }

    public BooleanSetting chams = new BooleanSetting("Chams", false, this);
    public BooleanSetting glint = new BooleanSetting("Glint", false, this);
    public BooleanSetting effect = new BooleanSetting("Effect", false, this);
    public BooleanSetting wireframe = new BooleanSetting("Wireframe", false, this);
    public BooleanSetting throughwalls = new BooleanSetting("Walls", false, this);
    public BooleanSetting xqz = new BooleanSetting("XQZ", false, this);

    public ColourSetting colour = new ColourSetting("Colour", new Colour(255, 255, 255, 150), this);
    public ColourSetting wireColour = new ColourSetting("WireframeColour", new Colour(0, 0, 0, 150), this);
    public ColourSetting hiddenColour = new ColourSetting("HiddenColour", new Colour(255, 255, 255, 150), this);

    public DoubleSetting width = new DoubleSetting("Width", 3.0, 0.1, 5.0, this);
    public DoubleSetting scale = new DoubleSetting("Scale", 1.0, 0.1, 3.0, this);

    public Map<EntityEnderCrystal, Float> scaleMap = new ConcurrentHashMap<>();

    @Override
    public void onUpdate() {
        if(nullCheck())return;
        for (Entity crystal : mc.world.loadedEntityList) {
            if (crystal instanceof EntityEnderCrystal) {
                if (!this.scaleMap.containsKey(crystal)) {
                    this.scaleMap.put((EntityEnderCrystal) crystal, 3.125E-4f);
                } else {
                    try { // this just crashed my game so its time to 'fix' it
                        this.scaleMap.put((EntityEnderCrystal) crystal, this.scaleMap.get(crystal) + 3.125E-4f);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                if (!(this.scaleMap.get(crystal) >= 0.0625f * this.scale.getValue()))
                    continue;
                this.scaleMap.remove(crystal);
            }
        }
    }

    @CommitEvent
    public void onPacket(PacketEvent.Receive event) {
        if (event.getPacket() instanceof SPacketDestroyEntities) {
            SPacketDestroyEntities packet = event.getPacket();
            for (int id : packet.getEntityIDs()) {
                try {
                    Entity entity = mc.world.getEntityByID(id);
                    if (entity instanceof EntityEnderCrystal) {
                        this.scaleMap.remove(entity);
                    }
                } catch (Exception ignored) {}
            }
        }
    }

    public void onRenderModel(RenderEntityModelEvent event) {
        if(nullCheck())return;
        if (event.getStage() != 0 || !(event.entity instanceof EntityEnderCrystal) || !this.wireframe.getValue()) {
            return;
        }
        Color colour = EntityUtil.getColor(event.entity, this.wireColour.getValue().getRed(), this.wireColour.getValue().getGreen(), this.wireColour.getValue().getBlue(), wireColour.getValue().getAlpha(), false);
        mc.gameSettings.fancyGraphics = false;
        mc.gameSettings.gammaSetting = 10000.0f;
        GL11.glPushMatrix();
        GL11.glPushAttrib(1048575);
        GL11.glPolygonMode(1032, 6913);
        GL11.glDisable(3553);
        GL11.glDisable(2896);
        if (this.throughwalls.getValue()) {
            GL11.glDisable(2929);
        }
        GL11.glEnable(2848);
        GL11.glEnable(3042);
        GlStateManager.blendFunc(770, 771);
        GlStateManager.color((float) colour.getRed() / 255.0f, (float) colour.getGreen() / 255.0f, (float) colour.getBlue() / 255.0f, (float) colour.getAlpha() / 255.0f);
        GlStateManager.glLineWidth(this.width.getValue().floatValue());
        event.modelBase.render(event.entity, event.limbSwing, event.limbSwingAmount, event.age, event.headYaw, event.headPitch, event.scale);
        GL11.glPopAttrib();
        GL11.glPopMatrix();
    }

}
