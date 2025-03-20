package client.commands;

import java.util.Scanner;

import client.commands.utility.ArgHandler;
import client.commands.utility.ConsoleInputHandler;
import collection.Color;
import collection.Coordinates;
import collection.Dragon;
import collection.DragonCharacter;
import collection.DragonHead;
import collection.DragonType;
import network.models.UpdateCommandArgs;


public class UpdateCommand implements Command {
    private ConsoleInputHandler consoleInputHandler;

    public UpdateCommand(ConsoleInputHandler consoleInputHandler){
        this.consoleInputHandler = consoleInputHandler;
    }

    @Override
    public boolean isHasArgs(){
        return true;
    }

    @Override
    public Object execute(String arg){
        try {
            if (ArgHandler.checkArgForInt(arg)){
                int id = Integer.parseInt(arg);                    
                System.out.println(String.format("Начинаем изменение дракона с ID-%d", id));
    
                String name = consoleInputHandler.promptForString("Введите имя дракона:", false);
                long x = consoleInputHandler.promptForLong("Введите координату x:", false, -420, Long.MAX_VALUE);
                long y = consoleInputHandler.promptForLong("Введите координату y:", false, Long.MIN_VALUE, 699);
                Long age = consoleInputHandler.promptForLong("Введите возраст дракона:", false, 0, Long.MAX_VALUE);
                Color color = consoleInputHandler.promptForEnum("Введите цвет дракона: %s", Color.values(), false);
                DragonType type = consoleInputHandler.promptForEnum("Введите тип дракона: %s", DragonType.values(), false);
                DragonCharacter character = consoleInputHandler.promptForEnum("Введите характер дракона: %s", DragonCharacter.values(), false);
                Float eyesCount = consoleInputHandler.promptForFloat("Введите кол-во глаз у дракона:", true, -Float.MAX_VALUE, Float.MAX_VALUE);
    
                System.out.println(String.format("Дракон с ID-%d успешно обновлён!", id));

                return new UpdateCommandArgs(id, name, x, y, age, color, type, character, eyesCount);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
        
    }
    
}
