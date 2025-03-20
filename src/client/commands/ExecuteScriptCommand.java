package client.commands;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.temporal.Temporal;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

import client.ClientUdpNetwork;
import client.NetTerminal;

public class ExecuteScriptCommand implements Command {
    private NetTerminal terminal;
    private static Set<String> executedScripts = new HashSet<>();

    public ExecuteScriptCommand(NetTerminal terminal){
        this.terminal = terminal;
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
            Scanner lastScanner = terminal.getScanner();
            Scanner scriptScanner = new Scanner(file);

            terminal.setScanner(scriptScanner);
            terminal.swapOutput();
            terminal.start(scriptScanner.hasNextLine());
            terminal.swapOutput();
            terminal.setScanner(lastScanner);

        } catch (Exception e) {
            System.out.println("Произошла ошибка: " + e.getMessage());
        } finally {
            executedScripts.remove(arg);
        }
        return null;
        
        
    }

    
}
