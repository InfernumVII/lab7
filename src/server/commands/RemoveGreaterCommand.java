package server.commands;

import java.util.ArrayList;
import java.util.List;

import client.commands.utility.ConsoleInputHandler;
import collection.Dragon;
import commandRecords.RemoveGreaterCommandArgs;
import managers.CommandManager;
import managers.DragonManager;


/**
 * Команда для удаления всех элементов коллекции, превышающих заданный по координатам.
 * Реализует интерфейс {@link Command}.
 */
public class RemoveGreaterCommand implements Command {

    private DragonManager dragonManager;

    /**
     * Конструктор команды RemoveGreaterCommand.
     *
     * @param dragonManager объект {@link DragonManager} для управления командами.
     * @param commandManager  объект {@link CommandManager} для управления коллекцией драконов.
     */
    public RemoveGreaterCommand(DragonManager dragonManager){
        this.dragonManager = dragonManager;
    }

    /**
     * Проверяет, имеет ли команда аргументы.
     *
     * @return возвращает {@code false}, так как команда не принимает аргументов.
     */
    @Override
    public boolean isHasArgs(){
        return true;
    }

    /**
     * Выполняет команду удаления всех драконов, превышающих заданный по координатам.
     *
     * @param arg аргумент команды.
     */
    @Override
    public String execute(Object arg){
        RemoveGreaterCommandArgs args = (RemoveGreaterCommandArgs) arg;
        long x = args.x();
        long y = args.y();
        boolean finded = false;
        
        List<Dragon> dragonToDelete = new ArrayList<>();
        for (Dragon dragon : dragonManager.getDragonSet()) {
            if (x + y > dragon.getCoordinates().getX() + dragon.getCoordinates().getY()){
                finded = true;
                dragonToDelete.add(dragon);
            }
        }
        if (finded == false){
            return "Драконы для удаления не найдены.";
        } else {
            for (Dragon dragon : dragonToDelete) {
                dragonManager.removeDragon(dragon);
                return String.format("Дракон с именем %s и айди %d был удалён", dragon.getName(), dragon.getId());
            }
        }
        return null;
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
