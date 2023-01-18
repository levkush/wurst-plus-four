package dev.levkush.wurstplusfour.event;

import com.google.common.base.Strings;
import dev.levkush.wurstplusfour.WurstplusFour;
import dev.levkush.wurstplusfour.event.events.*;
import dev.levkush.wurstplusfour.event.processor.CommitEvent;
import dev.levkush.wurstplusfour.gui.alt.defult.GuiAltButton;
import dev.levkush.wurstplusfour.event.events.*;
import dev.levkush.wurstplusfour.hack.Hack;
import dev.levkush.wurstplusfour.hack.hacks.client.Gui;
import dev.levkush.wurstplusfour.hack.hacks.render.Chams;
import dev.levkush.wurstplusfour.util.ClientMessage;
import dev.levkush.wurstplusfour.util.Globals;
import dev.levkush.wurstplusfour.util.elements.GLUProjection;
import dev.levkush.wurstplusfour.util.elements.Timer;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GLAllocation;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.play.server.SPacketEntityStatus;
import net.minecraft.network.play.server.SPacketPlayerListItem;
import net.minecraft.network.play.server.SPacketPlayerPosLook;
import net.minecraft.network.play.server.SPacketTimeUpdate;
import net.minecraftforge.client.event.*;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.common.network.FMLNetworkEvent;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.Objects;
import java.util.UUID;

public class Events implements Globals {

    private final Timer logoutTimer = new Timer();
    private long time = -1;

    public Events() {
        MinecraftForge.EVENT_BUS.register(this);
        WurstplusFour.EVENT_PROCESSOR.addEventListener(this);
    }

    public void unload() {
        MinecraftForge.EVENT_BUS.unregister(this);
        WurstplusFour.EVENT_PROCESSOR.removeEventListener(this);
    }

    /*
    @SubscribeEvent
    public void onKeyPress(InputEvent.KeyInputEvent org.madmeg.wurstplus.event) {
        for(Hack hack : WurstplusThree.HACKS.getHacks()){
            if (hack.getBind() <= -1 || hack.getBind() == Keyboard.KEY_NONE) continue;
            if(Keyboard.isKeyDown(hack.getBind())){
                hack.toggle();
            }
        }
    }
    */


    @SubscribeEvent
    public void onMousePress(InputEvent.MouseInputEvent event){
        int button = new MouseEvent().getButton();
        if (System.currentTimeMillis() - time < Gui.INSTANCE.mouseDelay.getValue()) {
            time = System.currentTimeMillis();
            return;
        }
        time = System.currentTimeMillis();
        for(Hack hack : WurstplusFour.HACKS.getHacks()) {
            if (hack.getBind() >= -1 || hack.getBind() == Keyboard.KEY_NONE) continue;
            if (button == 0 && hack.getBind() == -2) {
                hack.toggle();
            } else if (button == 1 && hack.getBind()  == -3) {
                hack.toggle();
            } else if (button == 2 && hack.getBind() == -4) {
                hack.toggle();
            } else if (button == 3 && hack.getBind()  == -5) {
                hack.toggle();
            } else if (button == 4 && hack.getBind() == -6) {
                hack.toggle();
            }
        }
    }

    @SubscribeEvent
    public void onAttackEntity(AttackEntityEvent event) {
        if (!nullCheck()) {
            WurstplusFour.KD_MANAGER.onAttackEntity(event);
        }
    }

    @CommitEvent
    public void onSendAttackPacket(PacketEvent.Send event) {
        if (!nullCheck()) {
            WurstplusFour.KD_MANAGER.onSendAttackPacket(event);
        }
    }

    @CommitEvent
    public void onEntityDeath(DeathEvent event) {
        if (!nullCheck()) {
            WurstplusFour.KD_MANAGER.onEntityDeath(event);
        }
    }
    @SubscribeEvent
    public void onUpdate(LivingEvent.LivingUpdateEvent event) {
        if (!nullCheck() && event.getEntity().getEntityWorld().isRemote && event.getEntityLiving().equals(mc.player)) {
            WurstplusFour.HACKS.onUpdate();
            WurstplusFour.HELP_MANAGER.onUpdate();
            WurstplusFour.KD_MANAGER.onUpdate();
        }
    }

