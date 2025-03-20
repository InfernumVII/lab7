package client.managers;

import java.time.temporal.Temporal;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.function.Predicate;

import client.ClientUdpNetwork;
import client.NetTerminal;
import client.commands.AddCommand;
import client.commands.AddIfMinCommand;
import client.commands.Command;
import client.commands.ExecuteScriptCommand;
import client.commands.ExitCommand;
import client.commands.RemoveGreaterCommand;
import client.commands.UpdateCommand;
import client.commands.utility.ConsoleInputHandler;
import managers.CommandManager;
import network.models.NetCommand;
import server.managers.exceptions.ParseCommandException;


public class ClientCommandManager extends CommandManager<Command> {

    public void initDefaultCommands(NetTerminal terminal){
        ConsoleInputHandler consoleInputHandler = new ConsoleInputHandler(terminal.getScanner(), terminal.getOutputState());
        registerCommand("add", new AddCommand(consoleInputHandler));
        registerCommand("update", new UpdateCommand(consoleInputHandler));
        registerCommand("add_if_min", new AddIfMinCommand(consoleInputHandler));
        registerCommand("remove_greater", new RemoveGreaterCommand(consoleInputHandler));
        registerCommand("execute_script", new ExecuteScriptCommand(terminal));
        registerCommand("exit", new ExitCommand());
    }

    public NetCommand getCommandFromRawInput(String in) throws ParseCommandException{
        String[] parsedCommand = ClientCommandManager.parseCommand(in);
        String command = parsedCommand[0];
        Object commandArgs = parsedCommand[1];
        if (listedNames().contains(command) == true){
            Object answer = executeCommand(command, (String) commandArgs);
            if (answer != null){
                commandArgs = answer;
            }
        }
        return new NetCommand(command, commandArgs);
    }

    @Override
    public Object executeCommand(String name, Object arg){
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

    public static String[] parseCommand(String command) throws ParseCommandException{
        String[] input = command.split(" ");
        String commandName = input[0];
        String commandArg = null;
        if (input.length > 2){
            throw new ParseCommandException();
        }
        if (input.length == 2){
            commandArg = input[1];
        }
        return new String[]{commandName, commandArg};
    }
}
