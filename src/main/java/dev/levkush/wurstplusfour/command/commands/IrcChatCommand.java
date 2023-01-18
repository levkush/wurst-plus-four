package dev.levkush.wurstplusfour.command.commands;

import dev.levkush.wurstplusfour.WurstplusFour;
import dev.levkush.wurstplusfour.command.Command;
import dev.levkush.wurstplusfour.util.ClientMessage;

/**
 * @author Madmegsox1
 * @since 30/05/2021
 */

public class IrcChatCommand extends Command {

    public IrcChatCommand(){
        super("irc");
    }
    @Override
    public void execute(String[] message) {
        if(message[0].equals("set")){
            switch (message[1]) {
                case "global":
                    WurstplusFour.CLIENT_HANDLING.newClient();
                    WurstplusFour.CHAT_HANDLING.setRunning(true);
                    WurstplusFour.CHAT_HANDLING.setToGlobal();
                    WurstplusFour.CHAT_HANDLING.start();
                    ClientMessage.sendIRCMessage("");
                    break;
                case "direct":
                case "dm":
                    if (message.length > 2) {
                        WurstplusFour.CLIENT_HANDLING.newClient();
                        WurstplusFour.CHAT_HANDLING.setRunning(true);
                        WurstplusFour.CHAT_HANDLING.setToDirect(message[2]);
                        WurstplusFour.CHAT_HANDLING.start();
                        ClientMessage.sendIRCMessage("");
                    }
                    break;
                case "server":
                    WurstplusFour.CHAT_HANDLING.setRunning(false);
                    WurstplusFour.CHAT_HANDLING.setToServer();
                    ClientMessage.sendIRCMessage("");
                    break;
            }
        }
    }
}