    @SubscribeEvent
    public void onTick(TickEvent.ClientTickEvent event) {
        if (!nullCheck()) {
            WurstplusFour.HACKS.onTick();
        }
    }

    @CommitEvent(priority = dev.levkush.wurstplusfour.event.processor.EventPriority.HIGH)
    public void onUpdateWalkingPlayerPost(UpdateWalkingPlayerEvent event) {
        if (nullCheck()) {
            return;
        }
        if (event.getStage() == 0) {
            WurstplusFour.ROTATION_MANAGER.updateRotations();
            WurstplusFour.POS_MANAGER.updatePosition();
        }
        if (event.getStage() == 1) {
            WurstplusFour.ROTATION_MANAGER.restoreRotations();
            WurstplusFour.POS_MANAGER.restorePosition();
        }
    }

    @SubscribeEvent(priority = EventPriority.LOW)
    public void onRenderGameOverlayEvent(RenderGameOverlayEvent.Text event) {
        if (event.getType().equals(RenderGameOverlayEvent.ElementType.TEXT)) {
            ScaledResolution resolution = new ScaledResolution(mc);
            Render2DEvent render2DEvent = new Render2DEvent(event.getPartialTicks(), resolution);
            WurstplusFour.HACKS.onRender2D(render2DEvent);
            WurstplusFour.HUD_MANAGER.onRender2D(render2DEvent);
            GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        }
    }

    @SubscribeEvent
    public void onWorldRender(RenderWorldLastEvent event) {
        if (event.isCanceled()) {
            return;
        }
        mc.profiler.startSection("wurstplus");
        GlStateManager.disableTexture2D();
        GlStateManager.enableBlend();
        GlStateManager.disableAlpha();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        GlStateManager.shadeModel(7425);
        GlStateManager.disableDepth();
        GlStateManager.glLineWidth(1.0f);
        Render3DEvent render3dEvent = new Render3DEvent(event.getPartialTicks());
        GLUProjection projection = GLUProjection.getInstance();
        IntBuffer viewPort = GLAllocation.createDirectIntBuffer(16);
        FloatBuffer modelView = GLAllocation.createDirectFloatBuffer(16);
        FloatBuffer projectionPort = GLAllocation.createDirectFloatBuffer(16);
        GL11.glGetFloat(2982, modelView);
        GL11.glGetFloat(2983, projectionPort);
        GL11.glGetInteger(2978, viewPort);
        ScaledResolution scaledResolution = new ScaledResolution(mc);
        projection.updateMatrices(viewPort, modelView, projectionPort, (double) scaledResolution.getScaledWidth() / (double) mc.displayWidth, (double) scaledResolution.getScaledHeight() / (double) mc.displayHeight);
        WurstplusFour.HACKS.onRender3D(render3dEvent);
        GlStateManager.glLineWidth(1.0f);
        GlStateManager.shadeModel(7424);
        GlStateManager.disableBlend();
        GlStateManager.enableAlpha();
        GlStateManager.enableTexture2D();
        GlStateManager.enableDepth();
        GlStateManager.enableCull();
        GlStateManager.enableCull();
        GlStateManager.depthMask(true);
        GlStateManager.enableTexture2D();
        GlStateManager.enableBlend();
        GlStateManager.enableDepth();
        mc.profiler.endSection();
    }

    @SubscribeEvent
    public void onClientDisconnect(FMLNetworkEvent.ClientDisconnectionFromServerEvent event) {
        WurstplusFour.HACKS.onLogout();
        WurstplusFour.POP_MANAGER.onLogout();
    }

