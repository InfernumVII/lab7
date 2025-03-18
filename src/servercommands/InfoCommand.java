package servercommands;

import java.util.StringJoiner;

import managers.DragonManager;
import utility.ConsoleInputHandler;

/**
 * Команда для вывода информации о коллекции.
 * Реализует интерфейс {@link CommandInterface}.
 */
public class InfoCommand implements CommandInterface {
    private DragonManager dragonManager;
    /**
     * Конструктор команды InfoCommand.
     *
     * @param dragonManager объект {@link DragonManager} для управления коллекцией драконов.
     */
    public InfoCommand(DragonManager dragonManager){
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
     * Выполняет команду вывода информации о коллекции.
     *
     * @param arg аргумент команды (в данной команде не используется).
     */
    @Override
    public String execute(Object arg){
        StringJoiner stringJoiner = new StringJoiner("\n");
        stringJoiner.add("Тип коллекции: " + dragonManager.getTypeName());
        stringJoiner.add("Дата инициализации: " + dragonManager.getInitializationDate());
        stringJoiner.add("Количество элементов: " + dragonManager.getDragonSet().size());
        return stringJoiner.toString();
    }

    /**
     * Возвращает описание команды.
     *
     * @return строковое описание команды.
     */
    @Override
    public String getDescription(){
        return "вывод информации о коллекции.";
    }
    
}
