package client;


import java.sql.Time;
import java.util.Scanner;
import java.util.concurrent.TimeoutException;

import client.commands.AddCommand;
import client.commands.AddIfMinCommand;
import client.commands.ExecuteScriptCommand;
import client.commands.ExitCommand;
import client.commands.RemoveGreaterCommand;
import client.commands.UpdateCommand;
import client.managers.CommandManager;
import managers.exceptions.ParseCommandException;
import network.Settings;
import network.UdpNetwork;
import network.exceptions.TimeOutException;
import network.models.Answer;
import network.models.Command;

public class ClientApp extends UdpNetwork {
    private Scanner scanner;
    private CommandManager commandManager;

    public ClientApp(Settings settings){
        scanner = new Scanner(System.in);
        commandManager = new CommandManager();
        try {
            inetSocketAddress = getSocketAddress(settings);
            datagramChannel = createDatagramChannel();
        } catch (Exception e) { //TODO handle all exceptions
            throw new RuntimeException(e);
        }
        
    }
    public Scanner getScanner(){
        return scanner;
    }
    public void setScanner(Scanner scanner){
        this.scanner = scanner;
    }
    public CommandManager getCommandManager(){
        return commandManager;
    }

    public static void main(String[] args) {
        Settings settings = new ClientSettings();
        ClientApp client = new ClientApp(settings);
        

        //CommandManager commandManager = new CommandManager();
        
        client.start(true, true);
        
    }
    public void start(boolean condition, boolean showPrints){
        //TODO вынести в command manager
        getCommandManager().registerCommand("add", new AddCommand(getScanner(), showPrints));
        getCommandManager().registerCommand("update", new UpdateCommand(getScanner(), showPrints));
        getCommandManager().registerCommand("add_if_min", new AddIfMinCommand(getScanner(), showPrints));
        getCommandManager().registerCommand("remove_greater", new RemoveGreaterCommand(getScanner(), showPrints));
        getCommandManager().registerCommand("execute_script", new ExecuteScriptCommand(this));
        getCommandManager().registerCommand("exit", new ExitCommand());

        
        while (condition) {
            if (showPrints == true) { System.out.print("> "); }

            String in;
            try {
                in = getScanner().nextLine().trim();
            } catch (Exception e) {
                break;
            }
            
            try {
                String[] parsedCommand = CommandManager.parseCommand(in);
                String command = parsedCommand[0];
                Object commandArgs = parsedCommand[1];
                if (commandManager.listedNames().contains(command) == true){
                    Object answer = commandManager.executeCommand(command, (String) commandArgs);
                    if (answer != null){
                        commandArgs = answer;
                    }
                }
                sendObject(new Command(command, commandArgs));
                Answer serverAnswer = handleAnswer(1000);

                if (showPrints == true) { System.out.println(serverAnswer.answer()); }
                
                
            } catch (TimeOutException | ParseCommandException e) {
                System.out.println(e.getMessage());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }
}





    


