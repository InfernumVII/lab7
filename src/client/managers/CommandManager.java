package client.managers;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import client.commands.Command;
import servercommands.CommandInterface;


public class CommandManager {


    private Map<String, Command> commands = new HashMap<>();

    public void registerCommand(String name, Command command){
        commands.put(name, command);
    }

    public Set<String> listedNames(){
        return commands.keySet();
    }

    

    public Object executeCommand(String name, String arg){
        Command command = commands.get(name);
        if (command != null) {
            if (command.isHasArgs() == true && arg == null){
                return "У команды должны быть аргументы.";
            }
            else if (command.isHasArgs() == false && arg != null){
                return "У команды не может быть аргументов.";
            } else {
                return command.execute(arg);
            }
        }
        return null;
    }

    public static String[] parseCommand(String command){
        String[] input = command.split(" ");
        String commandName = input[0];
        String commandArg = null;
        if (input.length > 2){
            System.out.println("У команды не может быть больше чем 1 аргумент.");
            return null;
        }
        if (input.length == 2){
            commandArg = input[1];
        } 
        return new String[]{commandName, commandArg};
    }
}
