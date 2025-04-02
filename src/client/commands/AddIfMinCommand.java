package client.commands;

import java.time.LocalDate;
import java.util.Scanner;

import client.commands.utility.ConsoleInputHandler;
import collection.Color;
import collection.Coordinates;
import collection.DefaultDragon;
import collection.Dragon;
import collection.Dragon.Builder;
import collection.DragonCharacter;
import collection.DragonHead;
import collection.DragonType;
//TODO в AddCommand убрать повторение
public class AddIfMinCommand implements Command {

    private ConsoleInputHandler consoleInputHandler;

    public AddIfMinCommand(ConsoleInputHandler consoleInputHandler){
        this.consoleInputHandler = consoleInputHandler;
    }

    @Override
    public boolean isHasArgs(){
        return false;
    }

    @Override
    public Object execute(Object arg) {
        return new DefaultDragon(consoleInputHandler).get();
    }

   
}
