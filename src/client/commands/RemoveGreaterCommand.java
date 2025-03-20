package client.commands;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import client.commands.utility.ConsoleInputHandler;
import collection.Dragon;
import commandRecords.RemoveGreaterCommandArgs;
import managers.CommandManager;
import managers.DragonManager;



public class RemoveGreaterCommand implements Command {

    private Scanner scanner;
    private boolean showOutput;


    public RemoveGreaterCommand(Scanner scanner, boolean showOutput){
        this.scanner = scanner;
        this.showOutput = showOutput;
    }


    @Override
    public boolean isHasArgs(){
        return false;
    }

    @Override
    public Object execute(String arg){
        System.out.println("Введите координаты элемента: ");
        ConsoleInputHandler consoleInputHandler = new ConsoleInputHandler(scanner, showOutput);
        long x = consoleInputHandler.promptForLong("Введите координату x:", false, -420, Long.MAX_VALUE);
        long y = consoleInputHandler.promptForLong("Введите координату y:", false, Long.MIN_VALUE, 699);
        
        return new RemoveGreaterCommandArgs(x, y);
        
    }

}
