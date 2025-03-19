package client.commands;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

import client.ClientApp;
import client.commands.utility.ConsoleInputHandler;
import managers.CommandManager;

/**
 * Команда для выполнения скрипта из указанного файла.
 * Реализует интерфейс {@link Command}.
 */
public class ExecuteScriptCommand implements Command {
    private ClientApp clientApp;
    private static Set<String> executedScripts = new HashSet<>();

    public ExecuteScriptCommand(ClientApp clientApp){
        this.clientApp = clientApp;
    }


    @Override
    public boolean isHasArgs(){
        return true;
    }


    @Override
    public Object execute(String arg){
        if (executedScripts.contains(arg)) {
            System.out.println("Ошибка: рекурсия обнаружена. Скрипт " + arg + " уже выполняется.");
            return null;
        }
            
        executedScripts.add(arg);

        System.out.println(String.format("Запуск команд из файла: %s", arg));
        try (FileInputStream file = new FileInputStream(arg)) {
            Scanner lastScanner = clientApp.getScanner();
            Scanner scriptScanner = new Scanner(file);
            clientApp.setScanner(scriptScanner);
            clientApp.start(scriptScanner.hasNextLine(), false);
            clientApp.setScanner(lastScanner);
        } catch (IOException e) {
            System.out.println("Произошла ошибка: " + e.getMessage());
        } finally {
            executedScripts.remove(arg);
        }
        return null;
        
        
    }

    
}
