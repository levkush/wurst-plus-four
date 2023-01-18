package dev.levkush.wurstplusfour.networking.chat.packets.message;

import dev.levkush.wurstplusfour.networking.chat.Packet;
import dev.levkush.wurstplusfour.networking.chat.Sockets;

import java.io.IOException;
import java.net.Socket;

/**
 * @author Madmegsox1
 * @since 20/05/2021
 */
public class PostToGlobalPacket extends Packet {

    public String[] run(String key, String... arguments) throws IOException {
        String client = mc.player.getName() + ":" + mc.player.getUniqueID();
        Socket s = Sockets.createConnection();
        Sockets.sendData(s, "client:postglobal:"+client+":"+key+":"+arguments[0]);
        String[] data = Sockets.getData(s);
        s.close();
        return data;
    }

}
