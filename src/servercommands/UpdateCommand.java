package servercommands;

import java.util.StringJoiner;

import collection.Color;
import collection.Coordinates;
import collection.Dragon;
import collection.DragonCharacter;
import collection.DragonHead;
import collection.DragonType;
import commandRecords.UpdateCommandArgs;
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

    /**
     * Конструктор команды UpdateCommand.
     *
     * @param dragonManager объект {@link DragonManager} для управления командами.
     */
    public UpdateCommand(DragonManager dragonManager){
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
     * Выполняет команду обновления дракона по его ID.
     *
     * @param arg строка, содержащая ID дракона.
     */
    @Override
    public String execute(Object argument){
        UpdateCommandArgs arg = (UpdateCommandArgs) argument;
        int id = arg.id();
        Dragon dragon;
        try {
            dragon = dragonManager.returnDragonById(id);
        } catch (Exception e) {
            return e.getMessage();
        }
        StringJoiner stringJoiner = new StringJoiner("\n");
        
        //stringJoiner.add(String.format("Начинаем изменение дракона с ID-%d и именем %s", id, dragon.getName()));
        dragon.setName(arg.name());
        dragon.setCoordinates(new Coordinates(arg.x(), arg.y()));
        dragon.setAge(arg.age());
        dragon.setColor(arg.color());
        dragon.setType(arg.type());
        dragon.setCharacter(arg.character());
        dragon.setHead(new DragonHead(arg.eyesCount()));

        stringJoiner.add(String.format("Дракон с ID-%d успешно обновлён!", id));
        return stringJoiner.toString();
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
