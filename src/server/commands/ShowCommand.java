package server.commands;
import java.util.StringJoiner;

import client.commands.utility.ConsoleInputHandler;
import collection.Dragon;
import managers.DragonManager;

/**
 * Команда для вывода всех элементов коллекции в строковом представлении.
 * Реализует интерфейс {@link Command}.
 */
public class ShowCommand implements Command {
    private DragonManager dragonManager;
    /**
     * Конструктор команды ShowCommand.
     *
     * @param dragonManager объект {@link DragonManager} для управления коллекцией драконов.
     */
    public ShowCommand(DragonManager dragonManager) {
        this.dragonManager = dragonManager;
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
     * Выполняет команду вывода всех элементов коллекции в строковом представлении.
     *
     * @param arg аргумент команды (в данной команде не используется).
     */
    @Override
    public String execute(Object arg){
        StringJoiner stringJoiner = new StringJoiner("\n");
        for (Dragon dragon : dragonManager.getSortedDragons()) {
            stringJoiner.add(dragon.toString());
        }
        return stringJoiner.toString();
    }

    /**
     * Возвращает описание команды.
     *
     * @return строковое описание команды.
     */
    @Override
    public String getDescription(){
        return "вывести в стандартный поток вывода все элементы коллекции в строковом представлении";
    }
    
}
