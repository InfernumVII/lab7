package server.local;

import managers.CommandManager;
import server.local.commands.Command;
import server.managers.DragonManager;
import temp.TerminalWithCommandManager;

public class ServerTerminal extends TerminalWithCommandManager<ServerCommandManager> {
    public ServerTerminal(DragonManager dragonManager) {
        super(new ServerCommandManager(dragonManager));
    }    
}
