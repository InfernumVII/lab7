package commands;

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
    public void execute(String arg){
        ConsoleInputHandler.printIfInputIsIn("Тип коллекции: " + dragonManager.getTypeName());
        ConsoleInputHandler.printIfInputIsIn("Дата инициализации: " + dragonManager.getInitializationDate());
        ConsoleInputHandler.printIfInputIsIn("Количество элементов: " + dragonManager.getDragonSet().size());
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
