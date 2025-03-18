package managers;

import java.util.HashMap;
import java.util.Map;

import clientCommands.Command;

public class ClientCommandManager {
    private Map<String, Command> commands = new HashMap<>();

    public void registerCommand(String name, Command command){
        commands.put(name, command);
    }
    public String executeCommand(String name, Object arg){
        Command command = commands.get(name);
        String answer = command.execute(arg);
        return answer;
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
