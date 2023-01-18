package dev.levkush.wurstplusfour.gui.alt;

import dev.levkush.wurstplusfour.WurstplusFour;
import dev.levkush.wurstplusfour.util.RenderUtil2D;
import dev.levkush.wurstplusfour.util.elements.Alt;

import java.awt.*;

/**
 * @author Madmegsox1
 * @since 02/05/2021
 */

public class AltComponent {
    Alt alt;
    int offset;
    MainAltGui parent;

    public AltComponent(Alt alt, int offset, MainAltGui parent) {
        this.alt = alt;
        this.offset = offset;
        this.parent = parent;
    }

    public void render() {
        RenderUtil2D.drawRectMC(MainAltGui.x, MainAltGui.y + offset, MainAltGui.x + MainAltGui.width, MainAltGui.y + offset + MainAltGui.height, MainAltGui.GUI_TRANSPARENCY);
        WurstplusFour.GUI_FONT_MANAGER.drawStringWithShadow(alt.getUsername(), MainAltGui.x + 20, MainAltGui.y + offset + 10, new Color(255, 255, 255).hashCode());
    }

}
