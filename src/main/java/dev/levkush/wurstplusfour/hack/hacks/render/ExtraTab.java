package dev.levkush.wurstplusfour.hack.hacks.render;

import com.mojang.realmsclient.gui.ChatFormatting;
import dev.levkush.wurstplusfour.WurstplusFour;
import dev.levkush.wurstplusfour.setting.type.IntSetting;
import dev.levkush.wurstplusfour.hack.Hack;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.scoreboard.ScorePlayerTeam;

@Hack.Registration(name = "Extra Tab", description = "this one doesnt crash ur game promise", category = Hack.Category.RENDER, isListening = false)
public class ExtraTab extends Hack {

    public static ExtraTab INSTANCE;

    public ExtraTab() {
        INSTANCE = this;
    }

    public IntSetting count = new IntSetting("Count", 250, 0, 1000, this);

    public static String getPlayerName(NetworkPlayerInfo networkPlayerInfoIn) {
        String name = networkPlayerInfoIn.getDisplayName() != null ? networkPlayerInfoIn.getDisplayName().getFormattedText() : ScorePlayerTeam.formatPlayerName(networkPlayerInfoIn.getPlayerTeam(), networkPlayerInfoIn.getGameProfile().getName());
        if (WurstplusFour.FRIEND_MANAGER.isFriend(name)) {
            return ChatFormatting.AQUA + name;
        }
        if (WurstplusFour.ENEMY_MANAGER.isEnemy(name)) {
            return ChatFormatting.RED + name;
        }
        if (name.equalsIgnoreCase("TrvsF")) {
            return ChatFormatting.GOLD + name;
        }
        return name;
    }

}
