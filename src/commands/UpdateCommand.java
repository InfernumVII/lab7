package commands;

import collection.Color;
import collection.Coordinates;
import collection.Dragon;
import collection.DragonCharacter;
import collection.DragonHead;
import collection.DragonType;
import managers.CommandManager;
import managers.DragonManager;
import utility.ArgHandler;
import utility.ConsoleInputHandler;


/**
 * Команда для обновления значения элемента коллекции по его ID.
 * Реализует интерфейс {@link CommandInterface}.
 */
public class UpdateCommand implements CommandInterface {
    private DragonManager dragonManager;
    private CommandManager commandManager;

    /**
     * Конструктор команды UpdateCommand.
     *
     * @param dragonManager объект {@link DragonManager} для управления командами.
     * @param commandManager  объект {@link CommandManager} для управления коллекцией драконов.
     */
    public UpdateCommand(DragonManager dragonManager, CommandManager commandManager){
        this.dragonManager = dragonManager;
        this.commandManager = commandManager;
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
    public void execute(String arg){
        /*
        if (ArgHandler.checkArgForInt(arg)){
            int id = Integer.parseInt(arg);
            Dragon dragon = dragonManager.returnDragonById(id);
            if (dragon != null){
                
                ConsoleInputHandler.printIfInputIsIn(String.format("Начинаем изменение дракона с ID-%d и именем %s", id, dragon.getName()));

                ConsoleInputHandler consoleInputHandler = new ConsoleInputHandler(commandManager);
                String name = consoleInputHandler.promtForString("Введите имя дракона:", false);
                dragon.setName(name);
                long x = consoleInputHandler.promptForLong("Введите координату x:", false, -420, Long.MAX_VALUE);
                long y = consoleInputHandler.promptForLong("Введите координату y:", false, Long.MIN_VALUE, 699);
                dragon.setCoordinates(new Coordinates(x, y));
                Long age = consoleInputHandler.promptForLong("Введите возраст дракона:", false, 0, Long.MAX_VALUE);
                dragon.setAge(age);
                Color color = consoleInputHandler.promptForEnum("Введите цвет дракона: %s", Color.values(), false);
                dragon.setColor(color);
                DragonType type = consoleInputHandler.promptForEnum("Введите тип дракона: %s", DragonType.values(), false);
                dragon.setType(type);
                DragonCharacter character = consoleInputHandler.promptForEnum("Введите характер дракона: %s", DragonCharacter.values(), false);
                dragon.setCharacter(character);
                Float eyesCount = consoleInputHandler.promptForFloat("Введите кол-во глаз у дракона:", true, -Float.MAX_VALUE, Float.MAX_VALUE);
                dragon.setHead(new DragonHead(eyesCount));

                ConsoleInputHandler.printIfInputIsIn(String.format("Дракон с ID-%d успешно обновлён!", id));
            }
        }*/
    }

    /**
     * Возвращает описание команды.
     *
     * @return строковое описание команды.
     */
    @Override
    public String getDescription(){
        return "обновить значение элемента коллекции, id которого равен заданному";
    }

    /**
     * Возвращает строковое представление аргумента команды.
     *
     * @return строковое представление аргумента команды.
     */
    @Override
    public String stringArgument(){
        return "id";
    }
    
}
