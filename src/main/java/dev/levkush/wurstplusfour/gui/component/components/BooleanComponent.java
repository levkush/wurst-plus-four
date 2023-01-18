package dev.levkush.wurstplusfour.gui.component.components;

import dev.levkush.wurstplusfour.WurstplusFour;
import dev.levkush.wurstplusfour.gui.WurstplusGuiNew;
import dev.levkush.wurstplusfour.hack.hacks.client.Gui;
import dev.levkush.wurstplusfour.setting.type.BooleanSetting;
import dev.levkush.wurstplusfour.util.RenderUtil2D;
import dev.levkush.wurstplusfour.gui.component.Component;
import dev.levkush.wurstplusfour.gui.component.HackButton;

import java.awt.*;

/**
 * @author Madmegsox1
 * @since 29/04/2021
 */

public class BooleanComponent extends Component {
    private boolean hovered;
    private BooleanSetting option;
    private final HackButton parent;
    private int offset;
    private int x;
    private int y;

    public BooleanComponent(BooleanSetting option, HackButton button, int offset) {
        this.option = option;
        this.parent = button;
        this.offset = offset;
        this.x = button.parent.getX() + button.parent.getWidth();
        this.y = button.parent.getY() + button.offset;
        setShown(true);
    }


    @Override
    public void renderComponent(int mouseX, int mouseY) {
        if(!isShown())return;
        WurstplusGuiNew.drawRect(parent.parent.getX() + WurstplusGuiNew.SETTING_OFFSET, parent.parent.getY() + offset + WurstplusGuiNew.MODULE_OFFSET, parent.parent.getX() + parent.parent.getWidth() - WurstplusGuiNew.SETTING_OFFSET, parent.parent.getY() + offset + WurstplusGuiNew.HEIGHT + WurstplusGuiNew.MODULE_OFFSET, this.hovered ? WurstplusGuiNew.GUI_HOVERED_COLOR() : this.option.isChild() ? WurstplusGuiNew.GUI_CHILDBUTTON() : WurstplusGuiNew.GUI_COLOR());
        RenderUtil2D.drawBorderedRect(parent.parent.getX() + WurstplusGuiNew.SETTING_OFFSET + 85, parent.parent.getY() + offset + 3 + WurstplusGuiNew.MODULE_OFFSET, parent.parent.getX() + 115 - WurstplusGuiNew.SETTING_OFFSET, parent.parent.getY() + offset + WurstplusGuiNew.HEIGHT + WurstplusGuiNew.MODULE_OFFSET - 3, 1, !this.option.getValue() ? WurstplusGuiNew.GUI_COLOR() : Gui.INSTANCE.buttonColor.getValue().hashCode(), new Color(0, 0, 0, 200).hashCode(), this.hovered);
        RenderUtil2D.drawRectMC(parent.parent.getX() + WurstplusGuiNew.SETTING_OFFSET + (this.option.getValue() ? 95 : 88), parent.parent.getY() + offset + 5 + WurstplusGuiNew.MODULE_OFFSET, parent.parent.getX() + (this.option.getValue() ? 112 : 105) - WurstplusGuiNew.SETTING_OFFSET, parent.parent.getY() + offset + WurstplusGuiNew.HEIGHT + WurstplusGuiNew.MODULE_OFFSET - 5, new Color(50, 50, 50, 255).hashCode());
        if (Gui.INSTANCE.customFont.getValue()) {
            WurstplusFour.GUI_FONT_MANAGER.drawStringWithShadow(this.option.getName(), parent.parent.getX() + WurstplusGuiNew.SUB_FONT_SIZE, parent.parent.getY() + offset + 3 + WurstplusGuiNew.MODULE_OFFSET, Gui.INSTANCE.fontColor.getValue().hashCode());
        } else {
            mc.fontRenderer.drawStringWithShadow(this.option.getName(), parent.parent.getX() + WurstplusGuiNew.SUB_FONT_SIZE, parent.parent.getY() + offset + 3 + WurstplusGuiNew.MODULE_OFFSET, Gui.INSTANCE.fontColor.getValue().hashCode());
        }
    }

    @Override
    public void setOff(int newOff) {
        offset = newOff;
    }

    @Override
    public void updateComponent(int mouseX, int mouseY) {
        this.hovered = isMouseOnButton(mouseX, mouseY);
        this.y = parent.parent.getY() + offset;
        this.x = parent.parent.getX();
        boolean old = isShown();
        setShown(this.option.isShown());
        if(old != isShown()){
            this.parent.init(parent.mod, parent.parent, parent.offset, true);
        }
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int button) {
        if(!isShown())return;
        if (isMouseOnButton(mouseX, mouseY) && button == 0 && this.parent.isOpen) {
            this.option.setValue(!option.getValue());
        }
    }

    public boolean isMouseOnButton(int x, int y) {
        return x > this.parent.parent.getX() + WurstplusGuiNew.SETTING_OFFSET && x < this.parent.parent.getX() + WurstplusGuiNew.WIDTH - WurstplusGuiNew.SETTING_OFFSET && y > this.parent.parent.getY() + offset + WurstplusGuiNew.MODULE_OFFSET && y < this.parent.parent.getY() + offset + WurstplusGuiNew.HEIGHT + WurstplusGuiNew.MODULE_OFFSET;
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

