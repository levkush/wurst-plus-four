package dev.levkush.wurstplusfour.util.elements;

import dev.levkush.wurstplusfour.util.Globals;
import dev.levkush.wurstplusfour.util.PlayerUtil;

import java.util.UUID;

public class WurstplusPlayer implements Globals {

    private final String name;
    private String nickName;

    public WurstplusPlayer(String name) {
        this.name = name;
        PlayerUtil.getUUIDFromName(name);
    }

    public WurstplusPlayer(String name, UUID uuid) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public String getNickName() {
        return this.nickName;
    }

    public void setNickName(String name) {
        this.nickName = name;
    }

}
