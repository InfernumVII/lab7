package server.local;

import managers.CommandManager;
import server.local.commands.Command;
import server.local.commands.ExitCommand;
import server.local.commands.SaveCommand;
import server.managers.DragonManager;

public class ServerCommandManager extends CommandManager<Command> {
    public ServerCommandManager(DragonManager dragonManager){
        registerCommand("exit", new ExitCommand());
        registerCommand("save", new SaveCommand(dragonManager));
    }

    @Override
    public Object executeCommand(String name, Object arg) {
        Command command = commands.get(name);
        if (command == null){
            System.out.println("Неизвестная команда: " + name);
        } else {
            command.execute();
        }
        return null;    
    }   
}
