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
import commandRecords.UpdateCommandArgs;
import managers.CommandManager;
import managers.DragonManager;


/**
 * Команда для обновления значения элемента коллекции по его ID.
 * Реализует интерфейс {@link Command}.
 */
public class UpdateCommand implements Command {
    private Scanner scanner;
    private boolean showOutput;


    public UpdateCommand(Scanner scanner, boolean showOutput){
        this.scanner = scanner;
        this.showOutput = showOutput;
    }

    /**
     * Проверяет, имеет ли команда аргументы.
     *
     * @return возвращает {@code true}, так как команда требует ID дракона в качестве аргумента.
     */
    @Override
    public boolean isHasArgs(){
        return true;
    }

    /**
     * Выполняет команду обновления дракона по его ID.
     *
     * @param arg строка, содержащая ID дракона.
     */
    @Override
    public Object execute(String arg){
        try {
            if (ArgHandler.checkArgForInt(arg)){
                int id = Integer.parseInt(arg);
                ConsoleInputHandler consoleInputHandler = new ConsoleInputHandler(scanner, showOutput);
                    
                System.out.println(String.format("Начинаем изменение дракона с ID-%d", id));
    
                String name = consoleInputHandler.promptForString("Введите имя дракона:", false);
                //dragon.setName(name);
                long x = consoleInputHandler.promptForLong("Введите координату x:", false, -420, Long.MAX_VALUE);
                long y = consoleInputHandler.promptForLong("Введите координату y:", false, Long.MIN_VALUE, 699);
                //dragon.setCoordinates(new Coordinates(x, y));
                Long age = consoleInputHandler.promptForLong("Введите возраст дракона:", false, 0, Long.MAX_VALUE);
                //dragon.setAge(age);
                Color color = consoleInputHandler.promptForEnum("Введите цвет дракона: %s", Color.values(), false);
                //dragon.setColor(color);
                DragonType type = consoleInputHandler.promptForEnum("Введите тип дракона: %s", DragonType.values(), false);
                //dragon.setType(type);
                DragonCharacter character = consoleInputHandler.promptForEnum("Введите характер дракона: %s", DragonCharacter.values(), false);
                //dragon.setCharacter(character);
                Float eyesCount = consoleInputHandler.promptForFloat("Введите кол-во глаз у дракона:", true, -Float.MAX_VALUE, Float.MAX_VALUE);
                //dragon.setHead(new DragonHead(eyesCount));
    
                System.out.println(String.format("Дракон с ID-%d успешно обновлён!", id));

                return new UpdateCommandArgs(id, name, x, y, age, color, type, character, eyesCount);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
        
    }
    
}