    @SubscribeEvent
    public void onClientConnect(FMLNetworkEvent.ClientConnectedToServerEvent event) {
        WurstplusFour.HACKS.onLogin();
        if(mc.world == null || !mc.world.isRemote)return;
        WurstplusFour.CONFIG_MANAGER.onLogin(event);
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void onChatSent(ClientChatEvent event) {
        if (event.getMessage().startsWith(WurstplusFour.COMMANDS.getPrefix())) {
            event.setCanceled(true);
            try {
                mc.ingameGUI.getChatGUI().addToSentMessages(event.getMessage());
                if (event.getMessage().length() > 1) {
                    WurstplusFour.COMMANDS.executeCommand(event.getMessage().substring(1));
                } else {
                    ClientMessage.sendErrorMessage("enter a command");
                }
            } catch (Exception e) {
                e.printStackTrace();
                ClientMessage.sendErrorMessage("error handling command");
            }
            event.setMessage("");
        }
    }

    @CommitEvent(priority = dev.levkush.wurstplusfour.event.processor.EventPriority.HIGH)
    public final void onPacketSend(@NotNull PacketEvent.Send event) {
        WurstplusFour.ROTATION_MANAGER.onPacketSend(event);
    }

    @CommitEvent
    public void onPacketReceive(PacketEvent.Receive event) {
        if (event.getStage() != 0) {
            return;
        }
        WurstplusFour.SERVER_MANAGER.onPacketReceived();
        if (event.getPacket() instanceof SPacketPlayerPosLook) {
            WurstplusFour.ROTATION_MANAGER.onPacketReceive((SPacketPlayerPosLook) event.getPacket());
        }
        if (event.getPacket() instanceof SPacketEntityStatus) {
            SPacketEntityStatus packet = event.getPacket();
            try {
                if (packet.getOpCode() == 0x23 && packet.getEntity(mc.world) instanceof EntityPlayer) {
                    EntityPlayer player = (EntityPlayer) packet.getEntity(mc.world);
                    WurstplusFour.EVENT_PROCESSOR.addEventListener(new TotemPopEvent(player));
                    Chams.INSTANCE.onPopLol(new TotemPopEvent(player));
                    WurstplusFour.POP_MANAGER.onTotemPop(player);
                }
            } catch (Exception ignored) {}
        } else if (event.getPacket() instanceof SPacketPlayerListItem && !nullCheck() && this.logoutTimer.passedS(1.0)) {
            SPacketPlayerListItem packet = event.getPacket();
            if (!SPacketPlayerListItem.Action.ADD_PLAYER.equals(packet.getAction()) && !SPacketPlayerListItem.Action.REMOVE_PLAYER.equals(packet.getAction())) {
                return;
            }
            packet.getEntries().stream().filter(Objects::nonNull).filter(data -> !Strings.isNullOrEmpty(data.getProfile().getName()) || data.getProfile().getId() != null).forEach(data -> {
                UUID id = data.getProfile().getId();
                switch (packet.getAction()) {
                    case ADD_PLAYER: {
                        String name = data.getProfile().getName();
                        WurstplusFour.EVENT_PROCESSOR.addEventListener(new ConnectionEvent(0, id, name));
                        break;
                    }
                    case REMOVE_PLAYER: {
                        EntityPlayer entity = mc.world.getPlayerEntityByUUID(id);
                        if (entity != null) {
                            String logoutName = entity.getName();
                            WurstplusFour.EVENT_PROCESSOR.addEventListener(new ConnectionEvent(1, entity, id, logoutName));
                            break;
                        }
                        WurstplusFour.EVENT_PROCESSOR.addEventListener(new ConnectionEvent(2, id, null));
                    }
                    default:
                        break;
                }
            });
        } else if (event.getPacket() instanceof SPacketTimeUpdate) {
            WurstplusFour.SERVER_MANAGER.update();
        }
    }

    @SubscribeEvent
    public void GuiEvent(GuiScreenEvent.InitGuiEvent.Post event){
        GuiScreen gui = event.getGui();

        if(gui instanceof GuiMainMenu){
            event.getButtonList().add(new GuiAltButton(69, gui.width / 2 + 104, (gui.height /4 + 48)+ 72 + 12, 20, 20, "Alt"));
        }
    }

}
