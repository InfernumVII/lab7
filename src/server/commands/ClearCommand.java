package server.commands;
import client.commands.utility.ConsoleInputHandler;
import managers.DragonManager;

/**
 * Команда для очистки коллекции драконов.
 * Реализует интерфейс {@link Command}.
 */
public class ClearCommand implements Command{
    private DragonManager dragonManager;

    /**
     * Конструктор команды ClearCommand.
     *
     * @param dragonManager объект {@link DragonManager} для управления коллекцией драконов.
     */
    public ClearCommand(DragonManager dragonManager) {
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
     * Выполняет команду очистки коллекции драконов.
     *
     * @param arg аргумент команды (в данной команде не используется).
     */
    @Override
    public String execute(Object arg){
        dragonManager.clearDragonSet();
        return "Драконы были очищены!";
    }

    /**
     * Возвращает описание команды.
     *
     * @return строковое описание команды.
     */
    @Override
    public String getDescription(){
        return "очистить коллекцию";
    }
    
}
