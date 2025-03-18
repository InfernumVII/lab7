package commands;

import java.util.ArrayList;
import java.util.List;

import collection.Dragon;
import managers.CommandManager;
import managers.DragonManager;
import utility.ConsoleInputHandler;


/**
 * Команда для удаления всех элементов коллекции, превышающих заданный по координатам.
 * Реализует интерфейс {@link CommandInterface}.
 */
public class RemoveGreaterCommand implements CommandInterface {

    private DragonManager dragonManager;
    private CommandManager commandManager;

    /**
     * Конструктор команды RemoveGreaterCommand.
     *
     * @param dragonManager объект {@link DragonManager} для управления командами.
     * @param commandManager  объект {@link CommandManager} для управления коллекцией драконов.
     */
    public RemoveGreaterCommand(DragonManager dragonManager, CommandManager commandManager){
        this.dragonManager = dragonManager;
        this.commandManager = commandManager;
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
    public void execute(String arg){
        ConsoleInputHandler.printIfInputIsIn("Введите координаты элемента: ");
        ConsoleInputHandler consoleInputHandler = new ConsoleInputHandler(commandManager);
        long x = consoleInputHandler.promptForLong("Введите координату x:", false, -420, Long.MAX_VALUE);
        long y = consoleInputHandler.promptForLong("Введите координату y:", false, Long.MIN_VALUE, 699);
        boolean finded = false;
        
        List<Dragon> dragonToDelete = new ArrayList<>();
        for (Dragon dragon : dragonManager.getDragonSet()) {
            if (x + y > dragon.getCoordinates().getX() + dragon.getCoordinates().getY()){
                finded = true;
                dragonToDelete.add(dragon);
            }
        }
        if (finded == false){
            ConsoleInputHandler.printIfInputIsIn("Драконы для удаления не найдены.");
        } else {
            for (Dragon dragon : dragonToDelete) {
                dragonManager.removeDragon(dragon);
                ConsoleInputHandler.printIfInputIsIn(String.format("Дракон с именем %s и айди %d был удалён", dragon.getName(), dragon.getId()));
            }
        }
    }

    /**
     * Возвращает описание команды.
     *
     * @return строковое описание команды.
     */
    @Override
    public String getDescription(){
        return "удалить из коллекции все элементы, превышающие заданный по координатам";
    }

    /**
     * Возвращает строковое представление аргумента команды.
     *
     * @return строковое представление аргумента команды.
     */
    @Override
    public String stringArgument(){
        return "{element}";
    }
    
}
