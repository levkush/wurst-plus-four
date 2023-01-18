package dev.levkush.wurstplusfour.gui;

import dev.levkush.wurstplusfour.gui.alt.defult.GuiAccountSelector;
import dev.levkush.wurstplusfour.WurstplusFour;
import dev.levkush.wurstplusfour.util.RenderUtil;
import dev.levkush.wurstplusfour.util.elements.DonatorItem;
import dev.levkush.wurstplusfour.util.elements.Rainbow;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.*;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class CustomSplashScreen extends GuiScreen {

    private final ResourceLocation background = new ResourceLocation("textures/pitbull1.jpg");
    private final List<DonatorItem> donatorItems = new ArrayList<>();
    private int y;
    private int x;
    private float watermarkX;

    public static void drawCompleteImage(float posX, float posY, float width, float height) {
        GL11.glPushMatrix();
        GL11.glTranslatef(posX, posY, 0.0f);
        GL11.glBegin(7);
        GL11.glTexCoord2f(0.0f, 0.0f);
        GL11.glVertex3f(0.0f, 0.0f, 0.0f);
        GL11.glTexCoord2f(0.0f, 1.0f);
        GL11.glVertex3f(0.0f, height, 0.0f);
        GL11.glTexCoord2f(1.0f, 1.0f);
        GL11.glVertex3f(width, height, 0.0f);
        GL11.glTexCoord2f(1.0f, 0.0f);
        GL11.glVertex3f(width, 0.0f, 0.0f);
        GL11.glEnd();
        GL11.glPopMatrix();
    }

    public static boolean isHovered(int x, int y, int width, int height, int mouseX, int mouseY) {
        return mouseX >= x && mouseX <= x + width && mouseY >= y && mouseY < y + height;
    }

    private void playMusic() {
        if (!mc.soundHandler.isSoundPlaying(WurstplusFour.SONG_MANAGER.getMenuSong())) {
            mc.soundHandler.playSound(WurstplusFour.SONG_MANAGER.getMenuSong());
        }
    }

    private void initDonators() {
        this.donatorItems.clear();
        try {
            URL capesList = new URL("https://raw.githubusercontent.com/TrvsF/capes/main/boosts.txt");
            BufferedReader in = new BufferedReader(new InputStreamReader(capesList.openStream()));
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                String colune = inputLine.trim();
                String name = colune.split(":")[0];
                String ammount = colune.split(":")[1];
                donatorItems.add(new DonatorItem(name, Integer.parseInt(ammount), WurstplusFour.GUI_FONT_MANAGER.getTextWidth(name),
                        WurstplusFour.GUI_FONT_MANAGER.getTextHeight(), this.width, this.height));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void initGui() {
        mc.gameSettings.enableVsync = false;
        mc.gameSettings.limitFramerate = 200;
        this.initDonators();
        this.playMusic();
        this.x = this.width / 4;
        this.y = this.height / 4 + 48;
        this.watermarkX = this.width + 80;
        this.buttonList.add(new TextButton(0, this.x, this.y + 22, "singleplayer"));
        this.buttonList.add(new TextButton(1, this.x, this.y + 44, "the_fellas"));
        this.buttonList.add(new TextButton(2, this.x, this.y + 66, "settings"));
        this.buttonList.add(new TextButton(2, this.x, this.y + 88, "discord"));
        this.buttonList.add(new TextButton(2, this.x, this.y + 110, "alts"));
        this.buttonList.add(new TextButton(2, this.x, this.y + 132, "log"));
        GlStateManager.disableTexture2D();
        GlStateManager.enableBlend();
        GlStateManager.disableAlpha();
        GlStateManager.shadeModel(7425);
        GlStateManager.shadeModel(7424);
        GlStateManager.disableBlend();
        GlStateManager.enableAlpha();
        GlStateManager.enableTexture2D();
    }

    public void updateScreen() {
        super.updateScreen();
    }

    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        if (CustomSplashScreen.isHovered(this.x, this.y + 20, WurstplusFour.MENU_FONT_MANAGER.getTextWidth("singleplayer"), WurstplusFour.MENU_FONT_MANAGER.getTextHeight(), mouseX, mouseY)) {
            this.mc.displayGuiScreen(new GuiWorldSelection(this));
        } else if (CustomSplashScreen.isHovered(this.x, this.y + 44, WurstplusFour.MENU_FONT_MANAGER.getTextWidth("the_fellas"), WurstplusFour.MENU_FONT_MANAGER.getTextHeight(), mouseX, mouseY)) {
            this.mc.displayGuiScreen(new GuiMultiplayer(this));
        } else if (CustomSplashScreen.isHovered(this.x, this.y + 66, WurstplusFour.MENU_FONT_MANAGER.getTextWidth("settings"), WurstplusFour.MENU_FONT_MANAGER.getTextHeight(), mouseX, mouseY)) {
            this.mc.displayGuiScreen(new GuiOptions(this, this.mc.gameSettings));
        } else if (CustomSplashScreen.isHovered(this.x, this.y + 88, WurstplusFour.MENU_FONT_MANAGER.getTextWidth("discord"), WurstplusFour.MENU_FONT_MANAGER.getTextHeight(), mouseX, mouseY)) {
            try {
                if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
                    Desktop.getDesktop().browse(new URI("https://discord.gg/hvnZePKQHx"));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (CustomSplashScreen.isHovered(this.x, this.y + 110, WurstplusFour.MENU_FONT_MANAGER.getTextWidth("alts"), WurstplusFour.MENU_FONT_MANAGER.getTextHeight(), mouseX, mouseY)) {
            this.mc.displayGuiScreen(new GuiAccountSelector());
        }
        else if (CustomSplashScreen.isHovered(this.x, this.y + 132, WurstplusFour.MENU_FONT_MANAGER.getTextWidth("log"), WurstplusFour.MENU_FONT_MANAGER.getTextHeight(), mouseX, mouseY)) {
            this.mc.shutdown();
        }
    }

    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        float xOffset = -1.0f * (((float) mouseX - (float) this.width / 2.0f) / ((float) this.width / 32.0f));
        float yOffset = -1.0f * (((float) mouseY - (float) this.height / 2.0f) / ((float) this.height / 18.0f));
        this.x = this.width / 4;
        this.y = this.height / 4 + 48;
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
        this.mc.getTextureManager().bindTexture(this.background);
        CustomSplashScreen.drawCompleteImage(-16.0f + xOffset, -9.0f + yOffset, this.width + 32, this.height + 18);
        String watermark = WurstplusFour.MODNAME + " v" + WurstplusFour.INSTANCE.MODVER+ " : made by travis#0001 | Madmeg#4882 - with help from BrownZombie, k3b, wallhacks#6969, and Austin :D";
        for (DonatorItem item : this.donatorItems) {
            item.updatePos();
            switch (item.getSize()) {
                case 1:
                    WurstplusFour.DONATOR_FONT_MANAGER.drawSmallStringRainbow(item.getName(), (float) item.getX(), (float) item.getY(), item.getRgb());
                    break;
                case 2:
                    WurstplusFour.DONATOR_FONT_MANAGER.drawMediumStringRainbow(item.getName(), (float) item.getX(), (float) item.getY(), item.getRgb());
                    break;
                case 3:
                    WurstplusFour.DONATOR_FONT_MANAGER.drawLargeStringRainbow(item.getName(), (float) item.getX(), (float) item.getY(), item.getRgb());
                    break;
            }

        }
        WurstplusFour.GUI_FONT_MANAGER.drawStringRainbow(watermark, watermarkX, this.height - WurstplusFour.GUI_FONT_MANAGER.getTextHeight() - 2, true);
        watermarkX -= .05f;
        if (watermarkX < -WurstplusFour.GUI_FONT_MANAGER.getTextWidth(watermark) - 10) {
            this.watermarkX = this.width + 40;
        }
        WurstplusFour.MENU_FONT_MANAGER.drawStringBig("WurstPlus 3", (float) this.x, (float) this.y - 20, Color.white.getRGB(), true);
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    private static class TextButton extends GuiButton {

        public TextButton(int buttonId, int x, int y, String buttonText) {
            super(buttonId, x, y, WurstplusFour.MENU_FONT_MANAGER.getTextWidth(buttonText), WurstplusFour.MENU_FONT_MANAGER.getTextHeight(), buttonText);
        }

        public void drawButton(Minecraft mc, int mouseX, int mouseY, float partialTicks) {
            if (this.visible) {
                this.enabled = true;
                this.hovered = (float) mouseX >= (float) this.x && mouseY >= this.y && mouseX < this.x + this.width && mouseY < this.y + this.height;
                WurstplusFour.MENU_FONT_MANAGER.drawStringWithShadow(this.displayString, (float) this.x + 1f, this.y, Color.WHITE.getRGB());
                if (this.hovered) {
                    RenderUtil.drawLine(this.x - 5f, this.y + 2 + WurstplusFour.MENU_FONT_MANAGER.getTextHeight(), this.x - 5f, this.y - 2, 2f, Rainbow.getColour().getRGB());
                }
            }
        }

        public boolean mousePressed(Minecraft mc, int mouseX, int mouseY) {
            return this.enabled && this.visible && (float) mouseX >= (float) this.x - (float) WurstplusFour.MENU_FONT_MANAGER.getTextWidth(this.displayString) / 2.0f && mouseY >= this.y && mouseX < this.x + this.width && mouseY < this.y + this.height;
        }
    }

}
