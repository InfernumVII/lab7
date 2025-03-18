package commands;
import collection.Dragon;
import collection.DragonType;
import managers.DragonManager;
import utility.ArgHandler;
import utility.ConsoleInputHandler;

/**
 * Команда для подсчета количества драконов определенного типа в коллекции.
 * Реализует интерфейс {@link CommandInterface}.
 */
public class CountByTypeCommand implements CommandInterface {

    
    private DragonManager dragonManager;
    /**
     * Конструктор команды CountByTypeCommand.
     *
     * @param dragonManager объект {@link DragonManager} для управления коллекцией драконов.
     */
    public CountByTypeCommand(DragonManager dragonManager){
        this.dragonManager = dragonManager;
    }

    /**
     * Проверяет, имеет ли команда аргументы.
     *
     * @return возвращает {@code true}, так как команда тип дракона в виде аргумента.
     */
    @Override
    public boolean isHasArgs(){
        return true;
    }

    /**
     * Выполняет команду, подсчитывая количество драконов заданного типа.
     *
     * @param arg аргумент команды (тип дракона).
     */
    @Override
    public void execute(String arg){
        int count = 0;
        if (ArgHandler.checkArgForEnumString(arg, DragonType.values())){
            DragonType dragonType = DragonType.valueOf(arg);
            for (Dragon dragon : dragonManager.getSortedDragons()) {
                if (dragon.getType() == dragonType){
                    count += 1;
                }
            }
            ConsoleInputHandler.printIfInputIsIn(String.format("Количество драконов с данным типом: %d", count));
        }
    }

    /**
     * Возвращает описание команды.
     *
     * @return строковое описание команды.
     */
    @Override
    public String getDescription(){
        return "вывести количество элементов, значение поля type которых равно заданному";
    }

    /**
     * Возвращает строковое представление аргумента команды.
     *
     * @return строковое представление аргумента команды.
     */
    @Override
    public String stringArgument(){
        return "type";
    }

    
}
