package client.commands;

import java.time.LocalDate;
import java.util.Scanner;

import client.commands.utility.ConsoleInputHandler;
import collection.Color;
import collection.Coordinates;
import collection.Dragon;
import collection.Dragon.Builder;
import collection.DragonCharacter;
import collection.DragonHead;
import collection.DragonType;

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
    public Object execute(String arg) {
        Builder dragonBuilder = new Dragon.Builder()
                    .withName(consoleInputHandler.promptForString("Введите имя дракона:", false))
                    .withCoordinates(new Coordinates(consoleInputHandler.promptForLong("Введите координату x:", false, -420, Long.MAX_VALUE),
                                                        consoleInputHandler.promptForLong("Введите координату y:", false, Long.MIN_VALUE, 699)))
                    .withAge(consoleInputHandler.promptForLong("Введите возраст дракона:", false, 0, Long.MAX_VALUE))
                    .withColor(consoleInputHandler.promptForEnum("Введите цвет дракона: %s", Color.values(), false))
                    .withType(consoleInputHandler.promptForEnum("Введите тип дракона: %s", DragonType.values(), false))
                    .withCharacter(consoleInputHandler.promptForEnum("Введите характер дракона: %s", DragonCharacter.values(), false))
                    .withHead(new DragonHead(consoleInputHandler.promptForFloat("Введите кол-во глаз у дракона:", true, -Float.MAX_VALUE, Float.MAX_VALUE)));
        return dragonBuilder;
    }

   
}
