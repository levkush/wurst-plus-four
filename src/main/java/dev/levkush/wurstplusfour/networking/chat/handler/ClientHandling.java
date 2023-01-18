package dev.levkush.wurstplusfour.networking.chat.handler;

import dev.levkush.wurstplusfour.WurstplusFour;
import dev.levkush.wurstplusfour.networking.chat.packets.client.NewClientPacket;
import dev.levkush.wurstplusfour.networking.chat.packets.ping.PingUpPacket;
import dev.levkush.wurstplusfour.networking.chat.Packet;
import net.minecraft.client.Minecraft;

import java.io.IOException;

/**
 * @author Madmegsox1
 * @since 30/05/2021
 */

public class ClientHandling {
    public String token;
    private final Minecraft mc = Minecraft.getMinecraft();

    public ClientHandling(){
        token = "";
    }

    public void newClient(){
        try {
            Packet packetClient = new NewClientPacket(); // TODO fix null
            String[] data = packetClient.run();
            if(data[0].equals("server") && data[1].equals("newclient")){
               if(data[2].equals("false")){ // loads token
                   this.token = WurstplusFour.CONFIG_MANAGER.loadIRCtoken();
               }
               else if(data[2].equals("true")){ // saves token
                   this.token = data[3];
                   WurstplusFour.CONFIG_MANAGER.saveIRCtoken(this.token);
               }
            }
            if(!token.isEmpty()){
                Packet packetUp = new PingUpPacket();
                data = packetUp.run(token);
                if(data[0].equals("server") && data[1].equals("pingup")){
                    WurstplusFour.LOGGER.info("IRC chat init complete");
                }else {
                    WurstplusFour.LOGGER.error("IRC chat not didnt init");
                }
            }
        }catch (IOException e){
            WurstplusFour.LOGGER.error("Exception in loading new client for IRC " + e);
        }
    }
}
