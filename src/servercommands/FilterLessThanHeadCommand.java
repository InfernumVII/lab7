package servercommands;
import java.util.StringJoiner;

import collection.Dragon;
import managers.DragonManager;
import utility.ArgHandler;
import utility.ConsoleInputHandler;

/**
 * Команда для вывода элементов коллекции, значение поля head которых меньше заданного.
 * Реализует интерфейс {@link CommandInterface}.
 */
public class FilterLessThanHeadCommand implements CommandInterface {
    private DragonManager dragonManager;

    /**
     * Конструктор команды FilterLessThanHeadCommand.
     *
     * @param dragonManager объект {@link DragonManager} для управления коллекцией драконов.
     */
    public FilterLessThanHeadCommand(DragonManager dragonManager){
        this.dragonManager = dragonManager;
    }

    /**
     * Проверяет, имеет ли команда аргументы.
     *
     * @return возвращает {@code true}, так как команда требует аргумента (количество глаз).
     */
    @Override
    public boolean isHasArgs(){
        return true;
    }

    /**
     * Выполняет команду вывода элементов коллекции, значение поля head которых меньше заданного.
     *
     * @param arg аргумент команды (количество глаз).
     */
    @Override
    public String execute(Object argument){
        String arg = (String) argument;
        StringJoiner stringJoiner = new StringJoiner("\n");
        try {
            if (ArgHandler.checkArgForFloat(arg)){
                Float eyesCount = Float.parseFloat(arg);
                stringJoiner.add(String.format("Драконы у которых кол-во глаз на говоле меньше чем: %s", eyesCount));
                for (Dragon dragon: dragonManager.getSortedDragons()) {
                    if (dragon.getHead().getEyesCount() < eyesCount){
                        stringJoiner.add(dragon.toString());
                    }
                }
            }
            
        } catch (Exception e) {
            return e.getMessage();
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
        return "вывести элементы, значение поля head которых меньше заданного";
    }

    /**
     * Возвращает строковое представление аргумента команды.
     *
     * @return строковое представление аргумента команды (количество глаз).
     */
    @Override
    public String stringArgument(){
        return "head";
    }

    
}
