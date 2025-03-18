package servercommands;
import java.util.StringJoiner;

import collection.Dragon;
import managers.DragonManager;
import utility.ArgHandler;
import utility.ConsoleInputHandler;

/**
 * Команда для удаления элемента коллекции по его ID.
 * Реализует интерфейс {@link CommandInterface}.
 */
public class RemoveByIdCommand implements CommandInterface {

    private DragonManager dragonManager;
    /**
     * Конструктор команды RemoveByIdCommand.
     *
     * @param dragonManager объект {@link DragonManager} для управления коллекцией драконов.
     */
    public RemoveByIdCommand(DragonManager dragonManager){
        this.dragonManager = dragonManager;
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
     * Выполняет команду удаления дракона по его ID.
     *
     * @param arg строка, содержащая ID дракона.
     */
    @Override
    public String execute(Object argument){
        try {
            String arg = (String) argument;
            if (ArgHandler.checkArgForInt(arg)){
                int id = Integer.parseInt(arg);
                Dragon dragon = dragonManager.returnDragonById(id);
                if (dragon != null){
                    dragonManager.removeDragon(dragon);
                    return "Дракон удалён.";
                }
            }
        } catch (Exception e) {
            return e.getMessage();
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
        return "удалить элемент из коллекции по его id";
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
