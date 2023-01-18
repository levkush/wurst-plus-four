package dev.levkush.wurstplusfour.gui.component.components;

import dev.levkush.wurstplusfour.WurstplusFour;
import dev.levkush.wurstplusfour.gui.WurstplusGuiNew;
import dev.levkush.wurstplusfour.hack.Hack;
import dev.levkush.wurstplusfour.hack.hacks.client.Gui;
import dev.levkush.wurstplusfour.util.RenderUtil2D;
import dev.levkush.wurstplusfour.gui.component.Component;
import dev.levkush.wurstplusfour.gui.component.HackButton;

/**
 * @author BrownZombie
 * @since 29/04/2021
 */

public class ShownComponent extends Component {
    private String name;
    private boolean isHovered;
    private HackButton parent;
    private int offset;
    private int x;
    private int y;

    private Hack module;

    public ShownComponent(HackButton button, int offset) {
        this.parent = button;
        this.name = "Shown";
        this.offset = offset;

        this.x = button.parent.getX() + button.parent.getWidth();
        this.y = button.parent.getY() + button.offset;
        setShown(true);
    }

    @Override
    public void renderComponent(int mouseX, int mouseY) {
        module = this.parent.mod;
        RenderUtil2D.drawRectMC(parent.parent.getX() + WurstplusGuiNew.SETTING_OFFSET, parent.parent.getY() + offset + WurstplusGuiNew.MODULE_OFFSET, parent.parent.getX() + parent.parent.getWidth() - WurstplusGuiNew.SETTING_OFFSET, parent.parent.getY() + offset + WurstplusGuiNew.HEIGHT + WurstplusGuiNew.MODULE_OFFSET, this.isHovered ? WurstplusGuiNew.GUI_HOVERED_COLOR() : WurstplusGuiNew.GUI_COLOR());
        if (Gui.INSTANCE.customFont.getValue()) {
            WurstplusFour.GUI_FONT_MANAGER.drawStringWithShadow("Shown: " + (this.module.isNotification() ? "True" : "False"), parent.parent.getX() + WurstplusGuiNew.SUB_FONT_SIZE, parent.parent.getY() + offset + 3 + WurstplusGuiNew.MODULE_OFFSET, Gui.INSTANCE.fontColor.getValue().hashCode());
        } else {
            mc.fontRenderer.drawStringWithShadow("Shown: " + (this.module.isNotification() ? "True" : "False"), parent.parent.getX() + WurstplusGuiNew.SUB_FONT_SIZE, parent.parent.getY() + offset + 3 + WurstplusGuiNew.MODULE_OFFSET, Gui.INSTANCE.fontColor.getValue().hashCode());
        }
    }

    @Override
    public void setOff(int newOff) {
        offset = newOff;
    }

    @Override
    public void updateComponent(int mouseX, int mouseY) {
        this.isHovered = isMouseOnButton(mouseX, mouseY);
        this.y = parent.parent.getY() + offset;
        this.x = parent.parent.getX();
    }

    public boolean isMouseOnButton(int x, int y) {
        return x > this.parent.parent.getX() + WurstplusGuiNew.SETTING_OFFSET && x < this.parent.parent.getX() + WurstplusGuiNew.WIDTH - WurstplusGuiNew.SETTING_OFFSET && y > this.parent.parent.getY() + offset + WurstplusGuiNew.MODULE_OFFSET && y < this.parent.parent.getY() + offset + WurstplusGuiNew.HEIGHT + WurstplusGuiNew.MODULE_OFFSET;
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int button) {
        if (isMouseOnButton(mouseX, mouseY) && button == 0 && this.parent.isOpen) {
            this.module.setNotification(!this.module.isNotification());
        }
    }

    @Override
    public HackButton getParent() {
        return parent;
    }

    @Override
    public int getOffset() {
        return offset;
    }
}
