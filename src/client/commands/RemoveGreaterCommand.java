package client.commands;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import collection.Dragon;
import commandRecords.RemoveGreaterCommandArgs;
import managers.CommandManager;
import managers.DragonManager;
import utility.ConsoleInputHandler;



public class RemoveGreaterCommand implements Command {

    private Scanner scanner;


    public RemoveGreaterCommand(Scanner scanner){
        this.scanner = scanner;
    }

    /**
     * Проверяет, имеет ли команда аргументы.
     *
     * @return возвращает {@code false}, так как команда не принимает аргументов.
     */
    @Override
    public boolean isHasArgs(){
        return false;
    }

    /**
     * Выполняет команду удаления всех драконов, превышающих заданный по координатам.
     *
     * @param arg аргумент команды (в данной команде не используется).
     */
    @Override
    public Object execute(String arg){
        System.out.println("Введите координаты элемента: ");
        ConsoleInputHandler consoleInputHandler = new ConsoleInputHandler(scanner);
        long x = consoleInputHandler.promptForLong("Введите координату x:", false, -420, Long.MAX_VALUE);
        long y = consoleInputHandler.promptForLong("Введите координату y:", false, Long.MIN_VALUE, 699);
        
        return new RemoveGreaterCommandArgs(x, y);
        
    }

}
