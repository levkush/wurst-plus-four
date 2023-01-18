package dev.levkush.wurstplusfour.hack.hacks.chat;

import com.mojang.realmsclient.gui.ChatFormatting;
import dev.levkush.wurstplusfour.event.events.PacketEvent;
import dev.levkush.wurstplusfour.event.processor.CommitEvent;
import dev.levkush.wurstplusfour.event.processor.EventPriority;
import dev.levkush.wurstplusfour.setting.type.BooleanSetting;
import dev.levkush.wurstplusfour.setting.type.DoubleSetting;
import dev.levkush.wurstplusfour.setting.type.EnumSetting;
import dev.levkush.wurstplusfour.gui.chat.GuiChat;
import dev.levkush.wurstplusfour.hack.Hack;
import dev.levkush.wurstplusfour.hack.HackPriority;
import net.minecraft.client.gui.GuiIngame;
import net.minecraft.client.gui.GuiNewChat;
import net.minecraft.network.play.client.CPacketChatMessage;
import net.minecraft.network.play.server.SPacketChat;
import net.minecraft.util.text.ChatType;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

@Hack.Registration(name = "Custom Chat", description = "lets you customise chat", category = Hack.Category.CHAT, priority = HackPriority.Low)
public class CustomChat extends Hack {

    public static CustomChat INSTANCE;

    public CustomChat() {
        INSTANCE = this;
    }

    public BooleanSetting customFont = new BooleanSetting("Custom Font", true, this);
    public BooleanSetting rainbow = new BooleanSetting("Rainbow", false, this);
    public BooleanSetting nameHighlight = new BooleanSetting("Name Highlight", true, this);
    public BooleanSetting timeStamps = new BooleanSetting("Time Stamps", true, this);
    public BooleanSetting colourTimeStamps = new BooleanSetting("Rainbow Time", true, this, s -> timeStamps.getValue());
    public BooleanSetting suffix = new BooleanSetting("Suffix", false, this);
    public BooleanSetting infinite = new BooleanSetting("Infinite", true, this);
    public BooleanSetting smoothChat = new BooleanSetting("SmoothChat", false, this);
    public DoubleSetting xOffset = new DoubleSetting("XOffset", 0.0, 0.0, 600.0,this);
    public DoubleSetting yOffset = new DoubleSetting("YOffset", 0.0, 0.0, 30.0, this);
    public DoubleSetting vSpeed = new DoubleSetting("VSpeed", 30.0, 1.0, 100.0, this);
    public DoubleSetting vLength = new DoubleSetting("VLength",10.0, 5.0, 100.0, this);
    public DoubleSetting vIncrements = new DoubleSetting("VIncrements", 1.0, 1.0, 5.0, this);
    public EnumSetting type = new EnumSetting("Type", "Horizontal",Arrays.asList("Horizontal", "Vertical"), this);
    public BooleanSetting help = new BooleanSetting("HelpMessages", true, this);

    public static GuiChat guiChatSmooth;
    public static GuiNewChat guiChat;

    @Override
    public void onEnable() {
        guiChatSmooth = new GuiChat(mc);
        ObfuscationReflectionHelper.setPrivateValue(GuiIngame.class, (mc).ingameGUI, guiChatSmooth, "field_73840_e");
    }

    @Override
    public void onDisable() {
        guiChat = new GuiNewChat(mc);
        ObfuscationReflectionHelper.setPrivateValue(GuiIngame.class, (mc).ingameGUI, guiChat, "field_73840_e");
    }

    @CommitEvent(priority = EventPriority.LOW)
    public void onPacketSend(PacketEvent.Send event) {
        if (event.getStage() == 0 && event.getPacket() instanceof CPacketChatMessage) {
            CPacketChatMessage packet = event.getPacket();
            String s = packet.getMessage();
            if (s.startsWith("/")) {
                return;
            }
            if (this.suffix.getValue()) {
                s += " \u25e6 \u0461\u028B\u0433\u0455\u0074\u0440\u0406\u028B\u0455 \u01B7";
            }
            if (s.length() >= 256) {
                s = s.substring(0, 256);
            }
            packet.message = s;
        }
    }

    @CommitEvent(priority = EventPriority.LOW)
    public void onPacketReceive(PacketEvent.Receive event) {
        if (event.getPacket() instanceof SPacketChat) {
            if (((SPacketChat) event.getPacket()).getType() == ChatType.GAME_INFO) {
                return;
            }
            String originalMessage = ((SPacketChat) event.getPacket()).chatComponent.getFormattedText();
            String message = this.getTimeString(originalMessage);
            if (nameHighlight.getValue()) {
                try {
                    message = message.replace(mc.player.getName(), ChatFormatting.GOLD + mc.player.getName() + ChatFormatting.RESET);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            ((SPacketChat) event.getPacket()).chatComponent = new TextComponentString(message);
        }
    }

    private String getTimeString(String message) {
        if (timeStamps.getValue()) {
            String date = new SimpleDateFormat("k:mm").format(new Date());
            return  "[" + date + "] " + (colourTimeStamps.getValue() ? ChatFormatting.RESET + message : message);
        }
        return message;
    }

    // ching chong
    @Override
    public void onLogin() {
        if (this.isEnabled()) {
            this.disable();
            this.enable();
        }
    }
}
